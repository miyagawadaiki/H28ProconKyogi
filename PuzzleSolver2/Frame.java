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
//    ArrayList<Coord> list;

    public Frame(int num) {
        super(num);
//        list = new ArrayList<Coord>();
    }

    public Frame(Coord[] coords) {
        super(coords);
//        list = new ArrayList<Coord>();
    }

    public Frame(Frame copy) {
        super(copy);
    }

    public Frame clone() {
        return new Frame(this);
    }

    public void initList() {
        for(int i=0;i<num;i++) {
//            list.add(getCrd(i));
        }
    }

    public boolean canPut(Piece p, State s, Coord join_crd) {
        Piece clone = p.clone();
        clone.move(s);
        if(this.maxLength() < clone.maxLength()) return false;
//                            System.out.println("\tf");
        if(this.calcArea() < clone.calcArea()) return false;
//                            System.out.println("\to");
        if(!this.isInArea(clone)) return false;
                            System.out.println("\to!");
        return true;
    }

    public double evaluate(Piece p, State s, Coord join_crd) {
        double ret = 0.0;
        Piece clone = p.clone();
        clone.move(s);
                            System.out.println("\t"+join_crd);
                            System.out.println("\t"+clone.toStringForRead());
        int join_idx_f = this.searchCrd(join_crd);
        int join_idx_p = clone.searchWCrd(join_crd);
                            System.out.println("\t"+join_idx_f);
                            System.out.println("\t"+join_idx_p);

        if(Tool.equals(this.getAngle(join_idx_f),clone.getAngle(join_idx_p)))
            ret += 1.0;

        return ret;
    }

    public ArrayList<Frame> put(Piece p_origin, State s, Coord join_crd) {
        ArrayList<Frame> ret = new ArrayList<Frame>();
        p_origin.move(s);
        int join_idx_f = this.searchCrd(join_crd);
        int join_idx_p = p_origin.searchWCrd(join_crd);
        ArrayList<Coord> list = new ArrayList<Coord>();
        for(Coord c : this.slice(getIdxN(join_idx_f),getIdxB(join_idx_f)))
            list.add(c);
        int idx_head = p_origin.getIdxN(join_idx_p);
        int idx_tail = p_origin.getIdxB(join_idx_p);

        while(true) {
            Coord head = p_origin.getWCrd(idx_head);
            int t;
            if(list.get(0).equals(head) &&
               Tool.equals(p_origin.getAngle(idx_head),getAngle(searchCrd(head))))
            {
                list.remove(0);
            }
            else if((t = searchOnLine(head)) >= 0 && t != join_idx_f) {
                list.add(0,head);
                for(int i=0;i<list.size();i++) {
                    if(list.get(i).equals(getWLine(t).getSt())) {
                        Coord[] array = new Coord[i+1];
                        for(int j=0;j<=i;j++) {
                            array[j] = list.get(0);
                            list.remove(0);
                        }
                        ret.add(new Frame(array));
                        break;
                    }
                }
            }
            else {
                list.add(0,head);
            }


            if(idx_head == idx_tail) {
                break;
            }


            Coord tail = p_origin.getWCrd(idx_tail);
            if(list.get(list.size()-1).equals(tail) &&
               Tool.equals(p_origin.getAngle(idx_tail),getAngle(searchCrd(tail))))
            {
                list.remove(list.size()-1);
            }
            else if((t = searchOnLine(tail)) >= 0 && t != getIdxB(join_idx_f)) {
                list.add(tail);
                for(int i=list.size()-1;i>=0;i--) {
                    if(list.get(i).equals(getWLine(t).getGo())) {
                        Coord[] array = new Coord[i+1];
                        for(int j=0;j<=i;j++) {
                            array[j] = list.get(list.size()-1);
                            list.remove(list.size()-1);
                        }
                        ret.add(new Frame(array));
                        break;
                    }
                }
            }
            else {
                list.add(tail);
            }

            if(false) {
                break;
            }

            idx_head = p_origin.getIdxN(idx_head);
            idx_tail = p_origin.getIdxB(idx_tail);
        }
        if(list.size() > 0) {
            Coord[] array = new Coord[list.size()];
            for(int i=0;i<list.size();i++) {
                array[i] = list.get(i).clone();
            }
            ret.add(new Frame(array));
        }

        return ret;
    }

    public boolean isInArea(Coord c) {
        for(Triangle t : triangles) {
            if(t.isInArea(c) == true) return true;
        }
        return false;
    }

    public boolean isInArea(Piece p) {
        for(int i=0;i<p.num;i++) {
            if(this.isInArea(p.getWCrd(i)) == false) return false;
        }
        for(int i=0;i<this.num;i++) {
            for(int j=0;j<p.num;j++) {
                if(this.getLine(i).isCross(p.getWLine(j)) == true) return false;
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
