package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.*;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	// PC addresses relevant for add method
	private final static int BRANCH_ON_EQUAL_TO_ZERO = 28;
	private final static int BRANCH_ON_EQUAL_VALUES = 39;
	
	// PC addresses relevant for addAll method
	private final static int METHOD_BEGIN = 75;
	private final static int WHILE_BEGIN = 86;
	private final static int LABEL1 = 109;
	private final static int LABEL2 = 122;
	private final static int EXIT = 129;
	
	// PC addresses relevant for print set
	private final static int LABEL_OFFSET = 12;
	private final static int PRINT_BEGIN_OFFSET = -12;
	
	// EQ = 0, NE = 1, LT = 2, LE = 3, GT = 4, GE = 5;
	private int currentCondJump = 0;
	
	// Unary operator "-" flag
	private boolean negativeExpr = false;
	
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
	
	private HashMap<Obj, Integer> setToSize = new HashMap<>();
	// private HashMap<Obj, Integer> arrayToSize = new HashMap<>();
	
	private Stack<Obj> arguments = new Stack<Obj>();
	
	private void initializePredeclaredMethods() {
		Obj chrMeth = Tab.find("chr");
		Obj ordMeth = Tab.find("ord");
		
		ordMeth.setAdr(Code.pc);
		chrMeth.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Obj lenMeth = Tab.find("len");
	
		lenMeth.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);	
		
		Obj addMethod = Tab.find("add");
		
		addMethod.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(4);
		Code.put(4);
		Collection<Obj> paramethers = addMethod.getLocalSymbols();
		
		Iterator<Obj> iterator = paramethers.iterator();
		
		Obj setObj = iterator.next();
		Obj valueObj = iterator.next();
		Obj indexObj = iterator.next();
		Obj lenObj = iterator.next();
		
		// i = 0
		Code.loadConst(0);
		Code.store(indexObj);
		
		// len = set[arraylen - 1]
		Code.load(setObj);
		Code.load(setObj);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.put(Code.aload);
		Code.store(lenObj);
		
		// WHILE_BEGIN, PC = 25
		
		// index, len
		Code.load(indexObj);
		Code.load(lenObj);
		
		// IF(index != len) ... ELSE BRANCH_ON_EQUAL_VALUES
		Code.putFalseJump(Code.ne, 44);
		
		// set[index], value
		Code.load(setObj);
		Code.load(indexObj);
		Code.put(Code.aload);
		Code.load(valueObj);
		
		// IF(set[index] != value) ... ELSE EXIT
		Code.putFalseJump(Code.ne, 70);
		
		Code.load(indexObj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(indexObj);
		
		// GOTO WHILE_BEGIN
		Code.putJump(25);
		
		// BRANCH_ON_EQUAL_VALUES, PC = 44
		
		Code.load(indexObj);
		Code.load(setObj);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		
		// IF(i != arraylen - 1) ... ELSE EXIT
		Code.putFalseJump(Code.ne, 70);
		
		// set[index] = value
		Code.load(setObj);
		Code.load(indexObj);
		Code.load(valueObj);
		Code.put(Code.astore);
		
		// set
		Code.load(setObj);
		
		// set, index
		Code.load(setObj);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		
		// set, index, value
		Code.load(setObj);
		Code.load(setObj);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.put(Code.aload);
		Code.loadConst(1);
		Code.put(Code.add);
		
		// len++
		Code.put(Code.astore);
		
		// EXIT, PC = 70
		
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Obj addAllMethod = Tab.find("addAll");
		
		addAllMethod.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(4);
		Code.put(4);
		
		paramethers = addAllMethod.getLocalSymbols();
		iterator = paramethers.iterator();
		
		setObj = iterator.next();
		Obj arrayObj = iterator.next();
		Obj i = iterator.next();
		Obj j = iterator.next();
		
		// METHOD BEGIN, PC = 75
		
		// i = 0
		Code.loadConst(0);
		Code.store(i);
		
		// j = 0
		Code.loadConst(0);
		Code.store(j);
		
		// arr[j], 0
		Code.load(arrayObj);
		Code.load(j);
		Code.put(Code.aload);
		Code.loadConst(0);
		
		// IF(arr[j] != 0) ... ELSE GOTO LABEL
		Code.putFalseJump(Code.ne, 0);
		
		// EXIT
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Obj unionMethod = Tab.find("union");
		
		unionMethod.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(6);
		Code.put(6);
		paramethers = unionMethod.getLocalSymbols();
		
		iterator = paramethers.iterator();
		
		Obj set1 = iterator.next();
		Obj set2 = iterator.next();
		Obj set3 = iterator.next();
		i = iterator.next();
		j = iterator.next();
		Obj t = iterator.next();
		
		// set1 = set2 union set3;
		
		// i = 0
		Code.loadConst(0);
		Code.store(i);
		
		// j = 0
		Code.loadConst(0);
		Code.store(j);
		
		// I_LOOP, PC = 96
		
		// load length(set2)
		Code.load(set2);
		Code.load(set2);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.put(Code.aload);
		
		// load i
		Code.load(i);
		
		// IF(i != length(set2)) ... ELSE GOTO S3, PC = 103
		Code.putFalseJump(Code.ne, Code.pc + 30);
		
		// set1[i] = set2[i]
		Code.load(set1);
		Code.load(i);
		Code.load(set2);
		Code.load(i);
		Code.put(Code.aload);
		Code.put(Code.astore);
		
		// set1[arraylength-1]++
		
		// load adr
		Code.load(set1);
		
		// load index
		Code.load(set1);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		
		// load value
		Code.load(set1);
		Code.load(set1);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.put(Code.aload);
		Code.loadConst(1);
		Code.put(Code.add);
		
		Code.put(Code.astore);
		
		// i++
		Code.load(i);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(i);
		
		// GOTO I_LOOP, PC = 130
		Code.putJump(Code.pc - 34);
		
		// S3, PC = 133
		
		// load length(set3)
		Code.load(set3);
		Code.load(set3);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.put(Code.aload);
		
		// load j
		Code.load(j);
		
		// IF(j != length(set3)) ... ELSE EXIT, PC = 141
		Code.putFalseJump(Code.ne, Code.pc + 71);
		
		// t = 0
		Code.loadConst(0);
		Code.store(t);
		
		// T_LOOP, PC = 147
		
		Code.load(set1);
		Code.load(set1);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.put(Code.aload);
		
		Code.load(t);
		
		// IF(t != length(set1)) ... ELSE STORE, PC = 155
		Code.putFalseJump(Code.ne, Code.pc + 23);
		
		Code.load(set1);
		Code.load(t);
		Code.put(Code.aload);
		
		Code.load(set3);
		Code.load(j);
		Code.put(Code.aload);
		
		// IF(set1[t] != set3[j]) ... ELSE J++, PC = 166
		Code.putFalseJump(Code.ne, Code.pc + 37);
		
		Code.load(t);
		Code.loadConst(1);
		Code.put(Code.add);
		
		Code.store(t);
		
		// GOTO T_LOOP, PC = 175
		Code.putJump(Code.pc - 28);
		
		// STORE, PC = 178
		
		// set1[i] = set3[j]
		Code.load(set1);
		Code.load(i);
		Code.load(set3);
		Code.load(j);
		Code.put(Code.aload);
		Code.put(Code.astore);
		
		// set1[arraylength-1]++
		
		// load adr
		Code.load(set1);
		
		// load index
		Code.load(set1);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		
		// load value
		Code.load(set1);
		Code.load(set1);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.put(Code.aload);
		Code.loadConst(1);
		Code.put(Code.add);
		
		Code.put(Code.astore);
		
		// i++
		Code.load(i);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(i);
		
		// J_INC, PC = 203
		
		// j++
		Code.load(j);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(j);
		
		// GOTO S3, PC = 209
		Code.putJump(Code.pc - 76);
		
		// EXIT, PC = 212
		
		Code.put(Code.exit);
		Code.put(Code.return_);
		
	}
	
	public CodeGenerator() {
		initializePredeclaredMethods();
	}
	
	public int getMainPc() {
		return mainPc;
	}
	
	public void visit(Print printStmt) {
		Obj printObj = printStmt.getExpr2().obj;
		
		if(printObj == null) {
			return;
		}
		
		if(printObj.getType().getKind() == Struct.Array && printObj.getType().getElemType().equals(Tab.intType)) {
			// set, i
			Code.loadConst(0);
			
			// WHILE BEGIN, PC = 157
			
			// set, i, set, i, set
			Code.put(Code.dup2);
			Code.load(printObj);
			
			// ..., i, set, arraylength - 1
			Code.load(printObj);
			Code.put(Code.arraylength);
			Code.loadConst(1);
			Code.put(Code.sub);
			
			// set, i, set, i, len
			Code.put(Code.aload);
			
			// IF(i != len) ... ELSE EXIT, PC = 168
			Code.putFalseJump(Code.ne, Code.pc + 13);
			
			// set, i, set -> set, i
			Code.put(Code.pop);
			
			// set, i, set, i
			Code.put(Code.dup2);
			
			// set, i, val, width
			Code.put(Code.aload);
			Code.loadConst(2);
			
			// set, i
			Code.put(Code.print);
			
			// i++
			Code.loadConst(1);
			Code.put(Code.add);
			
			// GOTO WHILE BEGIN, PC = 178
			Code.putJump(Code.pc - 21);
			
			// EXIT, PC = 181
			Code.put(Code.pop);
			Code.put(Code.pop);
			Code.put(Code.pop);
			
			return;
		}
		
		// Width
		Code.loadConst(0);
		
		if(printObj.getType().equals(Tab.intType)) {
			Code.put(Code.print);
		}
		else if(printObj.getType().equals(Tab.charType)) {
			Code.put(Code.bprint);
		}
	}
	
	public void visit(PrintWithComma printWithComma) {
		Obj printObj = printWithComma.getExpr2().obj;
		
		if(printObj == null) {
			return;
		}
		
		if(printObj.getType().getKind() == Struct.Array && printObj.getType().getElemType().equals(Tab.intType)) {
			// set, i
			Code.loadConst(0);
			
			// WHILE BEGIN
			
			// set, i, set, i, set
			Code.put(Code.dup2);
			Code.load(printObj);
			
			// ..., i, set, arraylength - 1
			Code.load(printObj);
			Code.put(Code.arraylength);
			Code.loadConst(1);
			Code.put(Code.sub);
			
			// set, i, set, i, len
			Code.put(Code.aload);
			
			// IF(i != len) ... ELSE EXIT
			Code.putFalseJump(Code.ne, Code.pc + 13);
			
			// set, i, set -> set, i
			Code.put(Code.pop);
			
			// set, i, set, i
			Code.put(Code.dup2);
			
			// set, i, val, width
			Code.put(Code.aload);
			Code.loadConst(2);
			
			// set, i
			Code.put(Code.print);
			
			// i++
			Code.loadConst(1);
			Code.put(Code.add);
			
			// GOTO WHILE BEGIN
			Code.putJump(Code.pc - 21);
			
			// EXIT
			Code.put(Code.pop);
			Code.put(Code.pop);
			Code.put(Code.pop);
			
			return;
		}
		
		// Width
		Code.loadConst(printWithComma.getN2());
		
		if(printObj.getType().equals(Tab.intType)) {
			Code.put(Code.print);
		}
		else if(printObj.getType().equals(Tab.charType)) {
			Code.put(Code.bprint);
		}
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
		
		// enter
		Code.put(Code.enter);
		
		// b1
		Code.put(paramCnt.getCount());
		
		// b2
		Code.put(varCnt.getCount() + paramCnt.getCount());
	}
	
	public void visit(MethodDeclaration methodDeclaration) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(DesignatorAssign designatorAssign) {
		Code.store(designatorAssign.getDesignator().obj);
	}
	
	public void visit(FunctionCall functionCall) {
		Obj functionObj = functionCall.getDesignator().obj;
		
		if(functionObj.getName().equals("add") || functionObj.getName().equals("addAll")) {
			// Passing i as third argument for add/addAll method
			Code.load(new Obj(Obj.Var, "i", Tab.intType, 0, 0));
			
			// Passing j as fourth argument for add/addAll method
			Code.load(new Obj(Obj.Var, "j", Tab.intType, 1, 0));
		}
		
		int offset = functionObj.getAdr() - Code.pc;
		
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	public void visit(DesignatorInc designatorInc) {
		Obj designatorObj = designatorInc.getDesignator().obj;
		
		if(designatorObj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		
		Code.load(designatorObj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorObj);
	}
	
	public void visit(DesignatorDec designatorDec) {
		Obj designatorObj = designatorDec.getDesignator().obj;
		
		if(designatorObj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		
		Code.load(designatorObj);
		Code.loadConst(1);
		Code.put(Code.sub);
		
		Code.store(designatorObj);
	}
	
	public void visit(DesignatorUnion designatorUnion) {
		Code.load(designatorUnion.getDesignator().obj);
		Code.load(designatorUnion.getDesignator1().obj);
		Code.load(designatorUnion.getDesignator2().obj);
		Code.load(new Obj(Obj.Var, "i", Tab.intType, 0, 0));
		Code.load(new Obj(Obj.Var, "j", Tab.intType, 0, 0));
		Code.load(new Obj(Obj.Var, "t", Tab.intType, 0, 0));
		
		Obj functionObj = Tab.find("union");
		
		int offset = functionObj.getAdr() - Code.pc;
		
		Code.put(Code.call);
		Code.put2(offset);
	}
	
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
	
	public void visit(MethodCallFactor methodCallFactor) {
		Obj functionObj = methodCallFactor.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		
		Code.put(Code.call);
		Code.put2(offset);
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
	
	public void visit(NewSetFactor newSetFactor) {
		Code.loadConst(1);
		Code.put(Code.add);
		
		Code.put(Code.newarray);
		Code.put(1);
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
	
	public void visit(ReturnWithExpr returnWithExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(Return returnWithoutExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(Read read) {
		if(read.getDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		}
		else if(read.getDesignator().obj.getType().equals(Tab.intType)) {
			Code.put(Code.read);
		}
		
		Code.store(read.getDesignator().obj);
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
	

	
	public void visit(NegativeOperator negativeOperator) {
		negativeExpr = true;
	}
	
	public void visit(IdentMethodArgument ident) {
		Code.load(ident.obj);
	}
	
	public void visit(IdentVectorMethodArgument ident) {
		Code.load(ident.obj);
	}
	
	public void visit(LiteralMethodArgument literal) {
		Code.load(literal.getLiteral().obj);
	}
	
}
