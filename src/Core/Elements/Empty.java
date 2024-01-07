package Core.Elements;

import java.awt.*;

public class Empty extends Tile {

    // Variable that ensures the empty area will be drawn like a chess board instead of a uniform color
    private boolean even;

    @Override
    public Color getColor() {
        if(even) {
            return new Color(40, 40, 40);
        }
        else {
            return new Color(70, 70, 70);
        }
    }

    public Empty(int x, int y) {
        even = (x + y) % 2 == 0;
    }
}
