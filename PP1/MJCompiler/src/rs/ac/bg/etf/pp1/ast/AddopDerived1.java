// generated with ast extension for cup
// version 0.8
// 29/11/2024 23:36:16


package rs.ac.bg.etf.pp1.ast;

public class AddopDerived1 extends Addop {

    public AddopDerived1 () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddopDerived1(\n");

        buffer.append(tab);
        buffer.append(") [AddopDerived1]");
        return buffer.toString();
    }
}
