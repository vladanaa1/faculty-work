// generated with ast extension for cup
// version 0.8
// 29/11/2024 23:36:16


package rs.ac.bg.etf.pp1.ast;

public class SingleMethodArgument extends MethodArgumentList {

    private MethodArgument MethodArgument;

    public SingleMethodArgument (MethodArgument MethodArgument) {
        this.MethodArgument=MethodArgument;
        if(MethodArgument!=null) MethodArgument.setParent(this);
    }

    public MethodArgument getMethodArgument() {
        return MethodArgument;
    }

    public void setMethodArgument(MethodArgument MethodArgument) {
        this.MethodArgument=MethodArgument;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodArgument!=null) MethodArgument.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodArgument!=null) MethodArgument.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodArgument!=null) MethodArgument.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleMethodArgument(\n");

        if(MethodArgument!=null)
            buffer.append(MethodArgument.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleMethodArgument]");
        return buffer.toString();
    }
}
