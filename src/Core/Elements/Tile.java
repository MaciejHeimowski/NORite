package Core.Elements;

import java.awt.*;
import java.io.Serializable;

public abstract class Tile implements Serializable {

    /**
     * Returns the color that the tile should have, based on its type and internal state
     * @return tile color
     */
    public abstract Color getColor();
}
