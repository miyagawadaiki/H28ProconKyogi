import java.util.*;

public class Main {
    public static long count = 0L;
    public static long start = 0L;
    public static long end = 0L;

///*
    public static void main(String[] args) {
//        System.out.println(Tool.hasIntersection(new Vector(5,0),new Vector(0,0),new Vector(1,2),new Vector(2,0)));
//        System.out.println((new Vector(2.0,-4.0)).reverse());
//        System.out.println(Tool.calcArea(new Vector(1,1),new Vector(1,0)));


        // パズルの初期状態を作成
        Solver first = new Solver();
        first.read();

        // 再帰により解く
//        solve_by_rec(first);
//        reduce_piece_by_stack(first);
//        System.out.println("\n\n\n\n");
//        count = 0;
        reduce_piece_by_rec(first);
    }


    // 深さ優先探索（再帰ver）でピースを枠に置いていく
    public static void solve_by_rec(Solver first) {
        System.out.println(first.toExString());
        solve_rec(first);
        end = System.nanoTime();
        System.out.println("Time:" + (end - start) / 1000000f + "ms");
        System.out.println(count);
        System.out.println("Damedaze! in solve_by_rec()");
    }

    // solve_by_rec() で使う再帰関数
    public static void solve_rec(Solver now) {
        count++;
        System.out.println(now.fixed.size() + "\t" + count);
//        System.out.println(now.toExString());
        System.out.println(now.toStringForRead()+"\n");
        if(now.isFinished() == true) {
            end = System.nanoTime();
            System.out.println("Time:" + (end - start) / 1000000f + "ms");
            System.out.println("finished!!!!!");
            System.out.println(now.toStringForRead());
            System.exit(1);
        }
        for(int i=0;i<now.frame.num;i++) {
            for(int j=0;j<now.data.size();j++) {
                for(int k=0;k<now.data.get(j).num;k++) {
                    System.out.printf("%d %d %d\n", i, j, k);
                    if(now.canPut(i,j,k) == true) {
                        System.out.println("Yattaze!");
                        Solver tmp = now.clone();
                        tmp.put(i,j,k);
                        solve_rec(tmp);
                    }
                }
            }
        }
    }


    // ピース数を減らす（再帰ver.）
    public static void reduce_piece_by_rec(Solver first) {
        start = System.nanoTime();
        reduce_rec(first);
        end = System.nanoTime();
        System.out.println("Time:" + (end - start) / 1000000f + "ms");
        System.out.println(count);
        System.out.println("Damedaze! in reduce_piece_by_rec()");
    }

    // ピース数を減らす（再帰ver.）
    public static void reduce_piece_by_stack(Solver first) {
        start = System.nanoTime();
        Stack<Solver> temp = new Stack<Solver>();
        temp.push(first);
        ArrayList<Solver> comb_list = new ArrayList<Solver>();

        System.out.println(first.toExString());

        while(temp.size() > 0) {
            Solver now = temp.pop();
            count++;
            System.out.printf("%4d %19d\n", temp.size(), count);
//            System.out.println(now);
            if(now.isFinished()) {
                end = System.nanoTime();
                System.out.println("finished!!!!!!!!!!!!!!!!!");
                System.out.println("Time:" + (end - start) / 1000000f + "ms");
                System.out.println(count);
                System.out.println(now);
//                System.exit(1);
                return;
            }
            for(int i=0;i<now.data.size();i++) {
//                System.out.println(now.data.get(i));
                for(int j=i+1;j<now.data.size();j++) {
//                    System.out.println("\t"+now.data.get(j));
                    for(int k=0;k<now.data.get(i).num;k++) {
//                        System.out.println("\t\t"+now.data.get(i).get(k));
                        for(int l=0;l<now.data.get(j).num;l++) {
//                            System.out.println("\t\t\t"+now.data.get(j).get(l));
                            Piece p = now.data.get(i);
                            Piece q = now.data.get(j);
//                            Vector a = p.get(k);
//                            Vector b = q.get(l);
//                            Piece c = new Piece(q,Tool.calcLinalizeAngle(a,b));
//                            System.out.println(c);
//                            q.rotate(Tool.calcLinalizeAngle(a,b));
//                            if(Tool.canFuse(p,c,k,l) &&
//                              (Tool.fuse(p,c,k,l).max <= now.frame.max)) {
//                            if(Tool.canFuse(p,q,k,l)) {
                            if(Tool.canFuse(p,q,k,l) == true) {
//                            if(Tool.hasAccuracy(0.0, a.length - b.length)) {
//                                System.out.println("Yattaze");
                                Solver brunch = now.clone();
                                brunch.data.remove(i);
                                brunch.data.remove(j-1);
                                brunch.data.add(Tool.fuse(p,q,k,l));
                                temp.push(brunch);
                            }
                        }
                    }
                }
            }
//            comb_list.add(now);
        }
        System.out.println("Time:" + (end - start) / 1000000f + "ms");
        System.out.println(count);
        System.out.println("Not Found");

    }

    // reduce_piece_by_rec()用の再帰関数
    static void reduce_rec(Solver now) {
//        if(count >= 50) System.exit(1);
        count++;
        System.out.println(now.data.size() + "\t" + count);
//        System.out.println(now.error);
//        if(now.data.size() <= 11)
//        System.out.println(now);
        if(now.isFinished()) {
            end = System.nanoTime();
            System.out.println("finished!!!!!!!!!!!!!!!!!");
            System.out.println("Time:" + (end - start) / 1000000f + "ms");
            System.out.println(count);
            System.out.println(now);
            System.exit(1);
        }
        for(int i=0;i<now.data.size();i++) {
            for(int j=i+1;j<now.data.size();j++) {
                for(int k=0;k<now.data.get(i).num;k++) {
                    for(int l=0;l<now.data.get(j).num;l++) {
                        Piece p = now.data.get(i);
                        Piece q = now.data.get(j);
                        if(Tool.canFuse(p,q,k,l) == true) {
//                            System.out.println("\nhogehogehogehoge  " + Tool.calcLinalizeAngle(a,b) + "\n");
                            Solver brunch = now.clone();
                            brunch.data.remove(i);
                            brunch.data.remove(j-1);
                            brunch.data.add(Tool.fuse(p,q,k,l));
                            reduce_rec(brunch);
                        }
                    }
                }
            }
        }

    }
}
