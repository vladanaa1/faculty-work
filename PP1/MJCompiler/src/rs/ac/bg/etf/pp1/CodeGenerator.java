package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.*;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.ac.bg.etf.pp1.SemanticPass;

public class CodeGenerator extends VisitorAdaptor {
	
	// EQ = 0, NE = 1, LT = 2, LE = 3, GT = 4, GE = 5;
	private int currentCondJump = 0;
	
	// Unary operator "-" flag
	private boolean negativeExpr = false;
	
	private int varCount;
	
	private int paramCnt;
	
	// Main PC
	private int mainPc;
	
	// Helpers for resolving and patching conditional jumps
	private Stack<Integer> skipJumps = new Stack<>();
	private Stack<Integer> skipConditions = new Stack<>();
	private Stack<Integer> skipThen = new Stack<Integer>();
	private Stack<Integer> skipElseJump = new Stack<Integer>();
	private Stack<Integer> doWhileStartAddress = new Stack<Integer>();
	
	private Stack<List<Integer>> breakJumps = new Stack<>();
	private Stack<List<Integer>> continueJumps = new Stack<>();
	
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
	
	public void visit(PrintWithComma printWithComma) {
		Obj printObj = printWithComma.getExpr2().obj;
		
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
		
		if(negativeExpr) {
			Code.put(Code.neg);
			negativeExpr = false;
		}
	}
	
	public void visit(CharFactor charFactor) {
		Code.load(new Obj(Obj.Con, "$", charFactor.obj.getType(), charFactor.getValue(), 0));
	}
	
	public void visit(BoolFactor boolFactor) {
		Code.load(new Obj(Obj.Con, "$", boolFactor.obj.getType(), boolFactor.getValue() ? 1 : 0, 0));
	}
	
