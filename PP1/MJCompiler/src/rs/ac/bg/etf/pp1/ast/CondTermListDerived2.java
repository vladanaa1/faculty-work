// generated with ast extension for cup
// version 0.8
// 11/0/2025 22:55:58


package rs.ac.bg.etf.pp1.ast;

public class CondTermListDerived2 extends CondTermList {

    public CondTermListDerived2 () {
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
        buffer.append("CondTermListDerived2(\n");

        buffer.append(tab);
        buffer.append(") [CondTermListDerived2]");
        return buffer.toString();
    }
}
