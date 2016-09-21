import java.util.ArrayList;

public class Main {
    public static void solve() {
        Puzzle first = new Puzzle();
        first.read();
        System.out.println(first);

        recursion(first);
    }

    public static void recursion(Puzzle puzzle) {
                                        System.out.println(puzzle);
        if(puzzle.isFinish()) {
            System.out.println("Finisssssshhhhhhh!!!!!!");
            System.out.println(puzzle.toStringForRead());
            System.exit(0);
        }
        for(int i=0;i<puzzle.frame_list.size();i++) {
            Frame f = puzzle.frame_list.get(i);
            for(int j=0;j<f.num;j++) {
                for(int k=0;k<puzzle.piece_list.size();k++) {
                    Piece p = puzzle.piece_list.get(k);
                    for(int l=0;l<p.num;l++) {
                                        System.out.printf("%d%d%d%d\n",i,j,k,l);
                        double angle = Tool.calcAngle(p.getVec(l),f.getVec(j));
                        Vector vector = f.getCrd(j).toVector().plus(new Vector(p.getCrd(l).toVector().reverse(),angle));
                        State state = new State(vector,angle);
                                        System.out.println(state);
                                        System.out.println(f.getCrd(j));
                        if(!(f.canPut(p,state,f.getCrd(j)))) continue;
                                        System.out.println("h");
                        if(f.evaluate(p,state,f.getCrd(j)) < 1.0) continue;
                                        System.out.println("o");
                        ArrayList<Frame> ret = f.put(p,state,f.getCrd(j));
                        if(ret.size() == 0) continue;
                                        System.out.println("ge");
                        for(Frame ff : ret) {
                                        System.out.println(ff);
                            puzzle.frame_list.add(ff);
                        }
                        puzzle.frame_list.remove(i);
                        puzzle.complete_list.add(p);
                        puzzle.piece_list.remove(k);
                        recursion(puzzle.clone());
                    }
                }
            }
        }
        System.out.println("Not Found");
//        System.exit(0);
    }

    public static void main(String[] args) {
        Coord[] cs1 = { new Coord(3.0,1.0),
                        new Coord(3.0,3.0),
                        new Coord(0.0,3.0),
                        new Coord(0.0,2.0),
                        new Coord(2.0,2.0),
                        new Coord(2.0,1.0),
                    };
        Coord[] cs2 = { new Coord(3.0,1.0),
                        new Coord(3.0,2.0),
                        new Coord(2.0,2.0),
                        new Coord(2.0,1.0),
                    };
        Piece p = new Piece(cs2);
        Frame f = new Frame(cs1);
        System.out.println(f.isInArea(p));
        System.out.println(f.calcArea());
        System.out.println(Tool.calcAngle(new Vector(0.0,-3.0),new Vector(-1.0,0.0)));
        Line line = new Line(new Coord(2.0,3.0),new Coord(0.0,3.0));
        System.out.println(line);
        System.out.println(line.isOnTheRight(new Coord(1.0,2.0)));
        System.out.println(line.isOnLine(new Coord(1.0,3.0)));
        Triangle tri = new Triangle(new Coord(2.0,3.0),new Coord(0.0,3.0),new Coord(0.0,0.0));
        System.out.println(tri.isInArea(new Coord(1.0,2.0)));




        solve();
    }
}
