// generated with ast extension for cup
// version 0.8
// 12/0/2025 16:12:47


package rs.ac.bg.etf.pp1.ast;

public class RelopCondFactor extends CondFact {

    private Expr2 Expr2;
    private Relop Relop;
    private Expr2 Expr21;

    public RelopCondFactor (Expr2 Expr2, Relop Relop, Expr2 Expr21) {
        this.Expr2=Expr2;
        if(Expr2!=null) Expr2.setParent(this);
        this.Relop=Relop;
        if(Relop!=null) Relop.setParent(this);
        this.Expr21=Expr21;
        if(Expr21!=null) Expr21.setParent(this);
    }

    public Expr2 getExpr2() {
        return Expr2;
    }

    public void setExpr2(Expr2 Expr2) {
        this.Expr2=Expr2;
    }

    public Relop getRelop() {
        return Relop;
    }

    public void setRelop(Relop Relop) {
        this.Relop=Relop;
    }

    public Expr2 getExpr21() {
        return Expr21;
    }

    public void setExpr21(Expr2 Expr21) {
        this.Expr21=Expr21;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr2!=null) Expr2.accept(visitor);
        if(Relop!=null) Relop.accept(visitor);
        if(Expr21!=null) Expr21.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr2!=null) Expr2.traverseTopDown(visitor);
        if(Relop!=null) Relop.traverseTopDown(visitor);
        if(Expr21!=null) Expr21.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr2!=null) Expr2.traverseBottomUp(visitor);
        if(Relop!=null) Relop.traverseBottomUp(visitor);
        if(Expr21!=null) Expr21.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RelopCondFactor(\n");

        if(Expr2!=null)
            buffer.append(Expr2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Relop!=null)
            buffer.append(Relop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr21!=null)
            buffer.append(Expr21.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RelopCondFactor]");
        return buffer.toString();
    }
}
