// generated with ast extension for cup
// version 0.8
// 12/0/2025 0:19:33


package rs.ac.bg.etf.pp1.ast;

public class NegativeOperator extends NegOp {

    public NegativeOperator () {
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
        buffer.append("NegativeOperator(\n");

        buffer.append(tab);
        buffer.append(") [NegativeOperator]");
        return buffer.toString();
    }
}
