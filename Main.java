import java.util.*;

public class Main {

///*
    public static void main(String[] args) {
        Solver first = new Solver();
        first.read();
        Stack<Solver> temp = new Stack<Solver>();
        temp.push(first);
        ArrayList<Solver> comb_list = new ArrayList<Solver>();

        System.out.println(first);

        while(temp.size() > 0) {
            Solver now = temp.pop();
            System.out.println(now);
            if(now.isFinished()) {
                System.out.println("finished!!!!!!!!!!!!!!!!!");
                System.out.println(now);
                System.exit(1);
            }
            for(int i=0;i<now.data.size();i++) {
                for(int j=i+1;j<now.data.size();j++) {
                    for(int k=0;k<now.data.get(i).num;k++) {
                        for(int l=0;l<now.data.get(j).num;l++) {
                            Piece p = now.data.get(i);
                            Piece q = now.data.get(j);
                            Vector a = p.get(k);
                            Vector b = q.get(l);
                            Piece c = new Piece(q,Tool.calcLinalizeAngle(a,b));
                            if(Tool.checkFitness(p,c,k,l)) {
//                            if(Tool.hasAccuracy(0.0, a.length - b.length)) {
                                Solver brunch = now.clone();
                                brunch.data.remove(i);
                                brunch.data.remove(j-1);
                                brunch.data.add(Tool.fuse(p,c,k,l));
//                                brunch.data.add(Tool.join(p,c,k,l));
                                temp.push(brunch);
                            }
                        }
                    }
                }
            }
            comb_list.add(now);
        }
        System.out.println("Not Found");

    }
//*/
/*
    public static void main(String[] args) {
        System.out.println(first);

        recursion();

        System.out.println(first);

        if(first.data.size() <= 1 && first.frame.equals(first.data.get(0))) {
            System.out.println("finish!");
        } else {
            System.out.println("hogehoge");
        }
    }
*/

/*
    public static void search(Solver now, int si, int sj, int sk, int sl) {
        for(int i=si;i<now.data.size();i++) {
            for(int j=sj;j<now.data.size();j++) {
                for(int k=sk;k<now.data.get(i).num;k++) {
                    for(int l=sl;l<now.data.get(j).num;l++) {
                        Piece p = now.data.get(i);
                        Piece q = now.data.get(j);
                        Vector a = p.vectors[k];
                        Vector b = q.vectors[l];
                        Piece c = new Piece(q,Tool.getLinalizeAngle(a,b));
                        if(Tool.checkFitness(p,c,k,l)) {
                            Solver brunch = now.clone();
                            brunch.data.remove(i);
                            brunch.data.remove(j-1);
                            brunch.data.add(new Piece(p,c,k,l));
                            task_list.add(brunch);
                        }
                    }
                }
            }
        }
    }

    public static void recursion() {
        if(first.data.size() == 1) return;
        for(int i=0;i<first.data.size()-1;i++) {
            for(int j=i+1;j<first.data.size();j++) {
                for(int k=0;k<first.data.get(i).num;k++) {
                    for(int l=0;l<first.data.get(j).num;l++) {
                        Piece p = first.data.get(i);
                        Piece q = first.data.get(j);
                        Vector a = p.vectors[k];
                        Vector b = q.vectors[l];
                        System.out.printf("%d %d\n", k, l);
                        Piece c = new Piece(q,Tool.getLinalizeAngle(a,b));
                        if(Tool.checkFitness(p,c,k,l)) {
                            System.out.printf("hogehoge\n%s\n%s\n", a.toString(), c.get(l).toString());
                            Piece n = new Piece(p,c,k,l);
//                            System.out.println(i + ":" + j);
//                            System.out.println(solver);
//                            System.out.println(n);
                            first.data.add(n);
//                            System.out.println(p);
                            first.data.remove(i);
//                            System.out.println(q);
                            first.data.remove(i);
                            System.out.println(first);
                            recursion();
                            return;
                        }
                    }
                }
            }
        }
    }
*/
}
