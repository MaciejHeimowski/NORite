package Core;

public class Node {
    private boolean state;

    private int id;

    public Node(int id) {
        this.id = id;
    }

    public boolean getState() {
        return this.state;
    }

    public int getId() {
        return this.id;
    }
}
