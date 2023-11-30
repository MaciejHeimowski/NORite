package Circuit.Entities.Active;

import Circuit.Entities.Entity;

public abstract class ActiveEntity extends Entity {
    protected boolean outputState;

    public boolean getOutputState() {
        return outputState;
    }

    public abstract void draw();
}
