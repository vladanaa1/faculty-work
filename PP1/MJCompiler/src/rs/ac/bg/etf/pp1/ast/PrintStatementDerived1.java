// generated with ast extension for cup
// version 0.8
// 21/11/2024 0:58:15


package rs.ac.bg.etf.pp1.ast;

public class PrintStatementDerived1 extends PrintStatement {

    public PrintStatementDerived1 () {
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
        buffer.append("PrintStatementDerived1(\n");

        buffer.append(tab);
        buffer.append(") [PrintStatementDerived1]");
        return buffer.toString();
    }
}
