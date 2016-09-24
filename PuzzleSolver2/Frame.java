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
                                System.out.print("list : ");
                                for(Coord c : list) System.out.print(c + "\t");
                                System.out.println();
            Coord head = p_origin.getWCrd(idx_head);

            if(list.get(0).equals(head)) {
                if(Tool.equals(p_origin.getAngle(idx_head),getAngle(searchCrd(head)))) {
                    if(idx_head == idx_tail) break;
                    for(int i=0;i<list.size();i++) {
                        Coord c = list.get(i);
                        if(c.equals(head)) {
                            list.remove(i);
                            break;
                        }
                    }
                }
            }
            else {
                list.add(0,head);

                if(searchOnLine(head) >= 0) {
//                    int idx_line = searchOnLine(head);
//                    Line l = new Line(getWLine(idx_line).getSt(),head);
                    int idx_div_list = -1;
                    for(int i=1;i<list.size()-1;i++) {
                        Coord now = list.get(i);
                        Coord next = list.get(i+1);
                        Line l = new Line(now,next);
                        if(l.isOnLine(head)) {
                            idx_div_list = i;
                            break;
                        }
                    }
                    if(idx_div_list >= 0) {
                        ArrayList<Coord> tmp = new ArrayList<Coord>();
                        for(int i=0;i<=idx_div_list;i++) {
                            tmp.add(list.get(0));
                            list.remove(0);
                        }
                        shape(tmp);
                                        System.out.print("\t\ttmp : ");
                                        for(Coord c : tmp) System.out.print(c + "\t");
                                        System.out.println();
                        Coord[] array = new Coord[tmp.size()];
                        for(int i=0;i<array.length;i++) {
                            array[i] = tmp.get(i);
                        }
                        list.add(0,head);
                        ret.add(new Frame(array));
                    }
                }
            }

                                        System.out.print("\tlist : ");
                                        for(Coord c : list) System.out.print(c + "\t");
                                        System.out.println();

            Coord tail = p_origin.getWCrd(idx_tail);

            if(list.get(list.size()-1).equals(tail)) {
                if(Tool.equals(p_origin.getAngle(idx_tail),getAngle(searchCrd(tail)))) {
                    for(int i=0;i<list.size();i++) {
                        Coord c = list.get(i);
                        if(c.equals(tail)) {
                            list.remove(i);
                            break;
                        }
                    }
                }
            }
            else {
                list.add(tail);

                if(searchOnLine(tail) >= 0) {
//                    int idx_line = searchOnLine(tail);
//                    Line l = new Line(tail,getWLine(idx_line).getGo());
                    int idx_div_list = -1;
                    for(int i=list.size()-2;i>=1;i--) {
                        Coord now = list.get(i);
                        Coord next = list.get(i-1);
                        Line l = new Line(now,next);
                        if(l.isOnLine(tail)) {
                            idx_div_list = i;
                            break;
                        }
                    }
                    if(idx_div_list >= 0) {
                        int size = list.size();
                        ArrayList<Coord> tmp = new ArrayList<Coord>();
                        for(int i=idx_div_list;i<size;i++) {
                            tmp.add(list.get(idx_div_list));
                            list.remove(idx_div_list);
                        }
                                        System.out.print("\t\ttmp : ");
                                        for(Coord c : tmp) System.out.print(c + "\t");
                                        System.out.println();
                        shape(tmp);
                                        System.out.print("\t\ttmps: ");
                                        for(Coord c : tmp) System.out.print(c + "\t");
                                        System.out.println();
                        Coord[] array = new Coord[tmp.size()];
                        for(int i=0;i<array.length;i++) {
                            array[i] = tmp.get(i);
                        }
                        list.add(tail);
                        ret.add(new Frame(array));
                    }
                }
            }
                                        System.out.print("\tlist : ");
                                        for(Coord c : list) System.out.print(c + "\t");
                                        System.out.println();

            int next_head = p_origin.getIdxN(idx_head);
            int next_tail = p_origin.getIdxB(idx_tail);

            if(idx_head == idx_tail || (idx_head == next_tail && idx_tail == next_head)) {
                                        System.out.print("list : ");
                                        for(Coord c : list) System.out.print(c + "\t");
                                        System.out.println();
                shape(list);
                                        System.out.print("list : ");
                                        for(Coord c : list) System.out.print(c + "\t");
                                        System.out.println();
                if(list.size() > 1) {
                    Coord[] array = new Coord[list.size()];
                    for(int i=0;i<array.length;i++) {
                        array[i] = list.get(i);
                    }
                    ret.add(new Frame(array));
                }
                break;
            }

            idx_head = p_origin.getIdxN(idx_head);
            idx_tail = p_origin.getIdxB(idx_tail);
        }

        return ret;
    }

    public void shape(ArrayList<Coord> list) {
        if(list.get(0).equals(list.get(list.size()-1))) {
            list.remove(list.size()-1);
                                System.out.println("shape-case1");
        }
        if(list.size() <= 1) return;
        for(int i=0;;) {
            int size = list.size();
            Coord s = list.get((i+size-1)%size);
            Coord g = list.get((i+1)%size);
            Coord now = list.get(i);
            Line line = new Line(s,g);
            if(line.isOnLine(now)) {
                list.remove(i);
                                System.out.print("shape-case2");
                                System.out.println(line + " " + now);
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
