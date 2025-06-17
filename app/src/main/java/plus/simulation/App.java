package plus.simulation;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class App {

    private static final int MAX_MACHINES = 20;
    private static final int MAX_WORKERS = 20;
    private static final double MACHINE_COST_PER_MIN = 50.0 / 60.0;
    private static final double WORKER_COST_PER_MIN = 10.0 / 60.0;

    // Time Units are in minutes
    private static final TimeInstant SIM_TIME = new TimeInstant(250 * 8 * 60);
    private static final TimeInstant START_TIME = new TimeInstant(8 * 60);

    public static void main(String[] args) {
        //runExperiment(10, 5, System.currentTimeMillis());
        runMatrixExperiment(MAX_MACHINES, MAX_WORKERS); // full simulation run with all combinations
    }

    private static void runMatrixExperiment(int machines, int workers) {
        for (int m = 1; m <= machines; m++) {
            Path filepath = Path.of("..", "reports", String.format("m%02d.txt", m));
            try (BufferedWriter fw = Files.newBufferedWriter(filepath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                fw.write("s\tmachine_cost\tworker_cost\ttotal_cost\n");
                for (int s = 1; s <= workers; s++) {
                    SimulationResult result = runExperiment(m, s, System.currentTimeMillis());
                    double machineCost = result.totalDowntime() * MACHINE_COST_PER_MIN;
                    double workerCost = SIM_TIME.getTimeAsDouble() * WORKER_COST_PER_MIN * s;
                    double totalCost = machineCost + workerCost;
                    fw.write(String.format("%d\t%.2f\t%.2f\t%.2f\n", s, machineCost, workerCost, totalCost));
                }
                fw.flush();
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }
    }

    private static SimulationResult runExperiment(int machines, int workers, long seed) {
        FactoryModel model = new FactoryModel("Factory Model", machines, workers);
        Experiment exp = new Experiment("Factory Experiment", "../reports");
        exp.setSeedGenerator(seed);
        model.connectToExperiment(exp);

        exp.setShowProgressBar(false);
        exp.setShowProgressBarAutoclose(true);
        exp.tracePeriod(START_TIME, SIM_TIME);
        exp.stop(SIM_TIME);

        exp.start(START_TIME);

        exp.report();
        exp.finish();

        return new SimulationResult(model.totalDowntime(), model.totalRepairTime());
    }
}
