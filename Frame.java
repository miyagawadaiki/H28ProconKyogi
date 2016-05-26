import java.util.Scanner;

public class Frame {
    int num;
    Coord[] coords;
//    Vector[] vectors;
    double total_length;

    public Frame(int num) {
        this.num = num;
//        vectors = new Vector[num];
        coords = new Coord[num];
    }

    public Frame(Frame copy) {
        this(copy.num);
        for(int i=0;i<num;i++)
//            this.vectors[i] = copy.vectors[i].clone();
            this.coords[i] = copy.coords[i].clone();
    }

    public Frame clone() {
        return new Frame(this);
    }

    void read(Scanner stdIn) {
        for(int i=0;i<num;i++) {
            coords[i] = new Coord(stdIn.nextDouble(), stdIn.nextDouble());
        }
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

    Vector get(int n) {
        if(n > num)
            throw new IllegalArgumentException("no such vector in this Piece : \n" + this.toString() + "\n");
//        return vectors[n];
        return new Vector(coords[n],coords[getNextIdx(n)]);
    }

    int getBackIdx(int n) {
        return (num + n - 1) % num;
    }

    int getNextIdx(int n) {
        return (n+1) % num;
    }

    double totalLength() {
        double s = 0;
//        for(Vector v : vectors)
//            s += v.length;
        for(int i=0;i<num;i++)
            s += get(i).length;
        return s;
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
                    if(!Tool.hasAccuracy(this.get((i+j)%num).length, p.get(j).length)) {
                        flg = false;
                        break;
                    }
                }
                if(flg == true) return true;
            }
            return false;
        }
    }

    public String toString() {
        String s = "";
//        for(Vector i : vectors) {
//            s += i.toString() + " ";
//        }
        for(int i=0;i<num;i++)
            s += get(i).toString() + " ";
        return s;
    }
}
