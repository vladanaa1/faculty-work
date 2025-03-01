// generated with ast extension for cup
// version 0.8
// 22/11/2024 23:42:19


package rs.ac.bg.etf.pp1.ast;

public class MethodArgumentsOptionalDerived1 extends MethodArgumentsOptional {

    private MethodArgumentList MethodArgumentList;

    public MethodArgumentsOptionalDerived1 (MethodArgumentList MethodArgumentList) {
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
        buffer.append("MethodArgumentsOptionalDerived1(\n");

        if(MethodArgumentList!=null)
            buffer.append(MethodArgumentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodArgumentsOptionalDerived1]");
        return buffer.toString();
    }
}
