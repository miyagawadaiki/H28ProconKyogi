/////////////////////////////////////////////////////
//  Pairクラス：２つの値を持つオブジェクト
//
//      double dx : x方向の変位量
//      double dy : y方向の変位量
//
/////////////////////////////////////////////////////

public class Pair {
    double dx, dy;

    public Pair() {
        this(0.0,0.0);
    }

    public Pair(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Pair(Pair copy) {
        this(copy.dx, copy.dy);
    }

    public Pair clone() {
        return new Pair(this);
    }

    public boolean equals(Pair t) {
        return this.dx == t.dx && this.dy == t.dy;
    }

    public String toString() {
        String s = "";
        s += String.format("(%8.5f, %8.5f)", dx, dy);
        return s;
    }
}
