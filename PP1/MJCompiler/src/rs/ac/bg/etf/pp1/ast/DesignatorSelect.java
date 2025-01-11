// generated with ast extension for cup
// version 0.8
// 11/0/2025 22:55:58


package rs.ac.bg.etf.pp1.ast;

public class DesignatorSelect extends Designator {

    private Designator Designator;
    private Selector Selector;

    public DesignatorSelect (Designator Designator, Selector Selector) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.Selector=Selector;
        if(Selector!=null) Selector.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public Selector getSelector() {
        return Selector;
    }

    public void setSelector(Selector Selector) {
        this.Selector=Selector;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(Selector!=null) Selector.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(Selector!=null) Selector.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(Selector!=null) Selector.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorSelect(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Selector!=null)
            buffer.append(Selector.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorSelect]");
        return buffer.toString();
    }
}
