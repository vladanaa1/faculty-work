// generated with ast extension for cup
// version 0.8
// 19/0/2025 17:52:27


package rs.ac.bg.etf.pp1.ast;

public class LsRelop extends Relop {

    public LsRelop () {
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
        buffer.append("LsRelop(\n");

        buffer.append(tab);
        buffer.append(") [LsRelop]");
        return buffer.toString();
    }
}
