import java.util.*;

public class Piece {
    int num;
    Vector[] vectors;

    public Piece(int num) {
        this.num = num;
        vectors = new Vector[num];
        for(Vector v : vectors)
            v = new Vector();
    }

    public Piece(Piece p, double theta) {
        this(p);
        rotate(theta);
//        System.out.printf("turn\n%f\n%s\n",theta,this);
    }

    public Piece(Piece p1, Piece p2, int p1_idx, int p2_idx, int num, boolean flag) {
        this(num);

        Vector p1b = p1.getBack(p1_idx);
        Vector p1n = p1.getNext(p1_idx);
        Vector p2b = p2.getBack(p2_idx);
        Vector p2n = p2.getNext(p2_idx);
        Vector p12 = new Vector(p1b,p2n);
        Vector p21 = new Vector(p2b, p1n);

        int index = 0;
        int pi = (p1_idx+2)%p1.num;
        while(true) {
            if(pi == p1.getBackIdx(p1_idx)) break;
//            System.out.printf("p1_%d:%s\n",pi,p1.vectors[pi]);
            this.vectors[index++] = p1.vectors[pi];
            pi = (pi+1)%p1.num;
        }
//        System.out.printf("p12:%s\n",p12);

        if(flag) {
            this.vectors[index++] = p12;
        } else {
            this.vectors[index++] = p1b;
            this.vectors[index++] = p2n;
        }
        pi = (p2_idx+2)%p2.num;
        while(true) {
            if(pi == p2.getBackIdx(p2_idx)) break;
//            System.out.printf("p2_%d:%s\n",pi,p1.vectors[pi]);
            this.vectors[index++] = p2.vectors[pi];
            pi = (pi+1)%p2.num;
        }
//        System.out.printf("p21:%s\n",p21);
        if(flag) {
            this.vectors[index] = p21;
        } else {
            this.vectors[index++] = p2b;
            this.vectors[index++] = p1n;
        }
    }

    public Piece(Piece copy) {
        this(copy.num);
        for(int i=0;i<num;i++)
            this.vectors[i] = copy.vectors[i].clone();
    }

    public Piece clone() {
        return new Piece(this);
    }

    Vector get(int n) {
        if(n > num)
            throw new IllegalArgumentException("no such vector in this Piece : \n" + this.toString() + "\n");
        return vectors[n];
    }

    Vector getBack(int n) {
        return vectors[getBackIdx(n)];
    }

    Vector getNext(int n) {
        return vectors[getNextIdx(n)];
    }

    int getBackIdx(int n) {
        return (num + n - 1) % num;
    }

    int getNextIdx(int n) {
        return (n+1) % num;
    }

    void read(Scanner stdIn) {
        Coord first = new Coord(stdIn.nextDouble(), stdIn.nextDouble());
        Coord front = first;
        int i;
        for(i=0;i<num-1;i++) {
            Coord cur = new Coord(stdIn.nextDouble(),stdIn.nextDouble());
            vectors[i] = new Vector(front,cur);
            front = cur;
        }
        vectors[i] = new Vector(front, first);
    }

    void rotate(double theta) {
        for(int i=0;i<num;i++)
            vectors[i] = new Vector(vectors[i], theta);
    }

    void flip() {
        for(Vector v : vectors)
            v = new Vector(v,-1.0,1.0);
    }

    double totalLength() {
        double s = 0;
        for(Vector v : vectors)
            s += v.length;
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
                    if(!Tool.hasAccuracy(this.vectors[(i+j)%this.num].length, p.vectors[j].length)) {
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
        for(Vector i : vectors) {
            s += i.toString() + " ";
        }
        return s;
    }
}
