// generated with ast extension for cup
// version 0.8
// 28/0/2025 23:43:39


package rs.ac.bg.etf.pp1.ast;

public class NegativeTermExprSum extends Expr2 {

    private NegOp NegOp;
    private Term Term;
    private AddopGroup AddopGroup;

    public NegativeTermExprSum (NegOp NegOp, Term Term, AddopGroup AddopGroup) {
        this.NegOp=NegOp;
        if(NegOp!=null) NegOp.setParent(this);
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.AddopGroup=AddopGroup;
        if(AddopGroup!=null) AddopGroup.setParent(this);
    }

    public NegOp getNegOp() {
        return NegOp;
    }

    public void setNegOp(NegOp NegOp) {
        this.NegOp=NegOp;
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public AddopGroup getAddopGroup() {
        return AddopGroup;
    }

    public void setAddopGroup(AddopGroup AddopGroup) {
        this.AddopGroup=AddopGroup;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NegOp!=null) NegOp.accept(visitor);
        if(Term!=null) Term.accept(visitor);
        if(AddopGroup!=null) AddopGroup.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NegOp!=null) NegOp.traverseTopDown(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(AddopGroup!=null) AddopGroup.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NegOp!=null) NegOp.traverseBottomUp(visitor);
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(AddopGroup!=null) AddopGroup.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NegativeTermExprSum(\n");

        if(NegOp!=null)
            buffer.append(NegOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddopGroup!=null)
            buffer.append(AddopGroup.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NegativeTermExprSum]");
        return buffer.toString();
    }
}
