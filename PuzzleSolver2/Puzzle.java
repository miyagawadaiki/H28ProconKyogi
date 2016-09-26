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

    public int getNumAll() {
        int sum = 0;
        for(Piece p : complete_list) sum += p.num;
        return sum;
    }

    public void read() {
        Scanner stdIn  = new Scanner(System.in);

        int n = stdIn.nextInt();
        for(int i=0;i<n;i++) {
            int id = stdIn.nextInt();
            frame_list.add(new Frame((int)stdIn.nextDouble()));
            frame_list.get(i).read(stdIn);
        }

        n = stdIn.nextInt();
        for(int i=0;i<n;i++) {
            int id = stdIn.nextInt();
            piece_list.add(new Piece((int)stdIn.nextDouble(),id));
            piece_list.get(i).read(stdIn);
        }
    }

    //TODO
    public boolean isFinish() {
        if(frame_list.size() == 0) return true;
        double sum = 0.0;
        for(Frame f : frame_list) sum += f.calcArea();
        if(Tool.nearlyEquals(sum,0.0)) return true;
//        if(sum <= 0.0) return true;
        if(piece_list.size() == 0) return true;
        return false;
    }

    public void addFrameList(ArrayList<Frame> list) {
        if(list.size() == 0) return;
        for(Frame f : list) {
            this.frame_list.add(f);
        }
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
        t += getNumAll() + "\n";
        t += complete_list.size() + "\n";
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

    public String toStringStatus() {
        String t = "";
        for(int i=0;i<complete_list.size();i++) t += "*";
        for(int i=0;i<piece_list.size();i++) t += "-";
        return t;
    }
}
