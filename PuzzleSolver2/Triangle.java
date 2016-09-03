//////////////////////////////////////////////////////////////
//  Triangleクラス：ピースを形成する最小の要素となる三角形を表すクラス
//
//      親クラス：Figure_framework
//      インターフェース：Figure_interface
//
//////////////////////////////////////////////////////////////

import java.util.Scanner;

public class Triangle extends Figure_framework implements Figure_interface {
    public Triangle() {
        super(3);
    }

    public Triangle(Coord a, Coord b, Coord c) {
        this();
        // .clone()したほうがいいだろうか？
        coords[0] = a;
        coords[1] = b;
        coords[2] = c;
    }

    public Triangle(Coord[] tmp) {
        this(tmp[0],tmp[1],tmp[2]);
        if(tmp.length != 3) throw new IllegalArgumentException("三角形ではありません");
    }

    public Triangle(Piece p) {
        this(p.coords[0],p.coords[1],p.coords[2]);
        if(p.num != 3) throw new IllegalArgumentException("三角形ではありません");
    }

    public Triangle(Triangle copy) {
        super(copy);
    }

    public Triangle clone() {
        return new Triangle(this);
    }

    public boolean isInArea(Coord c) {
        for(int i=0;i<3;i++) {
            if((new Line(getVec(i),getCrd(i))).isOnTheRight(c) == false) return false;
        }
        return true;
    }

    @Override
    public double calcArea() {
        Vector a = getVec(0);
        Vector b = getVec(2).reverse();
        return (a.length() * b.length()) / 2 * Math.sin(Tool.calcCosAngle(a,b));
    }

    @Override
    public void read(Scanner stdIn) {}

    //TODO
    @Override
    public String toString() {
        String t = "";
//        t += getVec(0) +"\t"+ getVec(1) +"\t"+ getVec(2);
        t += getCrd(0) +"\t"+ getCrd(1) +"\t"+ getCrd(2);
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
