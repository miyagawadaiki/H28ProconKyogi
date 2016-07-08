import java.util.*;

public class Main {
    public static long count = 0L;
    public static long start = 0L;
    public static long end = 0L;

///*
    public static void main(String[] args) {
        Solver first = new Solver();
        first.read();
        reduce_piece_by_stack(first);
        System.out.println("\n\n\n\n");
        count = 0;
        reduce_piece_by_rec(first);
    }

    public static void solve_by_rec(Solver first) {
        solve_rec(first);
        end = System.nanoTime();
        System.out.println("Time:" + (end - start) / 1000000f + "ms");
        System.out.println(count);
        System.out.println("Damedaze! in solve_by_rec()");
    }

    public static void solve_rec(Solver now) {
        count++;
        if(now.isFinished() == true) {
            System.out.println("finished!!!!!");
        }
        for(int i=0;i<now.frame.num;i++) {
            for(int j=0;j<now.data.size();j++) {
                for(int k=0;k<now.data.get(i).num;k++) {

                }
            }
        }
    }

    public static void reduce_piece_by_rec(Solver first) {
        start = System.nanoTime();
        reduce_rec(first);
        end = System.nanoTime();
        System.out.println("Time:" + (end - start) / 1000000f + "ms");
        System.out.println(count);
        System.out.println("Damedaze! in reduce_piece_by_rec()");
    }

    public static void reduce_piece_by_stack(Solver first) {
        start = System.nanoTime();
        Stack<Solver> temp = new Stack<Solver>();
        temp.push(first);
        ArrayList<Solver> comb_list = new ArrayList<Solver>();

        System.out.println(first.toString());

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

    static void reduce_rec(Solver now) {
//        if(count >= 50) System.exit(1);
        count++;
//        System.out.println(now.data.size() + "\t" + count);
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
