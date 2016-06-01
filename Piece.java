import java.util.*;

public class Piece {
    int num;
    double theta;
    Coord c;
    Coord[] coords;
    int dent_cnt;
    int[] dents;
//    Vector[] vectors;

    public Piece(int num) {
        this.num = num;
        theta = 0.0;
        c = new Coord();
        coords = new Coord[num];
//        vectors = new Vector[num];
//        for(Vector v : vectors)
//            v = new Vector();
        for(int i=0;i<num;i++)
            coords[i] = new Coord();
        dent_cnt = 0;
        dents = new int[32];
    }

    public Piece(Piece p, double theta) {
        this(p);
        rotate(theta);
//        System.out.printf("turn\n%f\n%s\n",theta,this);
    }

    public Piece(Piece p1, Piece p2, int p1_idx, int p2_idx, int n, boolean flag) {
        this(n);
//        System.out.println(this);

        Vector p1b = p1.getBack(p1_idx);
        Vector p1n = p1.getNext(p1_idx);
        Vector p2b = p2.getBack(p2_idx);
        Vector p2n = p2.getNext(p2_idx);
        Vector p12 = new Vector(p1b,p2n);
        Vector p21 = new Vector(p2b, p1n);

//        System.out.printf("%s %s %s %s\n%s %s\n", p1b, p2n, p2b, p1n, p12, p21);

        int index = 0;
        int pi = (p1_idx+2)%p1.num;
        while(true) {
            if(pi == p1.getBackIdx(p1_idx)) break;
//            System.out.printf("p1_%d:%s\n",pi,p1.get(pi).toString());
//            this.vectors[index++] = p1.vectors[pi];
            this.set(index++, p1.get(pi));
//            System.out.println("a");
            pi = (pi+1)%p1.num;
        }
//        System.out.printf("p12:%s\n",p12);

        if(flag) {
//            this.vectors[index++] = p12;
            this.set(index++, p12);
        } else {
//            this.vectors[index++] = p1b;
//            this.vectors[index++] = p2n;
            this.set(index++, p1b);
            this.set(index++, p2n);
        }
        pi = (p2_idx+2)%p2.num;
        while(true) {
            if(pi == p2.getBackIdx(p2_idx)) break;
//            System.out.printf("p2_%d:%s\n",pi,p1.vectors[pi]);
//            this.vectors[index++] = p2.vectors[pi];
            this.set(index++, p2.get(pi));
            pi = (pi+1)%p2.num;
        }
//        System.out.printf("p21:%s\n",p21);
        if(flag) {
//            this.vectors[index] = p21;
            this.set(index++, p21);
        } else {
//            this.vectors[index++] = p2b;
//            this.vectors[index++] = p1n;
            this.set(index++, p2b);
            this.set(index++, p1n);
        }
    }

    public Piece(Piece copy) {
        this(copy.num);
        this.theta = copy.theta;
        this.c = copy.c.clone();
        for(int i=0;i<num;i++)
//            this.vectors[i] = copy.vectors[i].clone();
            this.coords[i] = copy.coords[i].clone();
        this.dent_cnt = copy.dent_cnt;
        this.dents = copy.dents.clone();
    }

    public Piece clone() {
        return new Piece(this);
    }

    void set(int n, Vector v) {
//        System.out.println(getBackIdx(n));
        coords[n] = new Coord(coords[getBackIdx(n)], v);
    }

    Vector get(int n) {
        if(n > num)
            throw new IllegalArgumentException("no such vector in this Piece : \n" + this.toString() + "\n");
//        return vectors[n];
//        System.out.println(coords[getBackIdx(n)]);
//        System.out.println(coords[n]);
        return new Vector(new Vector(coords[getBackIdx(n)], coords[n]), theta);
    }

    Vector getBack(int n) {
//        return vectors[getBackIdx(n)];
        return get(getBackIdx(n));
    }

    Vector getNext(int n) {
//        return vectors[getNextIdx(n)];
        return get(getNextIdx(n));
    }

    int getBackIdx(int n) {
        return (num + n - 1) % num;
    }

    int getNextIdx(int n) {
        return (n+1) % num;
    }

    void read(Scanner stdIn) {
        for(int i=0;i<num;i++) {
            coords[i] = new Coord(stdIn.nextDouble(), stdIn.nextDouble());
        }
        countUpDent();
    }

/*
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
*/


    void rotate(double theta) {
        this.theta += theta;
    }


/*
    void flip() {
        for(Vector v : vectors)
            v = new Vector(v,-1.0,1.0);
    }
*/

    double totalLength() {
        double s = 0;
//        for(Vector v : vectors)
//            s += v.length;
        for(int i=0;i<num;i++)
            s += get(i).length;
        return s;
    }

    void countUpDent() {
        Vector v1,v2 = get(0);
        for(int i=0;i<num-1;i++) {
            v1 = v2; v2 = get(i+1);
//            System.out.println(Tool.calcAngle(v1,v2));
            if(Tool.calcAngle(v1,v2) < 0) {
                dents[dent_cnt++] = i;
            }
        }
    }

    boolean equals(Piece p) {
        if(this.num != p.num || !Tool.hasAccuracy(this.totalLength(),p.totalLength())) {
            return false;
        }
        else {
            for(int i=0;i<this.num;i++) {
                boolean flg = true;
                for(int j=0;j<p.num;j++) {
//                    if(!Tool.hasAccuracy(this.vectors[(i+j)%this.num].length, p.vectors[j].length)) {
                    if(!Tool.hasAccuracy(get((i+j)%num).length, p.get(j).length)) {
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
        s += String.format("[%2d:%2d:%4f] ", num, dent_cnt, theta);
//        for(Vector i : vectors) {
//            s += i.toString() + " ";
//        }
        for(int i=0;i<num;i++)
            s += get(i).toString() + " ";
        return s;
    }
}
