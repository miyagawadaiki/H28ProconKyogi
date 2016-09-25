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
//                            System.out.println("\to!");
        return true;
    }

    public double evaluate(Piece p, State s, Coord join_crd) {
        double ret = 0.0;
        Piece clone = p.clone();
        clone.move(s);
//                            System.out.println("\t"+join_crd);
//                            System.out.println("\t"+clone.toStringForRead());
        int join_idx_f = this.searchCrd(join_crd);
        int join_idx_p = clone.searchWCrd(join_crd);
//                            System.out.println("\t"+join_idx_f);
//                            System.out.println("\t"+join_idx_p);

        if(Tool.equals(this.getAngle(join_idx_f),clone.getAngle(join_idx_p)))
            ret += 1.0;

        return ret;
    }

    public ArrayList<Frame> put(Piece p_origin, State s, Coord join_crd) {
//                                System.out.println(this.toStringForRead());
        ArrayList<Frame> ret = new ArrayList<Frame>();
        p_origin.move(s);
        int join_idx_f = this.searchCrd(join_crd);
        int join_idx_p = p_origin.searchWCrd(join_crd);
        ArrayList<Coord> list = new ArrayList<Coord>();
        for(Coord c : this.slice(getIdxN(join_idx_f),getIdxB(join_idx_f)))
            list.add(c);
        int idx_head = p_origin.getIdxN(join_idx_p);

//                                System.out.print("list  : ");
//                                for(Coord c : list) System.out.print(c + "\t");
//                                System.out.println();
        for(int i=0;i<p_origin.num-1;i++,idx_head = p_origin.getIdxN(idx_head)) {
//                                System.out.println(i + " " + (p_origin.num-1));
            Coord head = p_origin.getWCrd(idx_head);

            if(list.get(0).equals(head)) {}
            else {
                list.add(0,head);
            }
//                                System.out.print("list1 : ");
//                                for(Coord c : list) System.out.print(c + "\t");
//                                System.out.println();

            int idx_div_list = -1;
            for(int j=1;j<list.size()-1;j++) {
                Coord now = list.get(j);
                Coord next = list.get(j+1);
                Line l = new Line(now,next);
                if(l.isOnLine(head)) {
                    idx_div_list = j;
                    break;
                }
            }

            if(idx_div_list >= 0) {
                ArrayList<Coord> tmp = new ArrayList<Coord>();
                for(int j=0;j<=idx_div_list;j++) {
                    tmp.add(list.get(0));
                    list.remove(0);
                }
                shape(tmp);
//                                        System.out.print("\t\ttmp cw\t: ");
//                                        for(Coord c : tmp) System.out.print(c + "\t");
//                                        System.out.println();
                if(tmp.size() >= 3) {
                    Coord[] array = new Coord[tmp.size()];
                    for(int j=0;j<array.length;j++) {
                        array[j] = tmp.get(j);
                    }
                    ret.add(new Frame(array));
                }
            }
//                                System.out.print("list2 : ");
//                                for(Coord c : list) System.out.print(c + "\t");
//                                System.out.println();

            if(searchCrd(head) >= 0 &&
               Tool.equals(getAngle(searchCrd(head)),p_origin.getAngle(idx_head)) &&
               Tool.equals(Tool.calcTheta(getWVec(searchCrd(head))),Tool.calcTheta(p_origin.getWVec(idx_head))))
            {
                list.remove(0);
            }
//                                System.out.print("list3 : ");
//                                for(Coord c : list) System.out.print(c + "\t");
//                                System.out.println("\n");
        }

        if(list.size() >= 3) {
            shape(list);
                                System.out.print("list  : ");
                                for(Coord c : list) System.out.print(c + "\t");
                                System.out.println("\n");
            Coord[] array = new Coord[list.size()];
            for(int i=0;i<array.length;i++) {
                array[i] = list.get(i);
            }
            ret.add(new Frame(array));
            System.out.println("hogehogehoge");
        }

        return ret;
    }

    public void shape(ArrayList<Coord> list) {
        for(int i=0;;) {
            int size = list.size();
            Coord s = list.get((i+size-1)%size);
            Coord g = list.get((i+1)%size);
            Coord now = list.get(i);
            Line line = new Line(s,g);
            if(line.isOnLine(now) || now.equals(g)) {
                list.remove(i);
            }
            else i++;
            if(list.size() <= i) break;
        }
    }

    public boolean isInArea(Coord c) {
        for(Triangle t : triangles) {
            if(t.isInArea(c) == true) return true;
        }
        return false;
    }

    public boolean isInArea(Piece p) {
        for(int i=0;i<p.num;i++) {
            if(this.isInArea(p.getWCrd(i)) == false) {
//                                System.out.println("a "+p.getCrd(i));
                return false;
            }
        }
        for(int i=0;i<this.num;i++) {
            for(int j=0;j<p.num;j++) {
                if(this.getLine(i).isCross(p.getWLine(j)) == true) {
//                                System.out.println("b "+getLine(i) + "  " + p.getWLine(j));
                    return false;
                }
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
        return super.toStringAll();
    }

    //TODO
    @Override
    public String toStringForRead() {
        return super.toStringForRead();
    }
}
