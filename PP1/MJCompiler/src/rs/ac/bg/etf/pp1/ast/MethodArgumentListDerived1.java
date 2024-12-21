// generated with ast extension for cup
// version 0.8
// 21/11/2024 0:58:15


package rs.ac.bg.etf.pp1.ast;

public class MethodArgumentListDerived1 extends MethodArgumentList {

    private MethodArgumentList MethodArgumentList;
    private MethodArgument MethodArgument;

    public MethodArgumentListDerived1 (MethodArgumentList MethodArgumentList, MethodArgument MethodArgument) {
        this.MethodArgumentList=MethodArgumentList;
        if(MethodArgumentList!=null) MethodArgumentList.setParent(this);
        this.MethodArgument=MethodArgument;
        if(MethodArgument!=null) MethodArgument.setParent(this);
    }

    public MethodArgumentList getMethodArgumentList() {
        return MethodArgumentList;
    }

    public void setMethodArgumentList(MethodArgumentList MethodArgumentList) {
        this.MethodArgumentList=MethodArgumentList;
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
        if(MethodArgumentList!=null) MethodArgumentList.accept(visitor);
        if(MethodArgument!=null) MethodArgument.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodArgumentList!=null) MethodArgumentList.traverseTopDown(visitor);
        if(MethodArgument!=null) MethodArgument.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodArgumentList!=null) MethodArgumentList.traverseBottomUp(visitor);
        if(MethodArgument!=null) MethodArgument.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodArgumentListDerived1(\n");

        if(MethodArgumentList!=null)
            buffer.append(MethodArgumentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodArgument!=null)
            buffer.append(MethodArgument.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodArgumentListDerived1]");
        return buffer.toString();
    }
}
