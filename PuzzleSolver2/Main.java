import java.util.ArrayList;

public class Main {
    public static void solve() {
        Puzzle first = new Puzzle();
        first.read();
        System.out.println(first);

        recursion(first);

        System.out.println("Damedakore");
    }

    public static void recursion(Puzzle puzzle) {
//                                        System.out.println(puzzle);
                                        System.out.println(puzzle.toStringForRead());
        if(puzzle.isFinish()) {
            System.out.println("#####################################");
            System.out.println("Finisssssshhhhhhh!!!!!!");
            System.out.println(puzzle.toStringForRead());
            System.out.println("#####################################");
            System.exit(0);
            System.out.println("\n\n\n\n\n\n");
        }
        for(int i=0;i<puzzle.frame_list.size();i++) {
            Frame f = puzzle.frame_list.get(i);
            for(int j=0;j<f.num;j++) {
                for(int k=0;k<puzzle.piece_list.size();k++) {
                    Piece p = puzzle.piece_list.get(k);
                    for(int l=0;l<p.num;l++) {
                                        System.out.printf("%2d %2d %2d %2d\n",i,j,k,l);
                        double angle = Tool.calcAngle(p.getVec(l),f.getVec(j));
                        Vector vector = f.getCrd(j).toVector().plus(new Vector(p.getCrd(l).toVector().reverse(),angle));
                        State state = new State(vector,angle);
//                                        System.out.println(state);
//                                        System.out.println(f.getCrd(j));
//                                        System.out.println("start canPut()");
                        if(!(f.canPut(p,state,f.getCrd(j)))) {
//                                        System.out.println("end canPut()");
                            continue;
                        }
//                                        System.out.println("start evaluate()");
                        if(f.evaluate(p,state,f.getCrd(j)) < 1.0) {
//                                        System.out.println("end evaluate()");
                            continue;
                        }
                        Puzzle clone = puzzle.clone();
                        Frame cf = clone.frame_list.get(i);
                        Piece cp = clone.piece_list.get(k);
//                                        System.out.println("start put()");
                        clone.addFrameList(cf.put(cp,state,cf.getCrd(j)));
//                                        System.out.println("end put()");
                        clone.frame_list.remove(i);
                        clone.complete_list.add(cp);
                        clone.piece_list.remove(k);
                        recursion(clone);
                    }
                }
            }
        }
//        System.out.println("Not Found");
//        System.out.println(("\n\n\n------------------------------------\n\n\n"));
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
        Line line = new Line(new Coord(0.0,2.0),new Coord(3.0,2.0));
        System.out.println(line);
        System.out.println(line.isOnTheRight(new Coord(1.0,2.0)));
        System.out.println(line.isOnLine(new Coord(2.0,2.0)));
        System.out.println(line.calcDist(new Coord(3.0,1.0)));
        Triangle tri = new Triangle(new Coord(2.0,3.0),new Coord(0.0,3.0),new Coord(0.0,0.0));
        System.out.println(tri.isInArea(new Coord(1.0,2.0)));




        solve();
    }
}
