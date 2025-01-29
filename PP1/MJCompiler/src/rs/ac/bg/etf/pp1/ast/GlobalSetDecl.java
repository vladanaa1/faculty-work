// generated with ast extension for cup
// version 0.8
// 28/0/2025 23:43:39


package rs.ac.bg.etf.pp1.ast;

public class GlobalSetDecl extends Declaration {

    private Set Set;
    private VarDeclList VarDeclList;

    public GlobalSetDecl (Set Set, VarDeclList VarDeclList) {
        this.Set=Set;
        if(Set!=null) Set.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
    }

    public Set getSet() {
        return Set;
    }

    public void setSet(Set Set) {
        this.Set=Set;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Set!=null) Set.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Set!=null) Set.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Set!=null) Set.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalSetDecl(\n");

        if(Set!=null)
            buffer.append(Set.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalSetDecl]");
        return buffer.toString();
    }
}
