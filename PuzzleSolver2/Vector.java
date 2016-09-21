/////////////////////////////////////////////////////
//  Vectorクラス：2次元ベクトルを表現するオブジェクト
//
//      double dx : x方向の変位量
//      double dy : y方向の変位量
//
/////////////////////////////////////////////////////

public class Vector extends Pair {
    public Vector() {
        super();
    }

    public Vector(double dx, double dy) {
        super(dx,dy);
    }

    public Vector(Coord s, Coord g) {
        this(g.dx-s.dx, g.dy-s.dy);
    }

    public Vector(Vector v, double angle) {
        this.dx = v.dx * Math.cos(angle) - v.dy * Math.sin(angle);
        this.dy = v.dx * Math.sin(angle) + v.dy * Math.cos(angle);
    }

    public Vector(Coord c) {
        this(c.dx,c.dy);
    }

    public Vector(Vector copy) {
        super(copy);
    }

    @Override
    public Vector clone() {
        return new Vector(this);
    }

    public Vector move(State state) {
        return new Vector(this,state.angle);
    }

    public double length() {
        return Math.sqrt(dx*dx + dy*dy);
    }

    public Vector plus(Vector v) {
        return new Vector(this.dx + v.dx, this.dy + v.dy);
    }

    public Vector minus(Vector v) {
        return plus(v.reverse());
    }

    public Vector reverse() {
        return new Vector(-1 * this.dx, -1*this.dy);
    }

    public boolean equals(Vector v) {
        return super.equals((Pair)v);
    }

    public boolean nearlyEquals(Vector v) {
        return super.nearlyEquals((Pair)v);
    }
}
