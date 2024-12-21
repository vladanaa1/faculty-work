// generated with ast extension for cup
// version 0.8
// 21/11/2024 0:58:15


package rs.ac.bg.etf.pp1.ast;

public class MethodParametersListDerived1 extends MethodParametersList {

    private MethodParametersList MethodParametersList;
    private MethodParameter MethodParameter;

    public MethodParametersListDerived1 (MethodParametersList MethodParametersList, MethodParameter MethodParameter) {
        this.MethodParametersList=MethodParametersList;
        if(MethodParametersList!=null) MethodParametersList.setParent(this);
        this.MethodParameter=MethodParameter;
        if(MethodParameter!=null) MethodParameter.setParent(this);
    }

    public MethodParametersList getMethodParametersList() {
        return MethodParametersList;
    }

    public void setMethodParametersList(MethodParametersList MethodParametersList) {
        this.MethodParametersList=MethodParametersList;
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
        if(MethodParametersList!=null) MethodParametersList.accept(visitor);
        if(MethodParameter!=null) MethodParameter.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodParametersList!=null) MethodParametersList.traverseTopDown(visitor);
        if(MethodParameter!=null) MethodParameter.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodParametersList!=null) MethodParametersList.traverseBottomUp(visitor);
        if(MethodParameter!=null) MethodParameter.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodParametersListDerived1(\n");

        if(MethodParametersList!=null)
            buffer.append(MethodParametersList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodParameter!=null)
            buffer.append(MethodParameter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodParametersListDerived1]");
        return buffer.toString();
    }
}
