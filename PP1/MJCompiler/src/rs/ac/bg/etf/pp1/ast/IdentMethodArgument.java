// generated with ast extension for cup
// version 0.8
// 19/0/2025 17:52:27


package rs.ac.bg.etf.pp1.ast;

public class IdentMethodArgument extends MethodArgument {

    private String ident;

    public IdentMethodArgument (String ident) {
        this.ident=ident;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident=ident;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentMethodArgument(\n");

        buffer.append(" "+tab+ident);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentMethodArgument]");
        return buffer.toString();
    }
}
