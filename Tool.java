public class Tool {
    static double thre = 0.05;

        //真値と誤差を比較して許容範囲に入っているか調べる
    static boolean hasAccuracy(double m, double m_t) {
//        m_t = Math.abs(m_t) * thre;
//        System.out.println(Math.abs(m - m_t) + " " + Math.abs(m_t)*thre + " " + ((Math.abs(m-m_t)<Math.abs(m_t)*thre)?"T":"F"));
//        if(Math.abs(m-m_t)/Math.abs(m_t) < 0.1) System.out.println(Math.abs(m - m_t)/Math.abs(m_t) + " " + Math.abs(m - m_t));
//        System.out.println((Math.abs(m - m_t)/Math.abs(m_t) < thre) + " " + (Math.abs(m - m_t) < thre));
//        return Math.abs(m - m_t)/Math.abs(m_t) < thre;
        return Math.abs(m - m_t) < thre;
    }

    static boolean hasIntersection(Vector v, Vector p_v, Vector w, Vector p_w) {
        double ds, dg;
        if(v.dx == 0.0) {
//            if(v.dy > 0) {
//                if()
//            }
            ds = v.dx - p_v.dx;
            dg = w.dx - p_v.dx;
            return (ds * dg != 0) && ((ds > 0)?1:-1) * ((dg > 0)?1:-1) < 0;
        }
        else if(v.dy == 0.0) {
            ds = v.dy - p_v.dy;
            dg = w.dy - p_v.dy;
            return (ds * dg != 0) && ((ds > 0)?1:-1) * ((dg > 0)?1:-1) < 0;
        }
//        else {
//
//        }
        return false;
    }

///*
    static boolean hasOverlap(Vector v, Vector p_v, Vector w, Vector p_w) {
        double a = v.dy / v.dx;
        double b = p_v.dy - a * p_v.dx;
        Vector t = new Vector(w,p_w);
        double ds = 0, dg = 0;
        ds = p_w.dy - a * p_w.dx - b;   dg = t.dy - a * t.dx - b;
        if(!hasAccuracy(ds * dg,0) && ((ds > 0)?1:-1) * ((dg > 0)?1:-1) > 0)
            return false;
        a = w.dy / w.dx;
        b = p_w.dy - a * p_w.dx;
        t = new Vector(v,p_v);
        ds = p_v.dy - a * p_v.dx - b;   dg = t.dy - a * t.dx - b;
        return (!hasAccuracy(ds * dg,0) && ((ds > 0)?1:-1) * ((dg > 0)?1:-1) < 0);
    }
//*/

    static double calcArea(Vector a, Vector b) {
        return Math.abs(a.length * b.length * Math.sin(calcAbsAngle(a,b))) / 2;
    }

        // 2つのベクトル間の角度をラジアンで返す
        // 範囲：0 ~ π
    static double calcCosAngle(Vector a, Vector b) {
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
        double d_v = calcCosAngle(v,new Vector(1.0,0.0));
        if(v.dy >= 0) return d_v;
        else return -1 * d_v;
    }

        // 二つのベクトル間の角度を返す
        // 範囲：0 ~ 2π
    static double calcAbsAngle(Vector v_s, Vector v_g) {
        double tmp = calcAngle(v_s, v_g);
        if(tmp >= 0) return tmp;
        return 2 * Math.PI + tmp;
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

        //2つのベクトルが並行であるかどうかを返す
    static boolean isParallel(Vector a, Vector b) {
        return hasAccuracy(Math.abs(a.dx * b.dy) - Math.abs(a.dy * b.dx),0.0);
    }

    static boolean canFuse(Piece p, Piece q, int p_c, int q_c) {
        Piece x = new Piece(q, calcAngle(p.getV(q_c), q.getV(q_c))+Math.PI);
        int x_c = q_c;
        Vector p_cv = p.getV(p_c), x_cv = x.getV(x_c);
//        Vector p_bv = p.getVBack(p_c), x_bv = x.getVBack(x_c);
//        Vector p_nv = p.getVNext(p_c), x_nv = x.getVNext(x_c);

        if(hasAccuracy(p_cv.length,x_cv.length) == false) return false;
//            System.out.printf("%s %s %s %s %s %s\n", p_cv, x_cv, p_bv, x_nv, p_nv, x_bv);
//        double tmp1 = calcCosAngle(p_bv,x_nv);
//        double tmp2 = calcCosAngle(p_nv,x_bv);
//            System.out.println(tmp1 + " " + tmp2);
//            if(hasAccuracy(tmp1,0.0) &&
//               hasAccuracy(tmp2,0.0)) {
//        if(hasAccuracy(tmp1,tmp2) == true) {
        else if(hasAccuracy(p.getAngle(p_c) + x.getAngle(x.getNextIdx(x_c)), Math.PI) &&
                hasAccuracy(p.getAngle(p.getNextIdx(p_c)) + x.getAngle(x_c), Math.PI)) {
//            if(isParallel(p_bv,x_nv) && isParallel(p_nv,x_bv)) {
//                System.out.println("match!");
            return true;
        }
        else {
            return false;
        }
    }

    static double correctDouble(double d) {
            // 通常doubleの小数点以下桁数は14 現在：14
        double tmp = d + 0.00000000000005;
        long hoge = (long)(tmp * 10000000000000L);
        return hoge / 10000000000000.0;
    }

    static Piece fuse(Piece p1, Piece p2, int p1_idx, int p2_idx) {
        return new Piece(p1,p2,p1_idx,p2_idx,p1.num-3+p2.num-3+2);
    }
}
