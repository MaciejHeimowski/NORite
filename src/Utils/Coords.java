package Utils;

public record Coords(int x, int y) {

    public Coords add(Coords rhs) {
        return new Coords(this.x + rhs.x, this.y + rhs.y);
    }
}
