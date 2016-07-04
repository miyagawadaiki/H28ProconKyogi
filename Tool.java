public class Tool {
    static double thre = 0.02;

        //2つのベクトル間の角度をラジアンで返す
    static double calcRadAngle(Vector a, Vector b) {
//        return Math.acos((a.dx * b.dx + a.dy * b.dy) / (a.length * b.length));
        double c = (a.dx * b.dx + a.dy * b.dy) / (a.length * b.length);
//        System.out.println(c);
        return Math.acos(c);
    }

        //x軸からの角度を返す
    static double calcTheta(Vector v) {
        double d_v = calcRadAngle(v,new Vector(1.0,0.0));
        if(v.dy >= 0) return d_v;
        else return Math.PI * 2 - d_v;
    }

    static double calcTheta(Vector a, Vector b) {
        return calcTheta(b) - calcTheta(a);
    }

    static double calcLinalizeAngle(Vector v, Vector x) {
        double d_a = calcRadAngle(v,x);
        double v_a = calcTheta(v);
        double x_a = calcTheta(x);
        if(v_a > x_a) return d_a - Math.PI;
        else return Math.PI - d_a;
    }

    static double calcAngle(Vector v1, Vector v2) {
        double t12 = calcRadAngle(v1,v2);
        double t1 = calcTheta(v1);
        double t2 = calcTheta(v2);
        double theta = t2-t1;
//        if(t2 - t1 <= Math.PI) return t2-t1;
//        else return t1-t2;
        return calcDTheta(theta);
    }

    static double calcDTheta(Vector v) {
        double theta = calcTheta(v);
        return calcDTheta(theta);
    }

    static double calcDTheta(double theta) {
        return theta - (int)(theta / Math.PI) * 2 * Math.PI;
    }

        //真値と誤差を比較して許容範囲に入っているか調べる
    static boolean hasAccuracy(double m, double m_t) {
        return Math.abs(m - m_t) < thre;
    }

        //2つのベクトルが並行であるかどうかを返す
    static boolean isParallel(Vector a, Vector b) {
        return (Math.abs(a.dx * b.dy) - Math.abs(a.dy * b.dx)) == 0;
    }

    static boolean checkFitness(Piece p, Piece x, int p_c, int x_c) {
        Vector p_cv = p.get(p_c), x_cv = x.get(x_c);
        Vector p_bv = p.getBack(p_c), x_bv = x.getBack(x_c);
        Vector p_nv = p.getNext(p_c), x_nv = x.getNext(x_c);

        if(hasAccuracy(p_cv.length,x_cv.length) == false)
            return false;
        else {
//            System.out.printf("%s %s %s %s %s %s\n", p_cv, x_cv, p_bv, x_nv, p_nv, x_bv);
            if(hasAccuracy(calcRadAngle(p_bv,x_nv),0.0) &&
               hasAccuracy(calcRadAngle(p_nv,x_bv),0.0)) {
//            if(isParallel(p_bv,x_nv) && isParallel(p_nv,x_bv))
//                System.out.println("match!");
                return true;
            }
            else {
                return false;
            }
        }
    }

    static Piece fuse(Piece p1, Piece p2, int p1_idx, int p2_idx) {
        return new Piece(p1,p2,p1_idx,p2_idx,p1.num-3+p2.num-3+2,true);
    }

    static Piece join(Piece p1, Piece p2, int p1_idx, int p2_idx) {
        return new Piece(p1,p2,p1_idx,p2_idx,p1.num+p2.num-2,false);
    }
}
