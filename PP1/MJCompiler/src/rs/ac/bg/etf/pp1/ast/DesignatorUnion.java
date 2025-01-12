// generated with ast extension for cup
// version 0.8
// 12/0/2025 16:12:47


package rs.ac.bg.etf.pp1.ast;

public class DesignatorUnion extends DesignatorStatement {

    private Designator Designator;
    private Designator Designator1;
    private Designator Designator2;

    public DesignatorUnion (Designator Designator, Designator Designator1, Designator Designator2) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.Designator1=Designator1;
        if(Designator1!=null) Designator1.setParent(this);
        this.Designator2=Designator2;
        if(Designator2!=null) Designator2.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public Designator getDesignator1() {
        return Designator1;
    }

    public void setDesignator1(Designator Designator1) {
        this.Designator1=Designator1;
    }

    public Designator getDesignator2() {
        return Designator2;
    }

    public void setDesignator2(Designator Designator2) {
        this.Designator2=Designator2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(Designator1!=null) Designator1.accept(visitor);
        if(Designator2!=null) Designator2.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(Designator1!=null) Designator1.traverseTopDown(visitor);
        if(Designator2!=null) Designator2.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(Designator1!=null) Designator1.traverseBottomUp(visitor);
        if(Designator2!=null) Designator2.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorUnion(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator1!=null)
            buffer.append(Designator1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator2!=null)
            buffer.append(Designator2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorUnion]");
        return buffer.toString();
    }
}
