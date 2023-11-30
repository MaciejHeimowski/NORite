package Circuit.Entities.Active;

import Circuit.Entities.Active.ActiveEntity;
import Utils.Orientation;

public abstract class Gate extends ActiveEntity {

    protected Orientation orientation;

    abstract public void setOrientation(Orientation orientation);

    abstract public void updateState();
}
