package Circuit.Passive;

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

    public boolean goesThrough(Coords point) {
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
    public void draw() {

    }
}
