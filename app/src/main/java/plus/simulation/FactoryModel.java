package plus.simulation;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.simulator.Model;

public class FactoryModel extends Model {
    private ContDistExponential machineBreakdownTime;
    private ContDistExponential repairTime;

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
        // The distribution mean is in minutes
        machineBreakdownTime = new ContDistExponential(this, "Machine Breakdown Time Distribution", 8 * 60, true, true);
        repairTime = new ContDistExponential(this, "Repair Time Distribution", 2 * 60, true, true);

        machineBreakdownTime.setNonNegative(true);
        repairTime.setNonNegative(true);
    }

    public double getMachineBreakdownTime() {
        return machineBreakdownTime.sample();
    }

    public double getRepairTime() {
        return repairTime.sample();
    }
}
