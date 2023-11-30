package Circuit.Entities.Active.Gates;

import Circuit.Entities.Active.Gate;
import Utils.Port;
import Utils.Coords;
import Utils.Orientation;

public class NOR extends Gate {

    private final Port[] ports = new Port[3];

    private final Port output = ports[0], inputA = ports[1], inputB = ports[2];

    private void setPortCoords(Orientation orientation) {
        this.orientation = orientation;

        this.inputA.setCoords(
            this.coords.add(
                switch(orientation) {
                    case Up    -> new Coords(-1, -3);
                    case Down  -> new Coords(-1,  3);
                    case Left  -> new Coords( 3, -1);
                    case Right -> new Coords(-3, -1);
                }
            )
        );
        this.inputB.setCoords(
            this.coords.add(
                switch(orientation) {
                    case Up    -> new Coords( 1, -3);
                    case Down  -> new Coords( 1,  3);
                    case Left  -> new Coords( 3,  1);
                    case Right -> new Coords(-3,  1);
                }
            )
        );
    }

    public NOR(Coords coords, Orientation orientation) {
        this.coords = coords;
        this.output.setCoords(this.coords);
        this.setPortCoords(orientation);
    }

    @Override
    public void setOrientation(Orientation orientation) {
        this.setPortCoords(orientation);
    }

    @Override
    public boolean occupies(Coords point) {
        return switch(orientation) {
            case Up    -> point.inRectangle(this.coords.add(new Coords(-1, -3)), new Coords(1, 0));
            case Down  -> point.inRectangle(this.coords.add(new Coords(-1,  3)), new Coords(1, 0));
            case Left  -> point.inRectangle(this.coords.add(new Coords(-3, -1)), new Coords(0, 1));
            case Right -> point.inRectangle(this.coords.add(new Coords( 3, -1)), new Coords(0, 1));
        };
    }

    public Port isPort(Coords point) {
        if(inputA.getCoords().equals(point))
            return inputA;
        else if(inputB.getCoords().equals(point))
            return inputB;
        else if(output.getCoords().equals(point))
            return output;
        else
            return null;
    }

    @Override
    public void setCoords(Coords coords) {
        this.coords = coords;
        this.setPortCoords(orientation);
    }

    @Override
    public void updateState() {
        this.outputState = !(this.inputA.getNode().getDriverHandle().getOutputState() || this.inputB.getNode().getDriverHandle().getOutputState());
    }

    @Override
    public Port[] getPorts() {
        return this.ports;
    }

    @Override
    public void draw() {

    }
}
