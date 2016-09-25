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
        double c = (dy/dx)*st.dx - st.dy;
        double x0 = co.dx;
        double y0 = co.dy;

        return Math.abs(a*x0 + b*y0 + c)/Math.sqrt(a*a + b*b);
    }

    public boolean isOnLine(Coord c) {
        Coord s = getSt(), g = getGo();
        if(dx == 0) {
            if(Tool.equals(s.dx,c.dx)) {
//            if(s.dx == c.dx) {
                int t = (Tool.smallerThan(s.dy,g.dy))?1:-1;
//                int t = (s.dy<g.dy)?1:-1;
                return Tool.STorE(s.dy*t,c.dy*t) && Tool.STorE(c.dy*t,g.dy*t);
//                return s.dy*t <= c.dy*t && c.dy*t <= g.dy*t;
            }
            return false;
        }
        else if(dy == 0) {
            if(Tool.equals(s.dy,c.dy)) {
//            if(s.dy == c.dy) {
                int t = (Tool.smallerThan(s.dx,g.dx))?1:-1;
//                int t = (s.dx<g.dx)?1:-1;
                return Tool.STorE(s.dx*t,c.dx*t) && Tool.STorE(c.dx*t,g.dx*t);
//                return s.dx*t <= c.dx*t && c.dx*t <= g.dx*t;
            }
            return false;
        }
        else {
            int tx = (Tool.smallerThan(s.dx,g.dx))?1:-1;
//            int tx = (s.dx<g.dx)?1:-1;
            int ty = (Tool.smallerThan(s.dy,g.dy))?1:-1;
//            int ty = (s.dy<g.dy)?1:-1;
            return (Tool.STorE(s.dx*tx,c.dx*tx) &&
//            return (s.dx*tx <= c.dx*tx &&
                    Tool.STorE(c.dx*tx,g.dx*tx) &&
//                    c.dx*tx <= g.dx*tx &&
                    Tool.STorE(s.dy*ty,c.dy*ty) &&
//                    s.dy*ty <= c.dy*ty &&
                    Tool.STorE(c.dy*ty,g.dy*ty) &&
//                    c.dy*ty <= g.dy*ty &&
                    Tool.equals(calcDist(c),0.0));
//                    calcDist(c) == 0.0);
        }
    }

    public boolean isOnLine(Line l) {
        return isOnLine(l.getSt()) && isOnLine(l.getGo());
    }

    public boolean isOnTheRight(Coord c) {
        if(this.dx == 0.0) {
            if(this.dy > 0) return Tool.STorE(c.dx,st.dx);
//            if(this.dy > 0) return c.dx <= st.dx;
            else return Tool.LTorE(c.dx,st.dx);
//            else return c.dx >= st.dx;
        }
        else if(this.dy == 0.0) {
            if(this.dx > 0) return Tool.LTorE(c.dy,st.dy);
//            if(this.dx > 0) return c.dy >= st.dy;
            else return Tool.STorE(c.dy,st.dy);
//            else return c.dy <= st.dy;
        }
        else {
            double y = (this.dy/this.dx)*(c.dx-st.dx) + st.dy;
            if(this.dx > 0) return Tool.STorE(y,c.dy);
//            if(this.dx > 0) return y <= c.dy;
            else return Tool.LTorE(y,c.dy);
//            else return y >= c.dy;
        }
    }

    public boolean isCross(Line l) {
        if(isOnLine(l.getSt()) || isOnLine(l.getGo()) ||
           l.isOnLine(getSt()) || l.isOnLine(getGo())) return false;
        boolean a = isOnTheRight(l.getSt());
        boolean b = isOnTheRight(l.getGo());
        boolean c = l.isOnTheRight(this.getSt());
        boolean d = l.isOnTheRight(this.getGo());
        return (a ^ b) && (c ^ d);
    }

    public boolean equals(Line l) {
        return super.equals((Vector)l) && this.st.equals(l.st);
    }

    public boolean nearlyEquals(Line l) {
        return super.nearlyEquals((Vector)l) && this.st.nearlyEquals(l.st);
    }

    public String toString() {
        String t = "("+ super.toString();
        t += ","+ st +")";
        return t;
    }
}
