// generated with ast extension for cup
// version 0.8
// 22/11/2024 21:59:47


package rs.ac.bg.etf.pp1.ast;

public class MethodParametersListDerived2 extends MethodParametersList {

    private MethodParameter MethodParameter;

    public MethodParametersListDerived2 (MethodParameter MethodParameter) {
        this.MethodParameter=MethodParameter;
        if(MethodParameter!=null) MethodParameter.setParent(this);
    }

    public MethodParameter getMethodParameter() {
        return MethodParameter;
    }

    public void setMethodParameter(MethodParameter MethodParameter) {
        this.MethodParameter=MethodParameter;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodParameter!=null) MethodParameter.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodParameter!=null) MethodParameter.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodParameter!=null) MethodParameter.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodParametersListDerived2(\n");

        if(MethodParameter!=null)
            buffer.append(MethodParameter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodParametersListDerived2]");
        return buffer.toString();
    }
}
