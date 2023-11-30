package Utils;

import static java.lang.Math.max;
import static java.lang.Math.min;

public record Coords(int x, int y) {

    public Coords add(Coords rhs) {
        return new Coords(this.x + rhs.x, this.y + rhs.y);
    }

    public boolean inRectangle(Coords lhs, Coords rhs) {
        return min(lhs.x(), rhs.x()) < this.x && this.x < max(lhs.x(), rhs.x()) &&
               min(lhs.y(), rhs.y()) < this.y && this.y < max(lhs.y(), rhs.y());
    }
}
