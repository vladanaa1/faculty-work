// generated with ast extension for cup
// version 0.8
// 10/0/2025 17:16:44


package rs.ac.bg.etf.pp1.ast;

public class Print extends Matched {

    private Expr2 Expr2;

    public Print (Expr2 Expr2) {
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
        buffer.append("Print(\n");

        if(Expr2!=null)
            buffer.append(Expr2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Print]");
        return buffer.toString();
    }
}
