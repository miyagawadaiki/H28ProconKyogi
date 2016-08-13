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
        for(Piece q : copy.fixed)
            this.fixed.add(q.clone());
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
        if(data.size() > 1) return false;
        else if(frame.equals(data.get(0))) return true;
//        if(Tool.hasAccuracy(frame.totalLength(),0.0) == true) return true;
//        if(data.size() > 0 && frame.equals(data.get(0))) return true;
        return false;
    }

    void calcError() {
        for(int i=0;i<data.size();i++) {
            this.error += data.get(i).error;
        }
    }

    public Vector getWorldPV(int fv_i, int dp_i, int pv_i) {
        return new Vector(frame.getPV(fv_i), data.get(dp_i).getPV(pv_i).reverse());
    }

        // fv_i : index of coords in the frame
        // dp_i : index of piece in the data
        // pv_i : index of coords in the piece
    public boolean canPut(int fv_i, int dp_i, int pv_i) {
//        System.out.printf("%f\t%f\n", frame.getAngle(fv_i), data.get(dp_i).getAngle(pv_i));
        if(Tool.hasAccuracy(frame.getAngle(fv_i),data.get(dp_i).getAngle(pv_i)) == false)
            return false;
//        System.out.print("Yattaze in Solver.canPut():1");
        Piece p = new Piece(data.get(dp_i), Tool.calcAngle(data.get(dp_i).getV(pv_i), frame.getV(fv_i)));
        System.out.println("theta=" + Tool.calcAngle(data.get(dp_i).getV(pv_i), frame.getV(fv_i)));
        p.p_vec = getWorldPV(fv_i, dp_i, pv_i);
        System.out.println(p.toExString());
//        System.out.println(this.toExString());
//        data.get(dp_i).rotate(Tool.calcAngle(data.get(dp_i).getV(pv_i), frame.getV(fv_i)));


//        for(int i=0;i<p.num;i++) {
//            if(isInArea(p.getV(i), p.getPVW(i)) == false) {
//                System.out.println("Missed : " + p.getV(i));
//                return false;
//            }
//        }
///*
        for(int i=0;i<frame.num;i++) {
//            Vector v_sp = v_sf.clone();
//            for(int j=0;j<data.get(dp_i).num;j++) {
            for(int j=0;j<p.num;j++) {
//                if(Tool.hasOverlap(frame.getV(i), v_sf, data.get(dp_i).getV(j), v_sp) == true) {
//                if(Tool.hasIntersection(frame.getV(i), frame.getPV(i), p.getV(j), new Vector(p.getCW(j))) == true) {
                if(Tool.hasOverlap(frame.getV(i), frame.getPV(i), p.getV(j), new Vector(p.getCW(j))) == true) {
                    System.out.printf(" %s %s %s %s\n", frame.getV(i), frame.getPV(i), p.getV(j), new Vector(p.getCW(j)));
                    return false;
                }
//                v_sp = new Vector(v_sp, data.get(dp_i).getV(j));
//                v_sp = new Vector(v_sp, p.getV(j));
//                v_sp = new Vector(v_sp, p.getPV(j));
            }
//            v_sf = new Vector(v_sf, frame.getV(i));
//            v_sf = new Vector(v_sf, frame.getV(i));
        }


//*/
//        System.out.println("Yattaze");
        return true;
    }

    public boolean isInArea(Piece p) {
        for(int i=0;i<p.num;i++) {
            Vector pv = p.getV(i), pp = p.getPVW(i);
            double scx = pp.dx, scy = pp.dy;
            double gcx = pp.dx + pv.dx, gcy = pp.dy + pv.dy;

            for(int j=0;j<frame.num;j++) {
                Vector fv = frame.getV(j), fp = frame.getPV(j);
                if(fv.dx == 0) {
                    if(fv.dy > 0) {
                        if(scx <= fp.dx && gcx <= fp.dx) continue;
                        System.out.println("case1:" + pv + pp + fv + fp);
                        return false;
                    }
                    else if(fv.dy < 0) {
                        if(scx >= fp.dx && gcx >= fp.dx) continue;
                        System.out.println("case2:" + pv + pp + fv + fp);
                        return false;
                    }
                }
                else if(fv.dy == 0) {
                    if(fv.dx > 0) {
                        if(scy >= fp.dy && gcy >= fp.dy) continue;
                        System.out.println("case3:" + pv + pp + fv + fp);
                        return false;
                    }
                    else if(fv.dx < 0) {
                        if(scy <= fp.dy && gcy <= fp.dy) continue;
                        System.out.println("case4:" + pv + pp + fv + fp);
                        return false;
                    }
                }
                else {
                    double a = fv.dy / fv.dx;
                    double b = fp.dy - a * fp.dx;
                    double ds = scy - a * scx - b;
                    double dg = gcy - a * gcx - b;
                    if(fv.dx > 0) {
                        if(ds >= 0 && dg >= 0) continue;
                        System.out.println("case5:" + pv + pp + fv + fp);
                        return false;
                    }
                    else if(fv.dx < 0) {
                        if(ds <= 0 && dg <= 0) continue;
                        System.out.println("case6:" + pv + pp + fv + fp);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void put(int fv_i, int dp_i, int pv_i) {
        data.get(dp_i).rotate(Tool.calcAngle(data.get(dp_i).getV(pv_i), frame.getV(fv_i)));
        Piece p = data.get(dp_i);
//        p.p_vec = f_pv.clone();
        p.p_vec = getWorldPV(fv_i, dp_i, pv_i);
//        System.out.println(p.toExString());
//        System.out.println(this.toExString());
        Frame tmp = frame.clone();
//        System.out.println(frame +"\n"+ tmp);
//        Coord c = p.getCLBack(fv_i);
        int i_f = frame.getNextIdx(fv_i);
        int i_p = pv_i;
//        frame.remove(fv_i);
//        for(int i=0;i<p.num-1;i++,idx = p.getBackIdx(idx)) {
        for(int i=0;i<p.num;i++,i_p = p.getBackIdx(i_p)) {
            int t = frame.contain(p.getCW(i_p));
//            int t = tmp.contain(p.getCW(i_p));

            if(t >= 0) {
                if(Tool.hasAccuracy(tmp.getAngle(tmp.contain(p.getCW(i_p))), p.getAngle(i_p))) {
//                    System.out.printf("case1 f_%d p_%d\n", t, i_p);
                    frame.remove(t);
                    i_f = t;
//                    System.out.println(frame);
                }
                else {
//                    System.out.printf("case2 f_%d p_%d\n", t, i_p);
                    i_f = frame.getNextIdx(t);
//                    System.out.println(frame);
                }
            }
            else {
//                System.out.printf("case3 f_%d p_%d\n", i_f, i_p);
                frame.add(i_f, new Coord(new Coord(p.p_vec), p.getPV(i_p)));
                i_f = frame.getNextIdx(i_f);
//                System.out.println(frame);
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
        Vector p_vec = new Vector();
        for(int i=0;i<fv_i;i++) {
            p_vec = new Vector(p_vec, frame.getV(i));
        }
        p.p_vec = p_vec.clone();
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
        s += frame.toExString() + "\n";
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
