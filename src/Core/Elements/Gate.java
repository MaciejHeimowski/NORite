package Core.Elements;

public abstract class Gate extends Tile {

    protected int x, y;

    public Gate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public abstract void attachConnections();

    public abstract boolean getOutputState();
}
