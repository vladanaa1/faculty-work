// generated with ast extension for cup
// version 0.8
// 23/0/2025 16:11:34


package rs.ac.bg.etf.pp1.ast;

public class LseqRelop extends Relop {

    public LseqRelop () {
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
        buffer.append("LseqRelop(\n");

        buffer.append(tab);
        buffer.append(") [LseqRelop]");
        return buffer.toString();
    }
}
