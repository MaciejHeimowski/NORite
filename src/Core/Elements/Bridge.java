package Core.Elements;

import UI.Editor;

import java.awt.*;

public class Bridge extends Tile {

    private int x, y;

    public Bridge(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public Color getColor() {
        return Color.YELLOW;
    }
}
