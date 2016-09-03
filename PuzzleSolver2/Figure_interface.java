/////////////////////////////////////////////////////////////
//  Figure_interfaceインターフェース：図形系クラスに共通のメソッドを定義
/////////////////////////////////////////////////////////////

import java.util.Scanner;

public interface Figure_interface {
    public abstract double calcArea();
    public abstract void read(Scanner stdIn);
    public abstract String toString();
    public abstract String toStringAll();
    public abstract String toStringForRead();
}
