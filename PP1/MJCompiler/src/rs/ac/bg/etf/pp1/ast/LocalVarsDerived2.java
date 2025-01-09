// generated with ast extension for cup
// version 0.8
// 9/0/2025 20:33:43


package rs.ac.bg.etf.pp1.ast;

public class LocalVarsDerived2 extends LocalVars {

    public LocalVarsDerived2 () {
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
        buffer.append("LocalVarsDerived2(\n");

        buffer.append(tab);
        buffer.append(") [LocalVarsDerived2]");
        return buffer.toString();
    }
}
