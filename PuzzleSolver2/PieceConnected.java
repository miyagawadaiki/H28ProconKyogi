/////////////////////////////////////////////////////
//  PieceConnectedクラス：連結したピースを扱う
//
//      Piece fst, scd : 一つ目、二つ目のピース
//
/////////////////////////////////////////////////////

public class PieceConnected extends Piece {
    Piece fst, scd;

    public PieceConnected(Piece f, Piece s) {
        super(f.num+s.num-2);
        // .clone()したほうがいいだろうか？
        fst = f;
        scd = s;
    }

    public String toString() {
        String t = "";
        t += fst + "\n" + scd;
        return t;
    }
}
