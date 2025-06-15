package plus.simulation;

import desmoj.core.simulator.Model;

public class FactoryModel extends Model {
    public FactoryModel(String s) {
        super(null, s, true, true);
    }

    @Override
    public String description() {
        return "Factory Model: " +
                "simulates a factory with m machines that can break after a random time. " +
                "The factory has s service workers available that can repair the machines. " +
                "A broken machine generates costs for the company until it is repaired. " +
                "In addition, the company has to pay each service worker a fixed salary.";
    }

    @Override
    public void doInitialSchedules() {

    }

    @Override
    public void init() {

    }
}
