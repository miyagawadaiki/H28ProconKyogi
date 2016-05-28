public class Coord {
    double x;
    double y;

    public Coord() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public Coord(Coord copy) {
        this(copy.x,copy.y);
    }

    public Coord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coord(Coord c, Vector v) {
        this(c.x + v.dx, c.y + v.dy);
    }

    public Coord clone() {
        return new Coord(this);
    }

    public String toString() {
        return String.format("(%10f,%10f)", x, y);
    }
}
