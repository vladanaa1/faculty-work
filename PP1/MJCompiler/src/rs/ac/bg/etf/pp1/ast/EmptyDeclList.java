// generated with ast extension for cup
// version 0.8
// 22/11/2024 23:42:19


package rs.ac.bg.etf.pp1.ast;

public class EmptyDeclList extends DeclarationsList {

    public EmptyDeclList () {
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
        buffer.append("EmptyDeclList(\n");

        buffer.append(tab);
        buffer.append(") [EmptyDeclList]");
        return buffer.toString();
    }
}
