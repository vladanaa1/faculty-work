// generated with ast extension for cup
// version 0.8
// 9/0/2025 20:33:43


package rs.ac.bg.etf.pp1.ast;

public class MethodSignature implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodSignature2 MethodSignature2;
    private MethodParametersOptional MethodParametersOptional;
    private MethodSignatureEnd MethodSignatureEnd;

    public MethodSignature (MethodSignature2 MethodSignature2, MethodParametersOptional MethodParametersOptional, MethodSignatureEnd MethodSignatureEnd) {
        this.MethodSignature2=MethodSignature2;
        if(MethodSignature2!=null) MethodSignature2.setParent(this);
        this.MethodParametersOptional=MethodParametersOptional;
        if(MethodParametersOptional!=null) MethodParametersOptional.setParent(this);
        this.MethodSignatureEnd=MethodSignatureEnd;
        if(MethodSignatureEnd!=null) MethodSignatureEnd.setParent(this);
    }

    public MethodSignature2 getMethodSignature2() {
        return MethodSignature2;
    }

    public void setMethodSignature2(MethodSignature2 MethodSignature2) {
        this.MethodSignature2=MethodSignature2;
    }

    public MethodParametersOptional getMethodParametersOptional() {
        return MethodParametersOptional;
    }

    public void setMethodParametersOptional(MethodParametersOptional MethodParametersOptional) {
        this.MethodParametersOptional=MethodParametersOptional;
    }

    public MethodSignatureEnd getMethodSignatureEnd() {
        return MethodSignatureEnd;
    }

    public void setMethodSignatureEnd(MethodSignatureEnd MethodSignatureEnd) {
        this.MethodSignatureEnd=MethodSignatureEnd;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodSignature2!=null) MethodSignature2.accept(visitor);
        if(MethodParametersOptional!=null) MethodParametersOptional.accept(visitor);
        if(MethodSignatureEnd!=null) MethodSignatureEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodSignature2!=null) MethodSignature2.traverseTopDown(visitor);
        if(MethodParametersOptional!=null) MethodParametersOptional.traverseTopDown(visitor);
        if(MethodSignatureEnd!=null) MethodSignatureEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodSignature2!=null) MethodSignature2.traverseBottomUp(visitor);
        if(MethodParametersOptional!=null) MethodParametersOptional.traverseBottomUp(visitor);
        if(MethodSignatureEnd!=null) MethodSignatureEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodSignature(\n");

        if(MethodSignature2!=null)
            buffer.append(MethodSignature2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodParametersOptional!=null)
            buffer.append(MethodParametersOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodSignatureEnd!=null)
            buffer.append(MethodSignatureEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodSignature]");
        return buffer.toString();
    }
}
