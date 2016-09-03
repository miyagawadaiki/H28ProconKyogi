//////////////////////////////////////////////////////////
//  Frameクラス：枠を表現するオブジェクト
//
//      親クラス：Piece
//      インターフェース：Figure_interface
//
//      ArrayList<Vector> list : 頂点編集作業用のリスト
//
//////////////////////////////////////////////////////////

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Frame extends Piece implements Figure_interface {
    ArrayList<Coord> list;

    public Frame(int num) {
        super(num);
        list = new ArrayList<Coord>();
    }

    public void initList() {
        for(int i=0;i<num;i++) {
            list.add(getCrd(i));
        }
    }

    //TODO
    public boolean canPut(Piece p) {
        return false;
    }

    //TODO
    public Frame put(Piece p) {
        return new Frame(3);
    }

    public boolean isInArea(Coord c) {
        for(Triangle t : triangles) {
            if(t.isInArea(c) == true) return true;
        }
        return false;
    }

    public boolean isInArea(Piece p) {
        if(this.maxLength() < p.maxLength()) return false;
        if(this.calcArea() < p.calcArea()) return false;
        for(Coord c : coords) {
            if(this.isInArea(c) == false) return false;
        }
        for(int i=0;i<this.num;i++) {
            for(int j=0;j<p.num;j++) {
                if(this.getLine(i).isCross(p.getLine(j)) == true) return false;
            }
        }
        return true;
    }

    @Override
    public double calcArea() {
        return super.calcArea();
    }

    //TODO
    @Override
    public void read(Scanner stdIn) {
        super.read(stdIn);
        initList();
    }

    //TODO
    @Override
    public String toString() {
        return super.toString();
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
