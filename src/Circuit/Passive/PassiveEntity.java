package Circuit.Passive;

import Circuit.Entity;
import Circuit.Netlist.Node;

public abstract class PassiveEntity extends Entity {
    private Node inputHandle;

    @Override
    public abstract void draw();
}
