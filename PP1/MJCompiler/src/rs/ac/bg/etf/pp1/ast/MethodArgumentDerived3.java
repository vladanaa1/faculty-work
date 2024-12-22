// generated with ast extension for cup
// version 0.8
// 22/11/2024 23:42:19


package rs.ac.bg.etf.pp1.ast;

public class MethodArgumentDerived3 extends MethodArgument {

    private Literal Literal;

    public MethodArgumentDerived3 (Literal Literal) {
        this.Literal=Literal;
        if(Literal!=null) Literal.setParent(this);
    }

    public Literal getLiteral() {
        return Literal;
    }

    public void setLiteral(Literal Literal) {
        this.Literal=Literal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Literal!=null) Literal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Literal!=null) Literal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Literal!=null) Literal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodArgumentDerived3(\n");

        if(Literal!=null)
            buffer.append(Literal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodArgumentDerived3]");
        return buffer.toString();
    }
}
