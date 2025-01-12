// generated with ast extension for cup
// version 0.8
// 12/0/2025 16:12:47


package rs.ac.bg.etf.pp1.ast;

public class LocalVarsDerived1 extends LocalVars {

    private LocalVars LocalVars;
    private LocalVar LocalVar;

    public LocalVarsDerived1 (LocalVars LocalVars, LocalVar LocalVar) {
        this.LocalVars=LocalVars;
        if(LocalVars!=null) LocalVars.setParent(this);
        this.LocalVar=LocalVar;
        if(LocalVar!=null) LocalVar.setParent(this);
    }

    public LocalVars getLocalVars() {
        return LocalVars;
    }

    public void setLocalVars(LocalVars LocalVars) {
        this.LocalVars=LocalVars;
    }

    public LocalVar getLocalVar() {
        return LocalVar;
    }

    public void setLocalVar(LocalVar LocalVar) {
        this.LocalVar=LocalVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalVars!=null) LocalVars.accept(visitor);
        if(LocalVar!=null) LocalVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVars!=null) LocalVars.traverseTopDown(visitor);
        if(LocalVar!=null) LocalVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVars!=null) LocalVars.traverseBottomUp(visitor);
        if(LocalVar!=null) LocalVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LocalVarsDerived1(\n");

        if(LocalVars!=null)
            buffer.append(LocalVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVar!=null)
            buffer.append(LocalVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LocalVarsDerived1]");
        return buffer.toString();
    }
}
