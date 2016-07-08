public class Tool {
    static double thre = 0.05;

        // 2つのベクトル間の角度をラジアンで返す
        // 範囲：0 ~ π
    static double calcAbsAngle(Vector a, Vector b) {
//        return Math.acos((a.dx * b.dx + a.dy * b.dy) / (a.length * b.length));
        double c = (a.dx * b.dx + a.dy * b.dy) / (a.length * b.length);
//        System.out.println(c);
        c = correctDouble(c);
//        System.out.println(c);
//        if(c > 1 && c - 1 < 0.0001) c = 1.0;
        return correctDouble(Math.acos(c));
    }

        // x軸からの角度を返す
        // 範囲：-π ~ π
    static double calcTheta(Vector v) {
        double d_v = calcAbsAngle(v,new Vector(1.0,0.0));
        if(v.dy >= 0) return d_v;
        else return -1 * d_v;
    }

    static double calcTheta(Vector a, Vector b) {
        return calcTheta(b) - calcTheta(a);
    }

    static double calcLinalizeAngle(Vector v, Vector x) {
        double d_a = calcAbsAngle(v,x);
        double v_a = calcTheta(v);
        double x_a = calcTheta(x);
//        System.out.println(d_a + "\t" + v_a + "\t" + x_a);
        if(v_a > x_a) return d_a - Math.PI;
        else return Math.PI - d_a;
    }

        // 基準ベクトルから目標ベクトルまでの角移動量を返す
        // 範囲：-π ~ π
    static double calcAngle(Vector v_s, Vector v_g) {
        double t_s = calcTheta(v_s);
        Vector tmp = new Vector(v_g, -t_s);
//        if(t2 - t1 <= Math.PI) return t2-t1;
//        else return t1-t2;
        return calcTheta(tmp);
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
//        m_t = Math.abs(m_t) * thre;
//        System.out.println(Math.abs(m - m_t) + " " + Math.abs(m_t)*thre + " " + ((Math.abs(m-m_t)<Math.abs(m_t)*thre)?"T":"F"));
//        if(Math.abs(m-m_t)/Math.abs(m_t) < 0.1) System.out.println(Math.abs(m - m_t)/Math.abs(m_t) + " " + Math.abs(m - m_t));
//        System.out.println((Math.abs(m - m_t)/Math.abs(m_t) < thre) + " " + (Math.abs(m - m_t) < thre));
//        return Math.abs(m - m_t)/Math.abs(m_t) < thre;
        return Math.abs(m - m_t) < thre;
    }

        //2つのベクトルが並行であるかどうかを返す
    static boolean isParallel(Vector a, Vector b) {
        return hasAccuracy(Math.abs(a.dx * b.dy) - Math.abs(a.dy * b.dx),0.0);
    }

    static boolean checkFitness(Piece p, Piece x, int p_c, int x_c) {
        Vector p_cv = p.get(p_c), x_cv = x.get(x_c);
        Vector p_bv = p.getBack(p_c), x_bv = x.getBack(x_c);
        Vector p_nv = p.getNext(p_c), x_nv = x.getNext(x_c);

        if(hasAccuracy(p_cv.length,x_cv.length) == false)
            return false;
        else {
//            System.out.printf("%s %s %s %s %s %s\n", p_cv, x_cv, p_bv, x_nv, p_nv, x_bv);
            double tmp1 = calcAbsAngle(p_bv,x_nv),
                   tmp2 = calcAbsAngle(p_nv,x_bv);
//            System.out.println(tmp1 + " " + tmp2);
//            if(hasAccuracy(tmp1,0.0) &&
//               hasAccuracy(tmp2,0.0)) {
            if(hasAccuracy(tmp1,tmp2) == true) {
//            if(isParallel(p_bv,x_nv) && isParallel(p_nv,x_bv)) {
//                System.out.println("match!");
                return true;
            }
            else {
                return false;
            }
        }
    }

    static double correctDouble(double d) {
            // 通常doubleの小数点以下桁数は14 現在：14
        double tmp = d + 0.00000000000005;
        long hoge = (long)(tmp * 10000000000000L);
        return hoge / 10000000000000.0;
    }

    static Piece fuse(Piece p1, Piece p2, int p1_idx, int p2_idx) {
        return new Piece(p1,p2,p1_idx,p2_idx,p1.num-3+p2.num-3+2,true);
    }

    static Piece join(Piece p1, Piece p2, int p1_idx, int p2_idx) {
        return new Piece(p1,p2,p1_idx,p2_idx,p1.num+p2.num-2,false);
    }
}
