package Circuit.Active;

import Circuit.Netlist.Node;

public class NOT extends Gate {

    private Node inputHandle;

    @Override
    public void updateState() {
        this.outputState = !this.inputHandle.getDriverHandle().getOutputState();
    }

    @Override
    public void draw() {

    }
}
