package Circuit.Entities.Passive;

import Circuit.Node;
import Utils.Port;
import Utils.Coords;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Wire extends PassiveEntity {
    public Wire(Coords start, Coords end, Bend bend) {
        this.start = start;
        this.end = end;
        this.bend = bend;
    }

    public enum Bend {
        HorizontalStart,
        VerticalStart
    }

    private Coords start, end;
    private Bend bend;

    private Port[] ports;

    private Node nodeHandle;

    public void setNodeHandle(Node nodeHandle) {
        this.nodeHandle = nodeHandle;
    }

    public Node getNodeHandle() {
        return this.nodeHandle;
    }
    @Override
    public boolean occupies(Coords point) {
        return switch(this.bend) {
            case HorizontalStart ->
                (point.y() == this.start.y()
                && min(this.start.x(), this.end.x()) < point.x()
                && point.x() < max(this.start.x(), this.end.x()))
                ||
                (point.x() == this.end.x()
                && min(this.start.y(), this.end.y()) < point.y()
                && point.y() < max(this.start.y(), this.end.y()));

            case VerticalStart ->
                (point.y() == this.start.x()
                && min(this.start.y(), this.end.y()) < point.y()
                && point.y() < max(this.start.y(), this.end.y()))
                ||
                (point.x() == this.end.y()
                && min(this.start.x(), this.end.x()) < point.x()
                && point.x() < max(this.start.x(), this.end.x()));
        };
    }

    @Override
    public void setCoords(Coords coords) {
        Coords diff = new Coords(coords.x() - this.coords.x(), coords.y() - this.coords.y());
        this.start.add(diff);
        this.end.add(diff);
    }

    @Override
    public Port[] getPorts() {
        return null;
    }

    @Override
    public void draw() {

    }
}
