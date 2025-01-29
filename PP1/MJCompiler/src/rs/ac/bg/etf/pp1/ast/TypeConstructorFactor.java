// generated with ast extension for cup
// version 0.8
// 28/0/2025 23:43:39


package rs.ac.bg.etf.pp1.ast;

public class TypeConstructorFactor extends Factor {

    private Type Type;
    private MethodArgumentList MethodArgumentList;

    public TypeConstructorFactor (Type Type, MethodArgumentList MethodArgumentList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.MethodArgumentList=MethodArgumentList;
        if(MethodArgumentList!=null) MethodArgumentList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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
        if(Type!=null) Type.accept(visitor);
        if(MethodArgumentList!=null) MethodArgumentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(MethodArgumentList!=null) MethodArgumentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(MethodArgumentList!=null) MethodArgumentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TypeConstructorFactor(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodArgumentList!=null)
            buffer.append(MethodArgumentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypeConstructorFactor]");
        return buffer.toString();
    }
}
