package Core.Elements;

import java.awt.*;

public abstract class Tile {

    private int x, y;

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public abstract Color getColor();
}
