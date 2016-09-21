////////////////////////////////////////////////////////////
//  Puzzleクラス：パズルの状態を表現するオブジェクト
//
//      ArrayList<Frame> frame_list : 枠のリスト
//      ArrayList<Piece> piece_list : ピースのリスト
//
////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Scanner;

public class Puzzle {
    ArrayList<Frame> frame_list;
    ArrayList<Piece> piece_list;
    ArrayList<Piece> complete_list;

    public Puzzle() {
        frame_list = new ArrayList<Frame>();
        piece_list = new ArrayList<Piece>();
        complete_list = new ArrayList<Piece>();
    }

    public Puzzle(Puzzle copy) {
        this();
        for(int i=0;i<copy.frame_list.size();i++) this.frame_list.add(copy.frame_list.get(i).clone());
        for(int i=0;i<copy.piece_list.size();i++) this.piece_list.add(copy.piece_list.get(i).clone());
        for(int i=0;i<copy.complete_list.size();i++) this.complete_list.add(copy.complete_list.get(i).clone());
    }

    public Puzzle clone() {
        return new Puzzle(this);
    }

/*
    public boolean execute() {
        for(int i=0;i<frame_list.size();i++) {
            Frame f = frame_list.get(i);
            for(int j=0;j<f.num;j++) {
                for(int k=0;k<piece_list.size();k++) {
                    Piece p = piece_list.get(k);
                    for(int l=0;l<p.num;l++) {
                        State state = new State(f.getCrd(i),Tool.calcAngle(p.getVec(j),f.getVec(i)));
                        if(!(f.canPut(p,state,f.getCrd(i)))) break;
                        if(f.evaluate(p,state,f.getCrd(i)) < 1.0) break;
                        for(Frame ff : f.put(p,state,f.getCrd(i))) {
                            frame_list.add(ff);
                        }
                        frame_list.remove(i);
                        complete_list.add(p);
                        piece_list.remove(k);
                        return true;
                    }
                }
            }
        }
        return false;
    }
*/

    public void read() {
        Scanner stdIn  = new Scanner(System.in);

        int n = stdIn.nextInt();
        for(int i=0;i<n;i++) {
            frame_list.add(new Frame((int)stdIn.nextDouble()));
            frame_list.get(i).read(stdIn);
        }

        n = stdIn.nextInt();
        for(int i=0;i<n;i++) {
            piece_list.add(new Piece((int)stdIn.nextDouble()));
            piece_list.get(i).read(stdIn);
        }
    }

    //TODO
    public boolean isFinish() {
        double sum = 0.0;
        for(Frame f : frame_list) sum += f.calcArea();
        if(sum <= 0.0) return true;
        if(piece_list.size() == 0) return true;
        return false;
    }

    //TODO
    @Override
    public String toString() {
        String t = "";
        t += "<Frame:" + frame_list.size() + ">\n";
        for(Frame f : frame_list) t += f.toString() +"\n";
        t += "<Piece:" + piece_list.size() + ">\n";
        for(Piece p : piece_list) t += p.toString() + "\n";
        return t;
    }

    //TODO
    public String toStringAll() {
        String t = "";
        return t;
    }

    //TODO
    public String toStringForRead() {
        String t = "";
//        for(Frame f : frame_list) t += f.toStringForRead() + "\n";
        for(Piece p : complete_list) t += p.toStringForRead() + "\n";
        return t;
    }

    public String toStringByTriangle() {
        String t = "";
        t += "<Frame:" + frame_list.size() + ">\n";
        for(Frame f : frame_list) t += f.toStringByTriangle() +"\n";
        t += "<Piece:" + piece_list.size() + ">\n";
        for(Piece p : piece_list) t += p.toStringByTriangle() + "\n";
        return t;
    }
}
