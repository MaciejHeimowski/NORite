package Core.Elements;

import Core.Node;
import UI.Editor;

import java.awt.*;

public class Wire extends Tile {

    private Node node;

    public void setNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return this.node;
    }

    @Override
    public Color getColor() {
        if(Editor.getStatus() != Editor.Status.Stopped && this.node.getState()) {
            return Color.GREEN;
        }
        else {
            return Color.WHITE;
        }
    }
}
