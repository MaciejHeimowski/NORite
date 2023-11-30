package Utils;

import Circuit.Entities.Active.ActiveEntity;
import Circuit.Node;

public class Port {

    public enum Direction {
        Output,
        Input,
        Conductor
    }

    public final Direction direction;
    private Coords coords;

    private Node node;

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public Coords getCoords() {
        return this.coords;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return this.node;
    }

    public Port(Direction direction, Coords coords, Node node) {
        this.direction = direction;
        this.coords = coords;
        this.node = node;
    }
}
