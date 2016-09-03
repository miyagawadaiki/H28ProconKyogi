public class Main {
    public void solve() {

    }

    public static void main(String[] args) {
        System.out.println(Tool.calcAngle(new Vector(0.0,-3.0),new Vector(-1.0,0.0)));
        Line line = new Line(new Coord(2.0,3.0),new Coord(0.0,3.0));
        System.out.println(line);
        System.out.println(line.isOnTheRight(new Coord(1.0,2.0)));
        Triangle tri = new Triangle(new Coord(2.0,3.0),new Coord(0.0,3.0),new Coord(0.0,0.0));
        System.out.println(tri.isInArea(new Coord(1.0,2.0)));
        Puzzle first = new Puzzle();
        first.read();
        System.out.println(first.toStringByTriangle());
    }
}
