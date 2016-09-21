public class Main {
    public static void solve() {
        Puzzle first = new Puzzle();
        first.read();
        System.out.println(first);

        recursion(first);
    }

    public static void recursion(Puzzle puzzle) {
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
                        State state = new State(f.getCrd(i),Tool.calcAngle(p.getVec(j),f.getVec(i)));
                        if(!(f.canPut(p,state,f.getCrd(i)))) break;
                        if(f.evaluate(p,state,f.getCrd(i)) < 1.0) break;
                        for(Frame ff : f.put(p,state,f.getCrd(i))) {
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
        System.exit(0);
    }

    public static void main(String[] args) {
        Coord[] cs1 = { new Coord(0.0,0.0),
                        new Coord(1.5,0.0),
                        new Coord(1.5,1.5),
                        new Coord(2.5,1.5),
                        new Coord(2.5,0.0),
                        new Coord(4.0,0.0),
                        new Coord(4.0,4.0),
                        new Coord(0.0,4.0),
                    };
        Coord[] cs2 = { new Coord(1.0,1.0),
                        new Coord(3.0,1.0),
                        new Coord(3.0,3.0),
                        new Coord(1.0,3.0),
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
