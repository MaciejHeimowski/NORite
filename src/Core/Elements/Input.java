package Core.Elements;

import UI.Editor;

import java.awt.*;

public class Input extends Gate {

    private boolean state;

    public Input(int x, int y) {
        super(x, y);
    }

    public Color getColor() {
        if(this.state) {
            return new Color(0, 200, 200);
        }
        else
        {
            return new Color(0, 64, 64);
        }
    }

    @Override
    public void attachConnections() {
        Tile tileL = this.x - 1 >= 1  ? Editor.getTile(this.x - 1, this.y) : null;
        Tile tileR = this.x + 1 <= 38 ? Editor.getTile(this.x + 1, this.y) : null;
        Tile tileU = this.y - 1 >= 1  ? Editor.getTile(this.x, this.y - 1) : null;
        Tile tileD = this.y + 1 <= 31 ? Editor.getTile(this.x, this.y + 1) : null;

        boolean isWireL = tileL instanceof Wire;
        boolean isWireR = tileR instanceof Wire;
        boolean isWireU = tileU instanceof Wire;
        boolean isWireD = tileD instanceof Wire;

        if(isWireU) {
            ((Wire) tileU).getNode().addDriver(this);
        }
        else if(isWireL) {
            ((Wire) tileL).getNode().addDriver(this);
        }
        else if(isWireD) {
            ((Wire) tileD).getNode().addDriver(this);
        }
        else if(isWireR) {
            ((Wire) tileR).getNode().addDriver(this);
        }
        else {
            // No connections
        }
    }

    public void flipOutputState() {
        this.state = !this.state;
    }

    @Override
    public boolean getOutputState() {
        return this.state;
    }
}
