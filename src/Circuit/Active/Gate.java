package Circuit.Active;

import Circuit.Netlist.Node;
import Utils.Coords;

public abstract class Gate extends ActiveEntity {
    protected Coords coords;

    abstract public void updateState();
}
