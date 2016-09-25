/////////////////////////////////////////////////////////////
//  Pieceクラス：ピースを表すクラス
//
//      親クラス：Figure_framework
//      インターフェース：Figure_interface
//
//      Triangle[] triangles: 三角形領域に分ける
//
/////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Scanner;

public class Piece extends Figure_framework implements Figure_interface {
    ArrayList<Triangle> triangles;
    int id;

    public Piece(int n) {
        super(n);
        id = -1;
        triangles = new ArrayList<Triangle>(n-2);
    }

    public Piece(int n, int id) {
        this(n);
        this.id = id;
    }

    //TODO
    public Piece(Coord[] tmp) {
        this(tmp.length);
        coords = tmp.clone();
        triangulate();
        System.out.println(this);
    }

    public Piece(Piece copy) {
        super(copy);
        this.id = copy.id;
        triangles = new ArrayList<Triangle>();
        for(Triangle t : copy.triangles)
            this.triangles.add(t);
    }

    public Piece clone() {
        return new Piece(this);
    }

    public void move(State state) {
        super.move(state);
        for(Triangle t : triangles)
            t.move(state);
    }

        // ピースを三角形分割する
        // コンストラクタを介した再帰関数になっている
    public void triangulate() {
//        System.out.println(this);
        if(this.num == 3) {
            triangles.add(this.convTriangle());
            return;
        }
        else {
            int s = 0;
            while(isDent(s) == true) s = getIdxB(s);
            Triangle tri = new Triangle(slice(getIdxB(s),getIdxN(s)));
            Line line = new Line(tri.getCrdB(s),tri.getCrdN(s));
            int idx = getIdxN(getIdxN(s));
            int max_idx = -1;
            double max = -1.0;
            for(int i=0;i<num-3;i++,idx = getIdxN(idx)) {
                if(tri.isInArea(getCrd(idx)) == true) {
                    double t = line.calcDist(getCrd(idx));
                    if(t > max) {
                        max = t;
                        max_idx = idx;
                    }
                }
            }
            if(max_idx == -1) {
//                System.out.println("ok");
                triangles.add(tri.clone());
                addTriList(getPiece(getIdxN(s),getIdxB(s)));
            }
            else {
//                System.out.println("no:" + max_idx);
                addTriList(getPiece(s,max_idx));
                addTriList(getPiece(max_idx,s));
            }
        }
    }

    public void addTriList(Piece p) {
        for(int i=0;i<p.triangles.size();i++) {
            this.triangles.add(p.triangles.get(i));
        }
    }

    public Triangle convTriangle() {
        return new Triangle(this);
    }

    public Piece getPiece(int a, int b) {
        return new Piece(slice(a,b));
    }

    public Coord[] slice(int a, int b) {
        int len = 1 + ((b>a)?(b-a):(b+num-a));
        Coord[] tmp = new Coord[len];
        int idx = a;
        for(int i=0;i<len;i++) {
            tmp[i] = getCrd(idx);
            idx = getIdxN(idx);
        }
        return tmp;
    }

    public int searchOnLine(Coord c) {
        for(int i=0;i<num;i++) {
            if(getWLine(i).isOnLine(c)) return i;
        }
        return -1;
    }

    @Override
    public double calcArea() {
        double sum = 0.0;
        for(Triangle tri : triangles) {
            sum += tri.calcArea();
        }
        return sum;
    }

    @Override
    public void read(Scanner stdIn) {
        Coord c = new Coord(stdIn.nextDouble(), stdIn.nextDouble());
        Vector v = c.toVector().reverse();
        coords[0] = c.translate(v);
        for(int i=1;i<num;i++) {
            Coord crd = new Coord(stdIn.nextDouble(), stdIn.nextDouble());
            coords[i] = crd.translate(v);
        }
        triangulate();
    }

    //TODO
    @Override
    public String toString() {
        String t = "";
        for(int i=0;i<num;i++) {
//            t += getVec(i).toString() + "\t";
            t += getCrd(i).toString() + "\t";
        }
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
        t += num + "\t" + id + "\n";
        for(int i=0;i<num-1;i++) {
            t += getWCrd(i).toStringForRead() + "\n";
        }
        t += getWCrd(num-1).toStringForRead();
        return t;
    }

    public String toStringByTriangle() {
        String t = "";
        t += this.toString() + "\n";
        for(Triangle tri : triangles)
            t += "\t" + tri.toString() + "\n";
        return t;
    }
}
