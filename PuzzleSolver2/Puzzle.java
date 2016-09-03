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

    public Puzzle() {
        frame_list = new ArrayList<Frame>();
        piece_list = new ArrayList<Piece>();
    }

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
