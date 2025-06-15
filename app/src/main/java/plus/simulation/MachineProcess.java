package plus.simulation;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.SimProcess;

public class MachineProcess extends SimProcess {
    public MachineProcess(FactoryModel parent, String name) {
        super(parent, name, true);
    }

    @Override
    public void lifeCycle() throws SuspendExecution {

    }
}
