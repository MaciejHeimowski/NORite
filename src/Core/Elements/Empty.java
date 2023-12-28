package Core.Elements;

import java.awt.*;

public class Empty extends Tile {
    private boolean even;

    @Override
    public Color getColor() {
        if(even) {
            return Color.DARK_GRAY;
        }
        else {
            return Color.GRAY;
        }
    }

    public Empty(int x, int y) {
        even = (x + y) % 2 == 0;
    }
}
