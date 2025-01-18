// generated with ast extension for cup
// version 0.8
// 17/0/2025 17:54:13


package rs.ac.bg.etf.pp1.ast;

public class FunctionCall extends DesignatorStatement {

    private Designator Designator;
    private MethodArgumentsOptional MethodArgumentsOptional;

    public FunctionCall (Designator Designator, MethodArgumentsOptional MethodArgumentsOptional) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.MethodArgumentsOptional=MethodArgumentsOptional;
        if(MethodArgumentsOptional!=null) MethodArgumentsOptional.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public MethodArgumentsOptional getMethodArgumentsOptional() {
        return MethodArgumentsOptional;
    }

    public void setMethodArgumentsOptional(MethodArgumentsOptional MethodArgumentsOptional) {
        this.MethodArgumentsOptional=MethodArgumentsOptional;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(MethodArgumentsOptional!=null) MethodArgumentsOptional.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(MethodArgumentsOptional!=null) MethodArgumentsOptional.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(MethodArgumentsOptional!=null) MethodArgumentsOptional.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FunctionCall(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodArgumentsOptional!=null)
            buffer.append(MethodArgumentsOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FunctionCall]");
        return buffer.toString();
    }
}
