// generated with ast extension for cup
// version 0.8
// 19/0/2025 17:52:26


package rs.ac.bg.etf.pp1.ast;

public class Const implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Name Name;
    private Literal Literal;

    public Const (Name Name, Literal Literal) {
        this.Name=Name;
        if(Name!=null) Name.setParent(this);
        this.Literal=Literal;
        if(Literal!=null) Literal.setParent(this);
    }

    public Name getName() {
        return Name;
    }

    public void setName(Name Name) {
        this.Name=Name;
    }

    public Literal getLiteral() {
        return Literal;
    }

    public void setLiteral(Literal Literal) {
        this.Literal=Literal;
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
        if(Name!=null) Name.accept(visitor);
        if(Literal!=null) Literal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Name!=null) Name.traverseTopDown(visitor);
        if(Literal!=null) Literal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Name!=null) Name.traverseBottomUp(visitor);
        if(Literal!=null) Literal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Const(\n");

        if(Name!=null)
            buffer.append(Name.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Literal!=null)
            buffer.append(Literal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Const]");
        return buffer.toString();
    }
}
