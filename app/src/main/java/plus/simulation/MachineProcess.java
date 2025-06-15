package plus.simulation;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.SimProcess;

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

            if (model.availableWorkers() > 0) {
                model.getRepairQueue().remove(this);
            } else {
                passivate();
            }
            repair();
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
        hold(model.getRepairTime());
        model.releaseWorker();
    }
}