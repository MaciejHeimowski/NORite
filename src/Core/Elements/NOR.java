package Core.Elements;

import Core.Node;
import UI.Editor;

import java.awt.*;

public class NOR extends Gate {

    private Node inputA, inputB;

    public NOR(int x, int y) {
        super(x, y);
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public void attachConnections() {
        Tile tileL = Editor.getTile(this.x - 1, this.y);
        Tile tileR = Editor.getTile(this.x + 1, this.y);
        Tile tileU = Editor.getTile(this.x, this.y - 1);
        Tile tileD = Editor.getTile(this.x, this.y + 1);

        boolean isWireL = tileL instanceof Wire;
        boolean isWireR = tileR instanceof Wire;
        boolean isWireU = tileU instanceof Wire;
        boolean isWireD = tileD instanceof Wire;

        if(isWireL && isWireU && isWireR && !isWireD) {
            ((Wire) tileU).getNode().addDriver(this);

            this.inputA = ((Wire) tileL).getNode();
            this.inputB = ((Wire) tileR).getNode();
        }
        else if(isWireL && isWireU && !isWireR && isWireD) {
            ((Wire) tileL).getNode().addDriver(this);

            this.inputA = ((Wire) tileU).getNode();
            this.inputB = ((Wire) tileD).getNode();
        }
        else if(isWireL && !isWireU && isWireR && isWireD) {
            ((Wire) tileD).getNode().addDriver(this);

            this.inputA = ((Wire) tileL).getNode();
            this.inputB = ((Wire) tileR).getNode();
        }
        else if(!isWireL && isWireU && isWireR && isWireD) {
            ((Wire) tileR).getNode().addDriver(this);

            this.inputA = ((Wire) tileU).getNode();
            this.inputB = ((Wire) tileD).getNode();
        }
        else {
            // invalid placement
        }
    }

    @Override
    public boolean getOutputState() {
        return !(this.inputA.getState() || this.inputB.getState());
    }
}