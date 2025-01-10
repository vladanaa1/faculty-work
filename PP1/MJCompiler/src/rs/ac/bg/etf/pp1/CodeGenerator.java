package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.*;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private int varCount;
	
	private int paramCnt;
	
	private int mainPc;
	
	public int getMainPc() {
		return mainPc;
	}
	
	public void visit(Print printStmt) {
		Obj printObj = printStmt.getExpr2().obj;
		
		if(printObj == null) {
			return;
		}
		
		if(printObj.getType().equals(Tab.intType)) {
			Code.loadConst(5);
			Code.put(Code.print);
		}
		else if(printObj.getType().equals(Tab.charType)) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(NumberFactor numberFactor) {
		Code.load(new Obj(Obj.Con, "$", numberFactor.obj.getType(), numberFactor.getValue(), 0));
	}
	
	public void visit(CharFactor charFactor) {
		Code.load(new Obj(Obj.Con, "$", charFactor.obj.getType(), charFactor.getValue(), 0));
	}
	
	public void visit(BoolFactor boolFactor) {
		Code.load(new Obj(Obj.Con, "$", boolFactor.obj.getType(), boolFactor.getValue() ? 1 : 0, 0));
	}
	
	public void visit(MethodSignature2 methodSignature) {
		
		String ident = methodSignature.getName().getIdent();
		
		if(ident.equals("main")) {
			mainPc = Code.pc;
		}
		
		Obj methodObj = methodSignature.obj;
		
		methodObj.setAdr(Code.pc);
		
		// Collect parameters and local variables
		SyntaxNode methodNode = methodSignature.getParent().getParent(); // MethodDeclaration
		
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter paramCnt = new FormParamCounter();
		methodNode.traverseTopDown(varCnt);
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(paramCnt.getCount());
		Code.put(varCnt.getCount() + paramCnt.getCount());
	}
	
	public void visit(MethodDeclaration methodDeclaration) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(DesignatorAssign designatorAssign) {
		Code.store(designatorAssign.getDesignator().obj);
	}
	
	public void visit(DesignatorIdent designatorIdent) {
		SyntaxNode parent = designatorIdent.getParent();
		
		if(DesignatorAssign.class != parent.getClass() && MethodCallFactor.class != parent.getClass()) {
			Code.load(designatorIdent.obj);
		}
	}
	
	public void visit(FunctionCall functionCall) {
		Obj functionObj = functionCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		
		Code.put(Code.call);
		Code.put2(offset);
		
		
		if(!functionObj.getType().equals(Tab.noType)) {
			Code.put(Code.pop);
		}
	}
	
	public void visit(MethodCallFactor methodCallFactor) {
		Obj functionObj = methodCallFactor.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	public void visit(ReturnWithExpr returnWithExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(Return returnWithoutExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(AddopGroupTerm addopGroupTerm) {
		if(addopGroupTerm.getAddop().getClass() == Minus.class) {
			Code.put(Code.sub);
		}
		else {
			Code.put(Code.add);
		}
	}
	
	public void visit(AddopTerm addopTerm) {
		if(addopTerm.getAddop().getClass() == Minus.class) {
			Code.put(Code.sub);
		}
		else {
			Code.put(Code.add);
		}
	}
	
	public void visit(MulopTerm mulopTerm) {
		if(mulopTerm.getMulop().getClass() == ModOp.class) {
			Code.put(Code.rem);
		}
		else if(mulopTerm.getMulop().getClass() == DivOp.class) {
			Code.put(Code.div);
		}
		else if(mulopTerm.getMulop().getClass() == MultOp.class) {
			Code.put(Code.mul);
		}
	}
	
	public void visit(RelopCondFactor relopCondFactor) {
		if(relopCondFactor.getRelop().getClass() == EqRelop.class) {
			Code.put(Code.eq);
		}
		else if(relopCondFactor.getRelop().getClass() == NeqRelop.class) {
			Code.put(Code.ne);
		}
		else if(relopCondFactor.getRelop().getClass() == GtRelop.class) {
			Code.put(Code.gt);
		}
		else if(relopCondFactor.getRelop().getClass() == GteqRelop.class) {
			Code.put(Code.ge);
		}
		else if(relopCondFactor.getRelop().getClass() == LsRelop.class) {
			Code.put(Code.lt);
		}
		else if(relopCondFactor.getRelop().getClass() == LseqRelop.class) {
			Code.put(Code.le);
		}
	}
}
