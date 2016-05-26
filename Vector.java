public class Vector {
    double dx,dy;
    double length;

    public Vector() {
        this(0.0, 0.0);
    }

    public Vector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        calcLength();
    }

    public Vector(double sx, double sy, double gx, double gy) {
        this(gx-sx, gy-sy);
    }

    public Vector(Coord s, Coord g) {
        this(s.x, s.y, g.x, g.y);
    }

    public Vector(Vector a, Vector b) {
        this(a.dx + b.dx, a.dy + b.dy);
    }

    public Vector(Vector v, double theta) {
//        System.out.printf("angle:%f\n", theta);
        this.dx = v.dx * Math.cos(theta) - v.dy * Math.sin(theta);
        this.dy = v.dx * Math.sin(theta) + v.dy * Math.cos(theta);
//        System.out.println(this);
        calcLength();
    }

    public Vector(Vector v, double a, double b) {
        this(v.dx * a, v.dy * b);
    }

    public Vector(Vector copy) {
        this(copy.dx, copy.dy);
    }


    public Vector clone() {
        return new Vector(this);
    }

    public void calcLength() {
        length = Math.sqrt(dx*dx + dy*dy);
    }

    public boolean equals(Vector v) {
        return Tool.hasAccuracy(this.dx,v.dx) && Tool.hasAccuracy(this.dy,v.dy);
    }

    public String toString() {
        String s = "";
        s += String.format("(%10f, %10f)", dx, dy);
        return s;
    }
}
