package Circuit.Active;

import Circuit.Netlist.Node;

public class NOR extends Gate {

    private Node inputAHandle, inputBHandle;

    @Override
    public void updateState() {
        this.outputState = !(this.inputAHandle.getDriverHandle().getOutputState() || this.inputBHandle.getDriverHandle().getOutputState());
    }

    @Override
    public void draw() {

    }
}
