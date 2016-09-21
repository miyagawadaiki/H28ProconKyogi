//////////////////////////////////////////////
//  Coordクラス：座標を扱うクラス
//
//////////////////////////////////////////////

public class Coord extends Pair {
    public Coord() {
        super();
    }

    public Coord(double x, double y) {
        super(x,y);
    }

    public Coord(Coord c, Vector v) {
        this(c.dx+v.dx, c.dy+v.dy);
    }

    public Coord(Coord v, double angle) {
        this.dx = v.dx * Math.cos(angle) - v.dy * Math.sin(angle);
        this.dy = v.dx * Math.sin(angle) + v.dy * Math.cos(angle);
    }

    public Coord(Coord copy) {
        super(copy);
    }

    public Vector toVector() {
        return new Vector(this);
    }

    public Coord clone() {
        return new Coord(this);
    }

    public Coord translate(Vector v) {
        return new Coord(this,v);
    }

    public Coord move(State state) {
        return new Coord(new Coord(this,state.angle),state.vector);
    }

    public double dist() {
        return Math.sqrt(dx*dx + dy*dy);
    }

    public boolean equals(Coord c) {
        return super.equals((Pair)c);
    }

    public boolean nearlyEquals(Coord c) {
        return super.nearlyEquals((Pair)c);
    }
}
