// generated with ast extension for cup
// version 0.8
// 21/11/2024 0:58:15


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclarationListDerived1 extends MethodDeclarationList {

    private MethodDeclarationList MethodDeclarationList;
    private MethodDeclaration MethodDeclaration;

    public MethodDeclarationListDerived1 (MethodDeclarationList MethodDeclarationList, MethodDeclaration MethodDeclaration) {
        this.MethodDeclarationList=MethodDeclarationList;
        if(MethodDeclarationList!=null) MethodDeclarationList.setParent(this);
        this.MethodDeclaration=MethodDeclaration;
        if(MethodDeclaration!=null) MethodDeclaration.setParent(this);
    }

    public MethodDeclarationList getMethodDeclarationList() {
        return MethodDeclarationList;
    }

    public void setMethodDeclarationList(MethodDeclarationList MethodDeclarationList) {
        this.MethodDeclarationList=MethodDeclarationList;
    }

    public MethodDeclaration getMethodDeclaration() {
        return MethodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration MethodDeclaration) {
        this.MethodDeclaration=MethodDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclarationList!=null) MethodDeclarationList.accept(visitor);
        if(MethodDeclaration!=null) MethodDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.traverseTopDown(visitor);
        if(MethodDeclaration!=null) MethodDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclarationList!=null) MethodDeclarationList.traverseBottomUp(visitor);
        if(MethodDeclaration!=null) MethodDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclarationListDerived1(\n");

        if(MethodDeclarationList!=null)
            buffer.append(MethodDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclaration!=null)
            buffer.append(MethodDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclarationListDerived1]");
        return buffer.toString();
    }
}
