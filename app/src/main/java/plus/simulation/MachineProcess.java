package plus.simulation;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class MachineProcess extends SimProcess {
    private final FactoryModel model;

    public MachineProcess(FactoryModel parent, String name) {
        super(parent, name, true);
        this.model = parent;
    }

    @Override
    public void lifeCycle() throws SuspendExecution {
        while (true) {
            hold(model.getMachineBreakdownTime());
            model.getRepairQueue().insert(this);

            TimeInstant repairStartTime = model.presentTime();

            if (model.availableWorkers() > 0) {
                model.getRepairQueue().remove(this);
            } else {
                passivate();
            }
            repair();

            TimeInstant repairEndTime = model.presentTime();
            double downtime = repairEndTime.getTimeAsDouble() - repairStartTime.getTimeAsDouble();
            model.reportDowntime(downtime);

            tryRepairNext();
        }
    }

    private void tryRepairNext() {
        if (!model.getRepairQueue().isEmpty()) {
            MachineProcess next = model.getRepairQueue().removeFirst();
            next.activate();
        }
    }

    public void repair() throws SuspendExecution {
        model.requestWorker();
        TimeSpan repairTime = model.getRepairTime();
        hold(repairTime);
        model.reportRepairTime(repairTime.getTimeAsDouble());
        model.releaseWorker();
    }
}