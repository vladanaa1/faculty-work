// generated with ast extension for cup
// version 0.8
// 21/11/2024 0:58:15


package rs.ac.bg.etf.pp1.ast;

public class Expr2Derived1 extends Expr2 {

    private Term Term;
    private AddopGroup AddopGroup;

    public Expr2Derived1 (Term Term, AddopGroup AddopGroup) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.AddopGroup=AddopGroup;
        if(AddopGroup!=null) AddopGroup.setParent(this);
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
        if(Term!=null) Term.accept(visitor);
        if(AddopGroup!=null) AddopGroup.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(AddopGroup!=null) AddopGroup.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(AddopGroup!=null) AddopGroup.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr2Derived1(\n");

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
        buffer.append(") [Expr2Derived1]");
        return buffer.toString();
    }
}
