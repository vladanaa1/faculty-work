// generated with ast extension for cup
// version 0.8
// 21/11/2024 0:58:15


package rs.ac.bg.etf.pp1.ast;

public class ProgramDerived1 extends Program {

    private ProgramName ProgramName;
    private DeclarationsOptional DeclarationsOptional;
    private MethodDeclarationsOptional MethodDeclarationsOptional;

    public ProgramDerived1 (ProgramName ProgramName, DeclarationsOptional DeclarationsOptional, MethodDeclarationsOptional MethodDeclarationsOptional) {
        this.ProgramName=ProgramName;
        if(ProgramName!=null) ProgramName.setParent(this);
        this.DeclarationsOptional=DeclarationsOptional;
        if(DeclarationsOptional!=null) DeclarationsOptional.setParent(this);
        this.MethodDeclarationsOptional=MethodDeclarationsOptional;
        if(MethodDeclarationsOptional!=null) MethodDeclarationsOptional.setParent(this);
    }

    public ProgramName getProgramName() {
        return ProgramName;
    }

    public void setProgramName(ProgramName ProgramName) {
        this.ProgramName=ProgramName;
    }

    public DeclarationsOptional getDeclarationsOptional() {
        return DeclarationsOptional;
    }

    public void setDeclarationsOptional(DeclarationsOptional DeclarationsOptional) {
        this.DeclarationsOptional=DeclarationsOptional;
    }

    public MethodDeclarationsOptional getMethodDeclarationsOptional() {
        return MethodDeclarationsOptional;
    }

    public void setMethodDeclarationsOptional(MethodDeclarationsOptional MethodDeclarationsOptional) {
        this.MethodDeclarationsOptional=MethodDeclarationsOptional;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgramName!=null) ProgramName.accept(visitor);
        if(DeclarationsOptional!=null) DeclarationsOptional.accept(visitor);
        if(MethodDeclarationsOptional!=null) MethodDeclarationsOptional.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgramName!=null) ProgramName.traverseTopDown(visitor);
        if(DeclarationsOptional!=null) DeclarationsOptional.traverseTopDown(visitor);
        if(MethodDeclarationsOptional!=null) MethodDeclarationsOptional.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgramName!=null) ProgramName.traverseBottomUp(visitor);
        if(DeclarationsOptional!=null) DeclarationsOptional.traverseBottomUp(visitor);
        if(MethodDeclarationsOptional!=null) MethodDeclarationsOptional.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgramDerived1(\n");

        if(ProgramName!=null)
            buffer.append(ProgramName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DeclarationsOptional!=null)
            buffer.append(DeclarationsOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclarationsOptional!=null)
            buffer.append(MethodDeclarationsOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgramDerived1]");
        return buffer.toString();
    }
}
