import java.util.*;

public class Frame {
    int num;
    ArrayList<Coord> coords;
//    Vector[] vectors;
    double total_length;
    double max;

    public Frame(int num) {
        this.num = num;
//        vectors = new Vector[num];
        coords = new ArrayList<Coord>();
    }

    public Frame(Frame copy) {
        this(copy.num);
        for(int i=0;i<num;i++)
//            this.vectors[i] = copy.vectors[i].clone();
            this.coords.add(copy.getC(i).clone());
        this.max = copy.max;
    }

    public Frame clone() {
        return new Frame(this);
    }

    void read(Scanner stdIn) {
        for(int i=0;i<num;i++) {
            coords.add(new Coord(stdIn.nextDouble(), stdIn.nextDouble()));
        }
        calcMax();
    }

/*
    void read(Scanner stdIn) {
        Coord first = new Coord(stdIn.nextDouble(), stdIn.nextDouble());
        Coord front = first;
        for(int i=0;i<num-1;i++) {
            Coord cur = new Coord(stdIn.nextDouble(),stdIn.nextDouble());
            vectors[i] = new Vector(front,cur);
            front = cur;
        }
        vectors[num-1] = new Vector(front, first);
    }
*/

    void add(Coord c) {
        coords.add(c);
        num++;
    }

    void add(int n, Coord c) {
        coords.add(n,c);
        num++;
    }

    void remove(int n) {
        coords.remove(n);
        num--;
    }

    Vector getV(int n) {
        if(n > num)
            throw new IllegalArgumentException("no such vector in this Piece : \n" + this.toString() + "\n");
//        return vectors[n];
        return new Vector(getC(n),getCNext(n));
    }

    Vector getVBack(int n) {
//        return vectors[getBackIdx(n)];
        return getV(getBackIdx(n));
    }

    Vector getVNext(int n) {
//        return vectors[getNextIdx(n)];
        return getV(getNextIdx(n));
    }

    int getBackIdx(int n) {
        return (num + n - 1) % num;
    }

    int getNextIdx(int n) {
        return (n+1) % num;
    }

    Coord getC(int n) {
        return coords.get(n);
    }

    Coord getCBack(int n) {
        return getC(getBackIdx(n));
    }

    Coord getCNext(int n) {
        return getC(getNextIdx(n));
    }

    double totalLength() {
        double s = 0;
//        for(Vector v : vectors)
//            s += v.length;
        for(int i=0;i<num;i++)
            s += getV(i).length;
        return s;
    }

    void calcMax() {
        Vector mx = new Vector(0,0);
        for(int i=0;i<num;i++) {
            if(getV(i).length > mx.length)
                mx = getV(i);
        }
        max = mx.length;
    }

    double getAngle(int n) {
        return Tool.calcAbsAngle(getV(n), new Vector(getVBack(n),Math.PI));
    }

    boolean equals(Piece p) {
        if(this.num != p.num || !Tool.hasAccuracy(this.totalLength(),p.totalLength())) {
            return false;
        }
        else {
            for(int i=0;i<this.num;i++) {
                boolean flg = true;
                for(int j=0;j<p.num;j++) {
    //                if(!Tool.hasAccuracy(this.vectors[(i+j)%this.num].length, p.vectors[j].length)) {
                    if(!Tool.hasAccuracy(this.getV((i+j)%num).length, p.getV(j).length)) {
                        flg = false;
                        break;
                    }
                }
                if(flg == true) return true;
            }
            return false;
        }
    }

    public String toStringForRead() {
        String s = "";
        s += num;
        for(int i=0;i<num;i++) {
            s += String.format("\n%f %f", getC(i).x, getC(i).y);
        }
        return s;
    }

    public String toString() {
        String s = "";
//        for(Vector i : vectors) {
//            s += i.toString() + " ";
//        }
        for(int i=0;i<num;i++)
            s += getV(i).toString() + " ";
        return s;
    }
}
