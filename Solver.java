import java.util.Scanner;
import java.util.ArrayList;

public class Solver {
    ArrayList<Piece> data;
    Frame frame;
    double error;

    public Solver() {
        data = new ArrayList<Piece>();
        error = 0.0;
    }

    public Solver(Solver copy) {
        this();
        for(Piece p : copy.data)
            this.data.add(p.clone());
        this.frame = copy.frame.clone();
    }

    public Solver clone() {
        return new Solver(this);
    }

    //TODO
    public double eval(Piece p) {
        return 0.0;
    }

    // ファイルorコンソールから読み込む
    public void read() {
        Scanner stdIn  = new Scanner(System.in);

        frame = new Frame(stdIn.nextInt());
        frame.read(stdIn);

        int n = stdIn.nextInt();
        for(int i=0;i<n;i++) {
            data.add(new Piece((int)stdIn.nextDouble()));
            data.get(i).read(stdIn);
        }
    }

    public boolean isFinished() {
//        if(data.size() > 1) return false;
//        else if(frame.equals(data.get(0))) return true;
        if(frame.equals(data.get(0))) return true;
        else return false;
    }

    void calcError() {
        for(int i=0;i<data.size();i++) {
            this.error += data.get(i).error;
        }
    }

    public String toStringForRead() {
        String s = "";
        s += frame.toStringForRead() + "\n";
        s += data.size();
        for(int i=0;i<data.size();i++) {
            s += "\n" + data.get(i).toStringForRead();
        }
        return s;
    }

    public String toExString() {
        String s = "";
        s += "f:\n";
        s += frame.toString() + "\n";
        s += "p:\n";
        for(int i=0;i<data.size();i++)
            s += String.format("<%d> %s\n",i,data.get(i).toExString());
        return s;
    }

    public String toString() {
        String s = "";
        s += "f:\n";
        s += frame.toString() + "\n";
        s += "p:\n";
        for(int i=0;i<data.size();i++)
            s += String.format("<%d> %s\n",i,data.get(i).toString());
        return s;
    }
}
