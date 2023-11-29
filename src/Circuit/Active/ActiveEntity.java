package Circuit.Active;

import Circuit.Entity;

public abstract class ActiveEntity extends Entity {
    protected boolean outputState;

    public boolean getOutputState() {
        return outputState;
    }

    public abstract void draw();
}
