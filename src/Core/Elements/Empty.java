package Core.Elements;

import java.awt.*;

public class Empty extends Tile {
    static boolean even;

    @Override
    public Color getColor() {
        even = !even;
        if(even) {
            return Color.DARK_GRAY;
        }
        else {
            return Color.GRAY;
        }
    }

    public Empty() {

    }
}
