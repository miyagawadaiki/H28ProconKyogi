public class PieceConnected extends Piece {
    Piece[] parts;
    Vector relative_vec;

    public PieceConnected(int num) {
        super(num);
        parts = new Piece[2];
        relative_vec = new Vector();
    }

    public PieceConnected(Piece p1, Piece p2, int p1_idx, int p2_idx) {
        this(p1.num - 3 + p2.num - 3 + 2);
        p2 = new Piece(p2, Tool.calcAngle(p1.getV(p1_idx), p2.getV(p2_idx))+Math.PI);
        parts[0] = p1.clone();
        parts[1] = p2.clone();
        relative_vec = new Vector(p1.getPV(p1.getNextIdx(p1_idx)),(new Vector(p2.getPV(p2_idx)).reverse()));

        Vector v_p12 = new Vector(p1.getVBack(p1_idx), p2.getVNext(p2_idx));
        Vector v_p21 = new Vector(p2.getVBack(p2_idx), p1.getVNext(p1_idx));

        int index = 0;
        int pi = (p1_idx+2)%p1.num;
        while(true) {
            if(pi == p1.getBackIdx(p1_idx)) break;
            this.set(index++, p1.getV(pi));
            pi = (pi+1)%p1.num;
        }

        this.set(index++, v_p12);

        pi = (p2_idx+2)%p2.num;
        while(true) {
            if(pi == p2.getBackIdx(p2_idx)) break;
            this.set(index++, p2.getV(pi));
            pi = (pi+1)%p2.num;
        }

        this.set(index, v_p21);

        countUpDent();
        calcMax();
        calcError();
    }

    public PieceConnected(PieceConnected copy) {
        super(copy);
        this.parts[0] = copy.parts[0].clone();
        this.parts[1] = copy.parts[1].clone();
        this.relative_vec = copy.relative_vec.clone();
    }

    @Override
    public String toStringForRead() {
        String s = "";
        s += parts[0].toStringForRead() + "\n" + parts[1].toStringForRead();
        return s;
    }

    @Override
    public String toExString() {
        String s = "";
        s += parts[0].toExString() + "\n" + parts[1].toExString();
        return s;
     }

    @Override
    public String toString() {
        String s = "";
        s += parts[0].toString() + "\n" + parts[1].toString();
        return s;
    }
}
