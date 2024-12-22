// generated with ast extension for cup
// version 0.8
// 22/11/2024 21:59:47


package rs.ac.bg.etf.pp1.ast;

public class MethodParametersOptionalDerived1 extends MethodParametersOptional {

    private MethodParametersList MethodParametersList;

    public MethodParametersOptionalDerived1 (MethodParametersList MethodParametersList) {
        this.MethodParametersList=MethodParametersList;
        if(MethodParametersList!=null) MethodParametersList.setParent(this);
    }

    public MethodParametersList getMethodParametersList() {
        return MethodParametersList;
    }

    public void setMethodParametersList(MethodParametersList MethodParametersList) {
        this.MethodParametersList=MethodParametersList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodParametersList!=null) MethodParametersList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodParametersList!=null) MethodParametersList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodParametersList!=null) MethodParametersList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodParametersOptionalDerived1(\n");

        if(MethodParametersList!=null)
            buffer.append(MethodParametersList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodParametersOptionalDerived1]");
        return buffer.toString();
    }
}
