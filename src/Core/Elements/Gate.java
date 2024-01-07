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

    /**
     * Scans neighbouring tiles and connects nodes to the gate's inputs and outputs
     */
    public abstract void attachConnections();

    /**
     * Returns the output state of the gate based on it's logic function
     * @return output state
     */
    public abstract boolean getOutputState();
}
