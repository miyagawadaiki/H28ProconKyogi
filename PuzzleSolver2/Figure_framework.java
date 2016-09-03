////////////////////////////////////////////////////////
//  Figure_frameworkクラス：図形系オブジェクトに継承してもらう
//
//      int num         : 頂点の数
//      Vector[] coords  : 頂点の座標
//      Coord ref       : coords[0]の相対的位置ベクトル
//      double rev_ang  : 回転角
//
////////////////////////////////////////////////////////

import java.util.Scanner;

public class Figure_framework implements Figure_interface {
    int num;
    Coord[] coords;
    Vector ref;
    double rev_ang;

    public Figure_framework(int num) {
        if(num < 3) throw new IllegalArgumentException("多角形ではありません:" + num);
        this.num = num;
        coords = new Coord[num];
        for(Coord c : coords) c = new Coord();
        ref = new Vector();
        rev_ang = 0.0;
    }

    public Figure_framework(Figure_framework copy) {
        this.num = copy.num;
        this.coords = copy.coords.clone();
        this.ref = copy.ref.clone();
        this.rev_ang = copy.rev_ang;
    }

    public Figure_framework clone() {
        return new Figure_framework(this);
    }

    public void translate(Vector v) {
        ref = ref.plus(v);
    }

    public void rotate(double theta) {
        rev_ang += theta;
    }

    public void move(Vector v, double theta) {
        translate(v);
        rotate(theta);
    }

    public int getIdxB(int n) { return (n+num-1)%num; }
    public int getIdxN(int n) { return (n+1)%num; }

    public Coord getCrd(int n) {  return new Coord(coords[n],rev_ang); }
    public Coord getCrdB(int n) { return coords[getIdxB(n)]; }
    public Coord getCrdN(int n) { return coords[getIdxN(n)]; }

    public Vector getVec(int n) {  return new Vector(new Vector(getCrd(n), getCrdN(n)),rev_ang); }
    public Vector getVecB(int n) { return getVec(getIdxB(n)); }
    public Vector getVecN(int n) { return getVec(getIdxN(n)); }

    public Line getLine(int n) {  return new Line(getVec(n),getCrd(n)); }
    public Line getLineB(int n) { return getLine(getIdxB(n)); }
    public Line getLineN(int n) { return getLine(getIdxN(n)); }

    public double getAngle(int n) { return Tool.calcAngle(getVecB(n),getVec(n)); }

    public boolean isDent(int n) { return getAngle(n) <= 0; }

    public double maxLength() {
        double max = 0.0;
        for(Coord c : coords) {
            if(c.dist() > max) max = c.dist();
        }
        return max;
    }

    @Override
    public double calcArea() { return 0.0; }

    //TODO
    @Override
    public void read(Scanner stdIn) {}

    //TODO
    @Override
    public String toString() {
        String t = "";
        return t;
    }

    //TODO
    @Override
    public String toStringAll() {
        String t = "";
        return t;
    }

    //TODO
    @Override
    public String toStringForRead() {
        String t = "";
        return t;
    }
}
