import java.util.Scanner;
import java.util.ArrayList;

public class Solver {
    ArrayList<Piece> data;
    ArrayList<Piece> fixed;
    Frame frame;
    double error;

    public Solver() {
        data = new ArrayList<Piece>();
        fixed = new ArrayList<Piece>();
        error = 0.0;
    }

    public Solver(Solver copy) {
        this();
        for(Piece p : copy.data)
            this.data.add(p.clone());
        for(Piece p : copy.fixed)
            this.fixed.add(p.clone());
        this.frame = copy.frame.clone();
    }

    public Solver clone() {
        return new Solver(this);
    }

    //TODO
    public double eval(Piece p) {
        return 0.0;
    }

    // ファイルorコンソールから読み込む
    public void read() {
        Scanner stdIn  = new Scanner(System.in);

        frame = new Frame(stdIn.nextInt());
        frame.read(stdIn);

        int n = stdIn.nextInt();
        for(int i=0;i<n;i++) {
            data.add(new Piece((int)stdIn.nextDouble()));
            data.get(i).read(stdIn);
        }
    }

    public boolean isFinished() {
//        if(data.size() > 1) return false;
//        else if(frame.equals(data.get(0))) return true;
        if(Tool.hasAccuracy(frame.totalLength(),0.0) == true) return true;
//        if(data.size() > 0 && frame.equals(data.get(0))) return true;
        return false;
    }

    void calcError() {
        for(int i=0;i<data.size();i++) {
            this.error += data.get(i).error;
        }
    }

        // fv_i : index of coords in the frame
        // dp_i : index of piece in the data
        // pv_i : index of coords in the piece
    public boolean canPut(int fv_i, int dp_i, int pv_i) {
        if(Tool.hasAccuracy(frame.getAngle(fv_i),data.get(dp_i).getAngle(pv_i)) == false)
            return false;
//        System.out.print("Yattaze");
        data.get(dp_i).rotate(Tool.calcAngle(data.get(dp_i).getV(pv_i), frame.getV(fv_i)));
        Vector v_sf = new Vector();
        for(int i=0;i<frame.num;i++) {
            Vector v_sp = v_sf.clone();
            for(int j=0;j<data.get(dp_i).num;j++) {
                if(Tool.hasIntersection(frame.getV(i), v_sf, data.get(dp_i).getV(j), v_sp) == true) {
//                    System.out.printf(" %s %s\n", frame.getV(i), data.get(dp_i).getV(j));
                    return false;
                }
                v_sp = new Vector(v_sp, data.get(dp_i).getV(j));
            }
            v_sf = new Vector(v_sf, frame.getV(i));
        }
//        System.out.println("Yattaze");
        return true;
    }

    public void put(int fv_i, int dp_i, int pv_i) {
        Piece p = data.get(dp_i);
        Vector pv = frame.getPV(fv_i);
        p.ref = pv.clone();
        Frame tmp = frame.clone();
//        Coord c = p.getCLBack(fv_i);
        int i_f = frame.getNextIdx(fv_i);
        int i_p = pv_i;
//        frame.remove(fv_i);
//        for(int i=0;i<p.num-1;i++,idx = p.getBackIdx(idx)) {
        for(int i=0;i<p.num;i++,i_p = p.getBackIdx(i_p)) {
            int t = frame.contain(p.getCW(i_p));

            if(t >= 0) {
                if(Tool.hasAccuracy(tmp.getAngle(tmp.contain(p.getCW(i_p))), p.getAngle(i_p))) {
//                    System.out.printf("case1 f_%d p_%d\n", t, i_p);
                    frame.remove(t);
                    i_f = t;
                }
                else {
//                    System.out.printf("case2 f_%d p_%d\n", t, i_p);
                    i_f = frame.getNextIdx(t);
                }
            }
            else {
//                System.out.printf("case3 f_%d p_%d\n", i_f, i_p);
                frame.add(i_f, new Coord(new Coord(pv), p.getPV(i_p)));
                i_f = frame.getNextIdx(i_f);
            }
/*
            int t;
//            if((t = frame.contain(c)) >=  0) {
            if((t = frame.contain(p.getCL(i_p))) >=  0) {
                frame.remove(t);
                if(Tool.hasAccuracy(frame.getAngle(t),p.getAngle(i_p)) == true) {
                    i_f = frame.getBackIdx(t);
                    continue;
                }
            }
            frame.add(i_f, new Coord(new Coord(pv), p.getPV(i_p)));
            i_f = frame.getNextIdx(i_f);
*/
        }
        fixed.add(p.clone());
        data.remove(dp_i);
    }

/*
    public void put(int fv_i, int dp_i, int pv_i) {
//        System.out.println(this.toString());
        Piece p = data.get(dp_i);
//        frame.add(fv_i,new Coord(frame.getCL(frame.getBackIdx(fv_i)), new Vector(frame.getVBack(fv_i),new Vector(p.getVBack(pv_i),Math.PI))));
        if(Tool.hasAccuracy(frame.getVBack(fv_i).length, p.getVBack(pv_i).length) == false)
            frame.add(fv_i,new Coord(frame.getCLBack(fv_i), new Vector(frame.getVBack(fv_i),new Vector(p.getVBack(pv_i),Math.PI))));
//        System.out.println(this.toString());
//        System.out.println(this.toStringForRead());
//        frame.add(fv_i+2,new Coord(frame.getCL(fv_i), new Vector(frame.getV(fv_i),new Vector(p.getV(pv_i),Math.PI))));
        if(Tool.hasAccuracy(frame.getV(fv_i).length, p.getV(pv_i).length) == false)
            frame.add(fv_i+2,new Coord(frame.getCL(fv_i+1), p.getV(pv_i)));
//        System.out.println(this.toString());
//        System.out.println(this.toStringForRead());
        frame.remove(fv_i+1);
//        System.out.println(this.toString());
//        System.out.println(this.toStringForRead());
        int p_i = p.getBackIdx(pv_i);
        for(int i=1;i<=data.get(dp_i).num-3;i++) {
            int tmp = p_i;
            p_i = p.getBackIdx(p_i);
            frame.add(fv_i+i, new Coord(frame.getCL(fv_i+i-1),new Vector(p.getCL(tmp),p.getCL(p_i))));
//            System.out.println(this.toString());
//            System.out.println(this.toStringForRead());
        }
        frame.num = frame.coords.size();
        Vector ref = new Vector();
        for(int i=0;i<fv_i;i++) {
            ref = new Vector(ref, frame.getV(i));
        }
        p.ref = ref.clone();
        fixed.add(p.clone());
        data.remove(dp_i);
    }
*/

    public String toStringForRead() {
        String s = "";
        s += frame.toStringForRead() + "\n";
        s += fixed.size();
        for(int i=0;i<fixed.size();i++) {
            s += "\n" + fixed.get(i).toStringForRead();
        }
        return s;
    }

    public String toExString() {
        String s = "";
        s += "f:\n";
        s += frame.toString() + "\n";
        s += "p:\n";
        for(int i=0;i<data.size();i++)
            s += String.format("<%d> %s\n",i,data.get(i).toExString());
        return s;
    }

    public String toString() {
        String s = "";
        s += "f:\n";
        s += frame.toString() + "\n";
        s += "p:\n";
        for(int i=0;i<data.size();i++)
            s += String.format("<%d> %s\n",i,data.get(i).toString());
        return s;
    }
}
