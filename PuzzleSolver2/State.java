///////////////////////////////////////////////////////////////////
//  Stateクラス：ワールド座標系での位置と回転角度をまとめたオブジェクト
///////////////////////////////////////////////////////////////////

public class State {
    Vector vector;
    double angle;

    public State() {
        this(new Vector(), 0.0);
    }

    public State(Vector v, double a) {
        vector = v.clone();
        angle = a;
    }

    public State(State copy) {
        this(copy.vector, copy.angle);
    }

    public State(Coord c, double a) {
        this(c.toVector(),a);
    }

    public State clone() {
        return new State(this);
    }

    public String toString() {
        return vector.toString() + "\t" + angle;
    }
}
