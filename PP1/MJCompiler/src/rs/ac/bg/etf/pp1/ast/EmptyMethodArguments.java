// generated with ast extension for cup
// version 0.8
// 23/0/2025 16:11:34


package rs.ac.bg.etf.pp1.ast;

public class EmptyMethodArguments extends MethodArgumentsOptional {

    public EmptyMethodArguments () {
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
        buffer.append("EmptyMethodArguments(\n");

        buffer.append(tab);
        buffer.append(") [EmptyMethodArguments]");
        return buffer.toString();
    }
}
