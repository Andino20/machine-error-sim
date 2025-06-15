package plus.simulation;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;

import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        Model model = new FactoryModel("Factory Model");
        Experiment exp = new Experiment("Factory Experiment", "../reports");

        model.connectToExperiment(exp);

        exp.setShowProgressBar(true);
        TimeInstant stopTime = new TimeInstant(1000, TimeUnit.MINUTES);
        exp.tracePeriod(new TimeInstant(0), stopTime);
        exp.stop(stopTime);

        exp.start();

        exp.report();
        exp.finish();
    }
}
