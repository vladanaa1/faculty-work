// generated with ast extension for cup
// version 0.8
// 11/0/2025 22:55:58


package rs.ac.bg.etf.pp1.ast;

public class IdentVectorMethodArgument extends MethodArgument {

    private String ident;
    private Expr2 Expr2;

    public IdentVectorMethodArgument (String ident, Expr2 Expr2) {
        this.ident=ident;
        this.Expr2=Expr2;
        if(Expr2!=null) Expr2.setParent(this);
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident=ident;
    }

    public Expr2 getExpr2() {
        return Expr2;
    }

    public void setExpr2(Expr2 Expr2) {
        this.Expr2=Expr2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr2!=null) Expr2.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr2!=null) Expr2.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr2!=null) Expr2.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentVectorMethodArgument(\n");

        buffer.append(" "+tab+ident);
        buffer.append("\n");

        if(Expr2!=null)
            buffer.append(Expr2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentVectorMethodArgument]");
        return buffer.toString();
    }
}