	public void visit(MethodSignature2 methodSignature) {
		// enter b1, b2
		// b1 -> formal parameters
		// b2 -> formal parameters + local variables (sizeof(locals))
		
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
		methodNode.traverseTopDown(paramCnt);
		
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
	
	/*
	public void visit(DesignatorIdent designatorIdent) {
		SyntaxNode parent = designatorIdent.getParent();
		
		// SyntaxNode grandparent = parent.getParent();
		
		if(DesignatorAssign.class != parent.getClass() && MethodCallFactor.class != parent.getClass()) {
			Code.load(designatorIdent.obj);
			if(negativeExpr) {
				Code.put(Code.neg);
				negativeExpr = false;
			}
		}
	}
	*/
	
	public void visit(DesignatorIdent designatorIdent) {
		if(designatorIdent.getParent().getClass() == DesignatorSelect.class) {
			Code.load(designatorIdent.obj);
		}
	}
	
	public void visit(DesignatorFactor designatorFactor) {
		if(negativeExpr) {
			Code.put(Code.neg);
			negativeExpr = false;
		}
		Code.load(designatorFactor.getDesignator().obj);
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
		
		if(!functionObj.getType().equals(Tab.noType)) {
			Code.put(Code.pop);
		}
	}
	
	public void visit(NewVectorFactor newVectorFactor) {
		Code.put(Code.newarray);
		
		Obj arrayFactor = newVectorFactor.obj;
		
		if(arrayFactor.getType().getElemType().equals(Tab.intType)) {
			Code.put(1);
		}
		else
			Code.put(0);
	}
	
	public void visit(ReturnWithExpr returnWithExpr) {
		// NE VALJA!!!
		Code.load(returnWithExpr.getExpr2().obj);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(Return returnWithoutExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(DesignatorMapExpr designatorMapExpr) {
		/*
		Funkcija predstavljena levim neterminalom Designator se poziva za sve elemente niza 
		predstavljenim desnim neterminalom Designator. Dobijeni neterminal Expr predstavlja zbir povratnih 
		vrednosti svih izvršenih poziva funkcije.
		*/
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
	
	public void visit(ExprCondFactor exprCondFactor) {
		// IF(EXPR) <=> IF(EXPR != 0)
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0); // FALSE (EXPR == 0)
		skipJumps.push(Code.pc - 2); // TRUE (EXPR != 0, NO JUMP)
	}
	
	public void visit(RelopCondFactor relopCondFactor) {
		if(relopCondFactor.getRelop().getClass() == EqRelop.class) {
			currentCondJump = Code.eq;
		}
		else if(relopCondFactor.getRelop().getClass() == NeqRelop.class) {
			currentCondJump = Code.ne;
		}
		else if(relopCondFactor.getRelop().getClass() == GtRelop.class) {
			currentCondJump = Code.gt;
		}
		else if(relopCondFactor.getRelop().getClass() == GteqRelop.class) {
			currentCondJump = Code.ge;
		}
		else if(relopCondFactor.getRelop().getClass() == LsRelop.class) {
			currentCondJump = Code.lt;
		}
		else if(relopCondFactor.getRelop().getClass() == LseqRelop.class) {
			currentCondJump = Code.le;
		}
		
		Code.putFalseJump(currentCondJump, 0); // FALSE
		skipJumps.push(Code.pc - 2); // TRUE (NO JUMP)
	}
	
	public void visit(CondTerm condTerm) {
		Code.putJump(0);
		
		skipConditions.push(Code.pc - 2);
		
		while(!skipJumps.empty()) {
			Code.fixup(skipJumps.pop());
		}
	}
	
	public void visit(Condition condition) {
		Code.putJump(0);
		
		skipThen.push(Code.pc - 2);
		
		while(!skipConditions.empty()) {
			Code.fixup(skipConditions.pop());
		}
	}
	
	public void visit(UnmatchedIf unmatchedIf) {
		Code.fixup(skipThen.pop()); // fixing-up jump in condition
	}
	
	public void visit(Else elseStatement) {
		Code.putJump(0);
		
		Code.fixup(skipThen.pop()); // fixing-up jump in condition
		
		skipElseJump.push(Code.pc - 2);
	}
	
	public void visit(MatchedIf matchedIf) {
		Code.fixup(skipElseJump.pop()); // fixing-up jump in elseStmt
	}
	
	public void visit(DoStatement doStatement) {
		doWhileStartAddress.push(Code.pc);
		
		breakJumps.push(new ArrayList<Integer>());
		continueJumps.push(new ArrayList<Integer>());
	}
	
	public void visit(WhileStatement whileStatement) {
		while(!continueJumps.peek().isEmpty()) {
			Code.fixup(continueJumps.peek().remove(0));
		}
		continueJumps.pop();
	}
	
	public void visit(DoWhileWithCond doWhileWithCond) {
		
		Code.putJump(doWhileStartAddress.pop());
		Code.fixup(skipThen.pop());
		
		while(!breakJumps.peek().isEmpty()) {
			Code.fixup(breakJumps.peek().remove(0));
		}
		breakJumps.pop();
	}
	
	public void visit(DoWhileWithCondNStmt doWhileWithCondNStmt) {
		Code.putJump(doWhileStartAddress.pop());
		Code.fixup(skipThen.pop());
		
		while(!breakJumps.peek().isEmpty()) {
			Code.fixup(breakJumps.peek().remove(0));
		}
		breakJumps.pop();
	}
	
	public void visit(Continue continueStatement) {
		Code.putJump(0);
		continueJumps.peek().add(Code.pc - 2);
	}
	
	public void visit(Break breakStatement) {
		Code.putJump(0);
		breakJumps.peek().add(Code.pc - 2);
	}
	
	/* Ne koristimo inc i dec jer oni rade samo sa lokalnim promenljivama */
	
	public void visit(DesignatorInc designatorInc) {
		Obj designatorObj = designatorInc.getDesignator().obj;
		
		// Code.put(Code.dup2);
		
		Code.load(designatorObj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorObj);
	}
	
	public void visit(DesignatorDec designatorDec) {
		Obj designatorObj = designatorDec.getDesignator().obj;
		
		// Code.put(Code.dup2);
		
		Code.load(designatorObj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorObj);
	}
	
	public void visit(NegativeOperator negativeOperator) {
		negativeExpr = true;
	}
	
	// NE VALJA!!!
	
	public void visit(IdentMethodArgument ident) {
		Code.load(new Obj(Obj.Var, ident.getIdent(), Tab.noType));
	}
	
	public void visit(IdentVectorMethodArgument ident) {
		Code.load(new Obj(Obj.Var, ident.getIdent(), Tab.noType));
	}
	
	public void visit(LiteralMethodArgument literal) {
		Code.load(new Obj(Obj.Var, literal.getLiteral().obj.getName(), literal.getLiteral().obj.getType()));
	}
	
	
}
