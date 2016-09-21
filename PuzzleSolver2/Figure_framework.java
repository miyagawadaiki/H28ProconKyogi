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
    State state;

    public Figure_framework(int num) {
        if(num < 3) throw new IllegalArgumentException("多角形ではありません:" + num);
        this.num = num;
        coords = new Coord[num];
        for(Coord c : coords) c = new Coord();
        state = new State();
    }

    public Figure_framework(Figure_framework copy) {
        this.num = copy.num;
        this.coords = copy.coords.clone();
        this.state = copy.state.clone();
    }

    public Figure_framework clone() {
        return new Figure_framework(this);
    }

    public void move(State state) {
        this.state = state.clone();
    }

    public int getIdxB(int n) { return (n+num-1)%num; }
    public int getIdxN(int n) { return (n+1)%num; }

    public Coord getCrd(int n) {  return coords[n]; }
    public Coord getCrdB(int n) { return getCrd(getIdxB(n)); }
    public Coord getCrdN(int n) { return getCrd(getIdxN(n)); }

    public Coord getWCrd(int n) {  return getCrd(n).move(state); }
    public Coord getWCrdB(int n) { return getWCrd(getIdxB(n)); }
    public Coord getWCrdN(int n) { return getWCrd(getIdxN(n)); }

    public Vector getVec(int n) {  return new Vector(getCrd(n), getCrdN(n)); }
    public Vector getVecB(int n) { return getVec(getIdxB(n)); }
    public Vector getVecN(int n) { return getVec(getIdxN(n)); }

    public Vector getWVec(int n) {  return getVec(n).move(state); }
    public Vector getWVecB(int n) { return getWVec(getIdxB(n)); }
    public Vector getWVecN(int n) { return getWVec(getIdxN(n)); }

    public Line getLine(int n) {  return new Line(getVec(n),getCrd(n)); }
    public Line getLineB(int n) { return getLine(getIdxB(n)); }
    public Line getLineN(int n) { return getLine(getIdxN(n)); }

    public Line getWLine(int n) {  return new Line(getWVec(n),getWCrd(n)); }
    public Line getWLineB(int n) { return getWLine(getIdxB(n)); }
    public Line getWLineN(int n) { return getWLine(getIdxN(n)); }

    public double getAngle(int n) { return Tool.calcAngle(getVecB(n),getVec(n)); }

    public boolean isDent(int n) { return getAngle(n) <= 0; }

    public double maxLength() {
        double max = 0.0;
        for(Coord c : coords) {
            if(c.dist() > max) max = c.dist();
        }
        return max;
    }

    public double allLength() {
        double sum = 0.0;
        for(int i=0;i<num;i++) {
            sum += getVec(i).length();
        }
        return sum;
    }

    public int searchCrd(Coord c) {
        for(int i=0;i<num;i++)
            if(c.equals(coords[i])) return i;
        return -1;
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
