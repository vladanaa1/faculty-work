// generated with ast extension for cup
// version 0.8
// 21/11/2024 0:58:15


package rs.ac.bg.etf.pp1.ast;

public class DeclarationsOptionalDerived1 extends DeclarationsOptional {

    private DeclarationsList DeclarationsList;

    public DeclarationsOptionalDerived1 (DeclarationsList DeclarationsList) {
        this.DeclarationsList=DeclarationsList;
        if(DeclarationsList!=null) DeclarationsList.setParent(this);
    }

    public DeclarationsList getDeclarationsList() {
        return DeclarationsList;
    }

    public void setDeclarationsList(DeclarationsList DeclarationsList) {
        this.DeclarationsList=DeclarationsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DeclarationsList!=null) DeclarationsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DeclarationsList!=null) DeclarationsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DeclarationsList!=null) DeclarationsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationsOptionalDerived1(\n");

        if(DeclarationsList!=null)
            buffer.append(DeclarationsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationsOptionalDerived1]");
        return buffer.toString();
    }
}
