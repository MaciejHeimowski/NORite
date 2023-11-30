package Circuit.Entities;

import Utils.Port;
import Utils.Coords;

public abstract class Entity {

    public Coords coords;

    public abstract boolean occupies(Coords point);

    public abstract void setCoords(Coords coords);

    public abstract Port[] getPorts();

    public abstract void draw();
}
