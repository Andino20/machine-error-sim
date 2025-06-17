package plus.simulation;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeSpan;
import desmoj.core.statistic.*;

public class FactoryModel extends Model {
    private final int machines;
    private int availableWorkers;

    private ContDistExponential machineBreakdownTime;
    private ContDistExponential repairTime;

    private ProcessQueue<MachineProcess> repairQueue;

    private Aggregate totalMachineDowntime;
    private Aggregate totalRepairTime;

    public FactoryModel(String s, int machines, int workers) {
        super(null, s, true, true);
        this.machines = machines;
        availableWorkers = workers;
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
        for (int i = 0; i < machines; i++) {
            new MachineProcess(this, "Machine " + i).activate();
        }
    }

    @Override
    public void init() {
        // The distribution mean is in minutes
        machineBreakdownTime = new ContDistExponential(this, "Machine Breakdown Time Distribution", 8 * 60, true, true);
        repairTime = new ContDistExponential(this, "Repair Time Distribution", 2 * 60, true, true);
        machineBreakdownTime.setNonNegative(true);
        repairTime.setNonNegative(true);

        repairQueue = new ProcessQueue<>(this, "Repair Queue", true, true);

        totalMachineDowntime = new Aggregate(this, "Total Machine Downtime", true, false);
        totalMachineDowntime.setUnit("min");
        totalRepairTime = new Aggregate(this, "Total Repair Time", true, false);
        totalRepairTime.setUnit("min");
    }

    public TimeSpan getMachineBreakdownTime() {
        return machineBreakdownTime.sampleTimeSpan();
    }

    public TimeSpan getRepairTime() {
        return repairTime.sampleTimeSpan();
    }

    public ProcessQueue<MachineProcess> getRepairQueue() {
        return repairQueue;
    }

    public int availableWorkers() {
        return availableWorkers;
    }

    public void requestWorker() {
        availableWorkers--;
    }

    public void releaseWorker() {
        availableWorkers++;
    }

    public void reportDowntime(double downtime) {
        totalMachineDowntime.update(downtime);
    }

    public void reportRepairTime(double repairTime) {
        totalRepairTime.update(repairTime);
    }

    public double totalDowntime() {
        return totalMachineDowntime.getValue();
    }

    public double totalRepairTime() {
        return totalRepairTime.getValue();
    }
}
