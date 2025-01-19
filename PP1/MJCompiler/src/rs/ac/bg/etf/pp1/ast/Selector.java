// generated with ast extension for cup
// version 0.8
// 19/0/2025 17:52:27


package rs.ac.bg.etf.pp1.ast;

public class Selector implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private LBracket LBracket;
    private Expr2 Expr2;

    public Selector (LBracket LBracket, Expr2 Expr2) {
        this.LBracket=LBracket;
        if(LBracket!=null) LBracket.setParent(this);
        this.Expr2=Expr2;
        if(Expr2!=null) Expr2.setParent(this);
    }

    public LBracket getLBracket() {
        return LBracket;
    }

    public void setLBracket(LBracket LBracket) {
        this.LBracket=LBracket;
    }

    public Expr2 getExpr2() {
        return Expr2;
    }

    public void setExpr2(Expr2 Expr2) {
        this.Expr2=Expr2;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LBracket!=null) LBracket.accept(visitor);
        if(Expr2!=null) Expr2.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LBracket!=null) LBracket.traverseTopDown(visitor);
        if(Expr2!=null) Expr2.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LBracket!=null) LBracket.traverseBottomUp(visitor);
        if(Expr2!=null) Expr2.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Selector(\n");

        if(LBracket!=null)
            buffer.append(LBracket.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr2!=null)
            buffer.append(Expr2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Selector]");
        return buffer.toString();
    }
}
