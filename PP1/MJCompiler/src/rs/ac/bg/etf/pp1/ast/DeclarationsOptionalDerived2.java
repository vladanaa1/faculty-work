// generated with ast extension for cup
// version 0.8
// 21/11/2024 0:58:15


package rs.ac.bg.etf.pp1.ast;

public class DeclarationsOptionalDerived2 extends DeclarationsOptional {

    public DeclarationsOptionalDerived2 () {
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
        buffer.append("DeclarationsOptionalDerived2(\n");

        buffer.append(tab);
        buffer.append(") [DeclarationsOptionalDerived2]");
        return buffer.toString();
    }
}