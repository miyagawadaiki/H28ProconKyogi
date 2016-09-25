/////////////////////////////////////////////////////////////
//  Toolクラス；staticのメソッド群を持つのでどこからでも参照しやすい
/////////////////////////////////////////////////////////////
public class Tool {

    static double threshold = 0.1;
        // 2つのベクトル間の角度をラジアンで返す
        // 範囲：0 ~ π
    static double calcCosAngle(Vector a, Vector b) {
//        return Math.acos((a.dx * b.dx + a.dy * b.dy) / (a.length * b.length));
        double c = (a.dx * b.dx + a.dy * b.dy) / (a.length() * b.length());
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

    static double correctDouble(double d) {
            // 通常doubleの小数点以下桁数は14 現在：14
        double tmp = d + 0.00000000000005;
        long hoge = (long)(tmp * 10000000000000L);
        return hoge / 10000000000000.0;
    }

    static double round(double d) {
        return ((int)((d+0.000005)*100000))/100000.0;
    }

    static boolean equals(double a, double b) {
        return round(a) == round(b);
    }

    static boolean nearlyEquals(double a, double b) {
        return Math.abs(a-b) <= threshold;
    }

    static boolean largerThan(double a, double b) {
        return a > b && !(equals(a,b));
    }

    static boolean smallerThan(double a, double b) {
        return a < b && !(equals(a,b));
    }

    static boolean LTorE(double a, double b) {
        return a > b && equals(a,b);
    }

    static boolean STorE(double a, double b) {
        return a < b && equals(a,b);
    }

    static boolean nearlyLT(double a, double b) {
        return a > b && !(nearlyEquals(a,b));
    }

    static boolean nearlyST(double a, double b) {
        return a < b && !(nearlyEquals(a,b));
    }

    static boolean nearlyLTorE(double a, double b) {
        return a > b && nearlyEquals(a,b);
    }

    static boolean nearlySTorE(double a, double b) {
        return a < b && nearlyEquals(a,b);
    }

}
