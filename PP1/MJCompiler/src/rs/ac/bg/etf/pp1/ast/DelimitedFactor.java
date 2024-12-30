// generated with ast extension for cup
// version 0.8
// 29/11/2024 23:36:16


package rs.ac.bg.etf.pp1.ast;

public class DelimitedFactor extends Factor {

    private Expr2 Expr2;

    public DelimitedFactor (Expr2 Expr2) {
        this.Expr2=Expr2;
        if(Expr2!=null) Expr2.setParent(this);
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
        buffer.append("DelimitedFactor(\n");

        if(Expr2!=null)
            buffer.append(Expr2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DelimitedFactor]");
        return buffer.toString();
    }
}
