// generated with ast extension for cup
// version 0.8
// 17/0/2025 17:54:13


package rs.ac.bg.etf.pp1.ast;

public class NonEmptyMethodArguments extends MethodArgumentsOptional {

    private MethodArgumentList MethodArgumentList;

    public NonEmptyMethodArguments (MethodArgumentList MethodArgumentList) {
        this.MethodArgumentList=MethodArgumentList;
        if(MethodArgumentList!=null) MethodArgumentList.setParent(this);
    }

    public MethodArgumentList getMethodArgumentList() {
        return MethodArgumentList;
    }

    public void setMethodArgumentList(MethodArgumentList MethodArgumentList) {
        this.MethodArgumentList=MethodArgumentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodArgumentList!=null) MethodArgumentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodArgumentList!=null) MethodArgumentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodArgumentList!=null) MethodArgumentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NonEmptyMethodArguments(\n");

        if(MethodArgumentList!=null)
            buffer.append(MethodArgumentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NonEmptyMethodArguments]");
        return buffer.toString();
    }
}
