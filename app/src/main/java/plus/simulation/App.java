package plus.simulation;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;

public class App {
    public static void main(String[] args) {
        Model model = new FactoryModel("Factory Model", 4, 1);
        Experiment exp = new Experiment("Factory Experiment", "../reports");

        model.connectToExperiment(exp);

        exp.setShowProgressBar(true);
        exp.setShowProgressBarAutoclose(true);
        TimeInstant stopTime = new TimeInstant(60000);
        exp.tracePeriod(new TimeInstant(0), stopTime);
        exp.stop(stopTime);

        exp.start();

        exp.report();
        exp.finish();
    }
}
