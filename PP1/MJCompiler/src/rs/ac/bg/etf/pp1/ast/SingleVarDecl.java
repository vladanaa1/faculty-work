// generated with ast extension for cup
// version 0.8
// 22/11/2024 21:59:47


package rs.ac.bg.etf.pp1.ast;

public class SingleVarDecl extends VarDeclList {

    private Name Name;

    public SingleVarDecl (Name Name) {
        this.Name=Name;
        if(Name!=null) Name.setParent(this);
    }

    public Name getName() {
        return Name;
    }

    public void setName(Name Name) {
        this.Name=Name;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Name!=null) Name.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Name!=null) Name.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Name!=null) Name.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleVarDecl(\n");

        if(Name!=null)
            buffer.append(Name.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleVarDecl]");
        return buffer.toString();
    }
}
