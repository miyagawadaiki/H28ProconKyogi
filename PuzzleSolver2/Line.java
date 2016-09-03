////////////////////////////////////////////////////////////
//  Lineクラス：方向有り線分を表現するオブジェクト
//
//      親クラス : Vector
//
//      Vector start : 線分の開始点
//
////////////////////////////////////////////////////////////

public class Line extends Vector {
    Coord st;

    public Line(double dx, double dy, Coord st) {
        super(dx,dy);
        this.st = st;
    }

    public Line(Vector v, Coord st) {
        super(v);
        this.st = st;
    }

    public Line(Coord s, Coord g) {
        super(s,g);
        st = s;
    }

    public Coord getSt() {
        return st;
    }

    public Coord getGo() {
        return new Coord(st.dx+this.dx,st.dy+this.dy);
    }

    public double calcDist(Coord co) {
        if(dx == 0.0) return Math.abs(co.dy-this.dy);
        double a = -dy/dx;
        double b = 1.0;
        double c = (dy/dx)*st.dx + st.dy;
        double x0 = co.dx;
        double y0 = co.dy;

        return Math.abs(a*x0 + b*y0 + c)/Math.sqrt(a*a + b*b);
    }

    public boolean isOnTheRight(Coord c) {
        if(this.dx == 0.0) {
            if(this.dy > 0) return c.dx <= st.dx;
            else return c.dx >= st.dx;
        }
        else if(this.dy == 0.0) {
            if(this.dx > 0) return c.dy >= st.dy;
            else return c.dy <= st.dy;
        }
        else {
            double y = (this.dy/this.dx)*(c.dx-st.dx) + st.dy;
            if(this.dx > 0) return y <= c.dy;
            else return y >= c.dy;
        }
    }

    public boolean isCross(Line l) {
        boolean a = isOnTheRight(l.getSt());
        boolean b = isOnTheRight(l.getGo());
        return a ^ b;
    }

    public String toString() {
        String t = "("+ super.toString();
        t += ","+ st +")";
        return t;
    }
}
