package rs.ac.bg.etf.pp1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticPass extends VisitorAdaptor {

	boolean errorDetected = false;
	
	Struct boolType = new Struct(Struct.Bool);
	
	int printCallCount = 0;
	int VarDeclCount = 0; // Global Vars Count
	int ConstDeclCount = 0; // Global Constants Count
	Obj currentMethod = null;
	Struct currentMethodReturnType = Tab.noType;
	int currentMethodParameters = 0;
	boolean returnFound = false;
	boolean breakFound = false;
	boolean continueFound = false;
	boolean doWhile = false;
	boolean mainDeclared = false;
	private Struct currentType = Tab.noType;
	
	Stack<Obj> constants = new Stack<Obj>(); // Not used
	
	Queue<Struct> queue = new LinkedList<>(); // Used for parameters in function calls

	Logger log = Logger.getLogger(getClass()); // Not used

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	private Obj findInCurrentScope(String identName) {
        Obj result = Tab.currentScope.findSymbol(identName);
        if (result == null) {
            result = Tab.noObj;
        }
        return result;
	}
	
	private Obj findInOuterScope(String identName) {
        Obj result = Tab.currentScope.getOuter().findSymbol(identName);
        if (result == null) {
            result = Tab.noObj;
        }
        return result;
    }
	
	private Obj findInCurrentOrSomeOuterScope(String identName) {
        return Tab.find(identName);
    }

	@Override
	public void visit(ProgramName programName) {
		programName.obj = Tab.insert(Obj.Prog, programName.getIdent(), Tab.noType);
		Tab.openScope();
		report_info("Otvoren opseg za program " + programName.getIdent(), programName);
	}
	
	@Override
	public void visit(MethodSignature2 methodSignature) {
		String ident = methodSignature.getName().getIdent();
		Struct retType = currentMethodReturnType;
		
		Obj methodObj = findInCurrentScope(ident);
		// Pretražuju se PROGRAM i UNIVERSE opsezi.
		
		if(methodObj == Tab.noObj) {
			methodSignature.obj = Tab.insert(Obj.Meth, ident, currentMethodReturnType);
			currentMethod = methodSignature.obj;
			currentMethod.setLevel(0);
			Tab.openScope();
			report_info("Obradjuje se funkcija " + ident, methodSignature);
			
			if(ident.equals("main")) {
				if(currentMethodReturnType.getKind() == 0) {
					mainDeclared = true;
				}
				else {
					report_info("Neispravan povratni tip funkcije main. Očekivan je void. Semantička greška", methodSignature);
					errorDetected = true;
				}
			}
		}
		else {
			report_info("Dvostruka definicija funkcije. Semantička greška" + ident, methodSignature);
			errorDetected = true;
		}
	}
	
	@Override
	public void visit(MultipleMethodParameters multipleMethodParameters) {
		currentMethodParameters++;
	}
	
	@Override
	public void visit(SingleMethodParameter singleMethodParameter) {
		currentMethodParameters++;
	}
	
	@Override
	public void visit(MethodSignature methodSignature) {
		methodSignature.getMethodSignature2().obj.setLevel(currentMethodParameters);
		currentMethodParameters = 0;
	}
	
	@Override
	public void visit(MethodCallFactor methodCallFactor) {
		Obj designatorObj = methodCallFactor.getDesignator().obj;
		
		if(designatorObj == null) {
			// DON'T CONTINUE
			return;
		}
		
		int argumentsNeeded = designatorObj.getLevel();
		
		if(argumentsNeeded != currentMethodParameters) {
			// ERROR
			report_info("Neodgovarajući broj parametara u pozivu funkcije. Semantička greška", methodCallFactor);
			errorDetected = true;
		}
		
		if(queue.isEmpty()) {
			// DON'T CONTINUE
			return;
		}
		
		Collection<Obj> locals = designatorObj.getLocalSymbols();
		
		if(!locals.isEmpty()) {
			int size = currentMethodParameters;
			
			Iterator<Obj> iterator = locals.iterator();
			
			while(size>0 && iterator.hasNext() && !queue.isEmpty()) {
				Obj local = iterator.next();
				
				if(local.getType().equals(queue.peek()) ||
						(local.getType().getKind() == Struct.Array && queue.peek().equals(local.getType().getElemType())) ||
						(queue.peek().getKind() == Struct.Array && local.getType().equals(queue.peek().getElemType()))) {
					// OK
					queue.remove();
				}
				else {
					// ERROR
					report_info("Neodgovarajući tip parametra u pozivu funkcije " + designatorObj.getName() + ". Semantička greška", methodCallFactor);
					errorDetected = true;
					break;
				}
				size--;
			}
		}
		
		queue.clear();
		currentMethodParameters = 0;
	}
	
	@Override
	public void visit(SingleMethodArgument singleMethodArgument) {
		currentMethodParameters++;
	}
	
	@Override
	public void visit(MultipleMethodArguments multipleMethodArguments) {
		currentMethodParameters++;
	}
	
	@Override
	public void visit(DoStatement doStatement) {
		doWhile = true;
	}
	
	@Override
	public void visit(WhileStatement whileStatement) {
		doWhile = false;
	}
	
	@Override
	public void visit(Continue cnt) {
		if(!doWhile) continueFound = true;
	}
	
	@Override
	public void visit(Break brk) {
		if(!doWhile) breakFound = true;
	}
	
	
	@Override
	public void visit(ScalarMethodParameter scalarMethodParameter) {
		String ident = scalarMethodParameter.getIdent();
		Tab.insert(1, ident, currentType);
	}
	
	@Override
	public void visit(VectorMethodParameter vectorMethodParameter) {
		String ident = vectorMethodParameter.getIdent();
		Tab.insert(1, ident, new Struct(Struct.Array, currentType));
	}
	
	@Override
	public void visit(DesignatorAssign designatorAssign) {
		Obj designatorObj = designatorAssign.getDesignator().obj;
		Obj expressionObj = designatorAssign.getExpr2().obj;
		
		if(designatorObj == null || expressionObj == null) {
			return;
		}
		
		Struct designatorType = designatorObj.getType();
		Struct expressionType = expressionObj.getType();
		
		if(designatorType.equals(expressionType)) {
			if(designatorObj.getKind() != Obj.Var) {
				// Error
				report_info("Na levoj strani dodele mora biti promenljiva ili niz. Semantička greška", designatorAssign);
				errorDetected = true;
			}
			else {
				// Ok
			}
		}
		else {
			if(designatorType.getKind() == Struct.Array && expressionType.equals(designatorType.getElemType())) {
				// Ok
			}
			else {
				// Error
				report_info("Nepoklapanje tipova u dodeli vrednosti. Semantička greška", designatorAssign);
				errorDetected = true;
			}
		}
	}
	
	@Override
	public void visit(DesignatorSelect desSelect) {
		desSelect.obj = desSelect.getDesignator().obj;
	}
	
	@Override
	public void visit(FunctionCall functionCall) {
		Obj functionCallObj = functionCall.getDesignator().obj;
		
		if(functionCallObj == null) {
			// Error
			return;
		}
		
		Obj designatorObj = findInCurrentOrSomeOuterScope(functionCall.getDesignator().obj.getName());
		
		if(designatorObj == Tab.noObj) {
			// ERROR
			report_info("Nedeklarisana funkcija. Semantička greška", functionCall);
			errorDetected = true;
		}
		else {
			if(designatorObj.getKind() == 3) {
				if(designatorObj.getLevel() == currentMethodParameters) {
					// OK
					if(queue.isEmpty()) {
						// DON'T CONTINUE
						return;
					}
					
					Collection<Obj> locals = designatorObj.getLocalSymbols();
					
					if(!locals.isEmpty()) {
						int size = currentMethodParameters;
						
						Iterator<Obj> iterator = locals.iterator();
						
						while(size>0 && iterator.hasNext() && !queue.isEmpty()) {
							Obj local = iterator.next();
							
							if(local.getType().equals(queue.peek())){
								// OK
								queue.remove();
							}
							else {
								if((local.getType().getKind() == Struct.Array && queue.peek().equals(local.getType().getElemType())) ||
										(queue.peek().getKind() == Struct.Array && local.getType().equals(queue.peek().getElemType()))) {
									// OK
									queue.remove();
								}
								else {
									// ERROR
									report_info("Neodgovarajući tip parametra u pozivu funkcije " + designatorObj.getName() + ". Semantička greška", functionCall);
									errorDetected = true;
									break;
								}
							}
							size--;
						}
					}
					queue.clear();
					currentMethodParameters = 0;
				}
				else {
					// ERROR
					report_info("Neodgovarajući broj parametara u pozivu funkcije. Semantička greška", functionCall);
					errorDetected = true;
				}
			}
			else {
				// ERROR
				report_info("Nedeklarisana funkcija. Semantička greška", functionCall);
				errorDetected = true;
			}
		}
	}
	
	@Override
	public void visit(DesignatorInc designatorInc) {
		Obj designatorObj = designatorInc.getDesignator().obj;
		
		if(designatorObj == null) {
			// Error
		}
		else {
			Struct designatorType = designatorObj.getType();
			if(designatorType.getKind() == 1) {
				// OK
			}
			else {
				// Error
				report_info("Nepoklapanje tipova. Semantička greška", designatorInc);
				errorDetected = true;
			}
		}
	}
	
	@Override
	public void visit(DesignatorDec designatorDec) {
		Obj designatorObj = designatorDec.getDesignator().obj;
		
		if(designatorObj == null) {
			// Error
		}
		else {
			Struct designatorType = designatorObj.getType();
			if(designatorType.getKind() == 1) {
				// OK
			}
			else {
				// Error
				report_info("Nepoklapanje tipova. Semantička greška", designatorDec);
				errorDetected = true;
			}
		}
	}
	
	@Override
	public void visit(DesignatorIdent designatorIdent) {
		String ident = designatorIdent.getIdent();
		
		Obj designatorObj = findInCurrentScope(ident);
		
		if(designatorObj == Tab.noObj) {
			designatorObj = findInOuterScope(ident);
			if(designatorObj == Tab.noObj) {
				// Not Found
				report_info("Promenljiva " + ident + " mora biti deklarisana pre upotrebe. Semantička greška", designatorIdent);
				errorDetected = true;
			}
			else {
				// Found in global scope
				designatorIdent.obj = designatorObj;
			}
		}
		else {
			// Found in local scope
			designatorIdent.obj = designatorObj;
		}
	}
	
	@Override
	public void visit(DesignatorFactor desfact) {
		Obj desfactObj = desfact.getDesignator().obj;
		
		if(desfactObj == null) {
			// ERROR
			return;
		}
		
		desfact.obj = desfactObj;
	}
	
	@Override
	public void visit(VectorFactor vectorFactor) {
		Obj desFactVect = vectorFactor.getDesignator().obj;
		
		vectorFactor.obj = desFactVect;
	}
	
	@Override
	public void visit(NonVoidReturnType ret) {
		String retType = ret.getType().getTypeName();
		if(retType.equals("int")) {
			currentMethodReturnType = Tab.intType;
		}
		else if(retType.equals("char")) {
			currentMethodReturnType = Tab.charType;
		}
		else if(retType.equals("bool")) {
			currentMethodReturnType = boolType;
		}
		else {
			report_info("Semanticka greska: Nepostojeci tip " + retType, ret);
			errorDetected = true;
		}
	}
	
	@Override
	public void visit(VoidReturnType ret) {
		currentMethodReturnType = Tab.noType;
	}
	
	@Override
	public void visit(Print printStmt) {
		printCallCount++;
	}
	
	@Override
	public void visit(PrintWithComma printStmt) {
		printCallCount++;
	}
	
	@Override
	public void visit(Return ret) {
		if(currentMethodReturnType != Tab.noType) {
			report_info("Return naredba mora sadržati povratnu vrednost. Semantička greška", ret);
			errorDetected = true;
		}
	}
	
	@Override
	public void visit(ReturnWithExpr ret) {
		if(currentMethodReturnType == Tab.noType) {
			report_info("Void metoda ne sme imati povratnu vrednost u return naredbi. Semantička greška", ret);
			errorDetected = true;
			return;
		}
		
		if(ret.getExpr2().obj==null){
			return;
		}	
		
		Struct retType = ret.getExpr2().obj.getType();
		
		if(retType != currentMethodReturnType) {
			report_info("Neslaganje povratnog tipa funkcije sa predeklarisanim. Semantička greška", ret);
			errorDetected = true;
		}
		else {
			returnFound = true;
		}
	}
	
	@Override
	public void visit(DesignatorVector vector) {
		Obj vectorObj = vector.getExpr2().obj;
		
		if(vectorObj == null) return;
		
		if(vectorObj.getType().equals(Tab.intType)) {
			// OK
		}
		else {
			report_info("Indeks mora biti tipa 'int'! Semantička greška", vector);
			errorDetected = true;
		}
	}
	
	@Override
	public void visit(Const constant) {
		String ident = constant.getName().getIdent();
		//report_info("Konstanta " + ident + " sa vrednoscu " + constant.getLiteral().getValue(), constant);
		
		Obj constantObj = findInCurrentScope(ident);
		// Pretražuju se PROGRAM i UNIVERSE opsezi.
		
		if(constantObj == Tab.noObj) {
			Struct constantType = constant.getLiteral().obj.getType();
			if(constantType != currentType) {
				report_info("Tip konstante nije u skladu sa dodeljenom vrednošću. Semantička greška", constant);
				errorDetected  = true;
			}
			else {
				constantObj = Tab.insert(0, ident, currentType);
				constantObj.setAdr(constant.getLiteral().obj.getAdr());
			}
		}
		else {
			report_info("Detektovana dvostruka definicija konstante. Semantička greška" + ident, constant);
			errorDetected  = true;
		}
	}
	
	@Override
	public void visit(Type type) {
		String typeName = type.getTypeName();
		
		if(typeName.equals("int")) {
			currentType = Tab.intType;
		}
		else if(typeName.equals("char")) {
			currentType = Tab.charType;
		}
		else if(typeName.equals("bool")) {
			currentType = boolType;
		}
		else {
			report_info("Nedefinisan tip " + typeName + ". Semantička greška", type);
			errorDetected = true;
		}
	}
	
	@Override
	public void visit(SingleVarDecl singleVarDecl) {
		String ident = singleVarDecl.getName().getIdent();
		
		Obj varObj = findInCurrentScope(ident);
		// Pretražuju se PROGRAM i UNIVERSE opsezi.
		
		if(varObj == Tab.noObj) {
			varObj = Tab.insert(1, ident, currentType);
		}
		else {
			report_info("Detektovana dvostruka deklaracija promenljive " + ident + " .Semantička greška", singleVarDecl);
			errorDetected  = true;
		}
	}
	
	@Override
	public void visit(SingleVarDeclVector singleVarDeclVector) {
		String ident = singleVarDeclVector.getName().getIdent();
		
		Obj varObj = findInCurrentScope(ident);
		// Pretražuju se PROGRAM i UNIVERSE opsezi.
		
		if(varObj == Tab.noObj) {
			varObj = Tab.insert(1, ident, new Struct(Struct.Array, currentType));
		}
		else {
			report_info("Detektovana dvostruka deklaracija promenljive " + ident + " .Semantička greška", singleVarDeclVector);
			errorDetected  = true;
		}
	}
	
	@Override
	public void visit(MultipleVarDecl multipleVarDecl) {
		String ident = multipleVarDecl.getName().getIdent();
		
		Obj varObj = findInCurrentScope(ident);
		// Pretražuju se PROGRAM i UNIVERSE opsezi.
		
		if(varObj == Tab.noObj) {
			varObj = Tab.insert(1, ident, currentType);
		}
		else {
			report_info("Detektovana dvostruka deklaracija promenljive " + ident + " .Semantička greška", multipleVarDecl);
			errorDetected  = true;
		}
	}
	
	@Override
	public void visit(MultipleVarDeclVector multipleVarDeclVector) {
		String ident = multipleVarDeclVector.getName().getIdent();
		
		Obj varObj = findInCurrentScope(ident);
		// Pretražuju se PROGRAM i UNIVERSE opsezi.
		
		if(varObj == Tab.noObj) {
			varObj = Tab.insert(1, ident, new Struct(Struct.Array, currentType));
		}
		else {
			report_info("Detektovana dvostruka deklaracija promenljive " + ident + " .Semantička greška", multipleVarDeclVector);
			errorDetected  = true;
		}
	}
	
	@Override
	public void visit(RelopCondFactor relopCondFactor) {
		Obj expr1 = relopCondFactor.getExpr2().obj;
		Obj expr2 = relopCondFactor.getExpr21().obj;
		
		if(expr1 == null || expr2 == null) {
			return;
		}
		
		if(!expr1.getType().equals(expr2.getType())) {
			if(expr1.getType().getKind() == Struct.Array) {
				if(expr1.getType().getElemType().equals(expr2.getType())) {
					// OK
				}
				else {
					// ERROR
					report_info("Nepoklapanje tipova. Semantička greška", relopCondFactor);
					errorDetected  = true;
				}
			}
			else if(expr2.getType().getKind() == Struct.Array) {
				if(expr2.getType().getElemType().equals(expr1.getType())) {
					// OK
				}
				else {
					// ERROR
					report_info("Nepoklapanje tipova. Semantička greška", relopCondFactor);
					errorDetected  = true;
				}
			}
			else {
				// ERROR
				report_info("Nepoklapanje tipova. Semantička greška", relopCondFactor);
				errorDetected  = true;
			}
		}
		else {
			Struct expr1Type = expr1.getType();
			Struct expr2Type = expr2.getType();
			if(expr1Type.getKind() == Struct.Array && expr2Type.getKind() == Struct.Array) {
				Relop relop = relopCondFactor.getRelop();
				if(relop instanceof EqRelop || relop instanceof NeqRelop) {
					// OK
				}
				else {
					// ERROR
					report_info("Neodgovarajući relacioni operator. Semantička greška", relopCondFactor);
					errorDetected  = true;
				}
			}
			else {
				// OK
			}
		}
	}
	
	@Override
	public void visit(NegativeTermExprSum negTerm) {
		Obj negObj = negTerm.getTerm().obj;
		
		if(negObj == null) {
			return;
		}
		
		negTerm.obj = negObj;
		
		if(negObj.getType().equals(Tab.intType)) {
			// OK
		}
		else {
			report_info("Izraz neposredno nakon '-' mora biti tipa int. Semantička greška", negTerm);
			errorDetected  = true;
		}
	}
	
	@Override
	public void visit(TermExprSum termExprSum) {
		Obj termObj = termExprSum.getTerm().obj;
		
		termExprSum.obj = termObj;
	}
	
	@Override
	public void visit(NegativeTermExpr negTerm){
		Obj negObj = negTerm.getTerm().obj;
		
		if(negObj == null) {
			return;
		}
		
		negTerm.obj = negObj;
		
		if(negObj.getType().equals(Tab.intType)) {
			// OK
		}
		else {
			report_info("Izraz neposredno nakon '-' mora biti tipa int. Semantička greška", negTerm);
			errorDetected  = true;
		}
	}
	
	@Override
	public void visit(AddopTerm addopTerm) {
		Obj addopTermObj = addopTerm.getTerm().obj;
		
		if(addopTermObj == null) return;
		
		if((addopTermObj.getKind() == Obj.Con && addopTermObj.getType().equals(Tab.intType)) ||
				(addopTermObj.getKind() == Obj.Var && addopTermObj.getType().equals(Tab.intType)) ||
				(addopTermObj.getType().getKind() == Struct.Array && addopTermObj.getType().getElemType().equals(Tab.intType))) {
			// OK
			// addopTerm.obj = addopTermObj;
		}
		else {
			report_info("Nepoklapanje tipova u izrazu. Semantička greška", addopTerm);
			errorDetected  = true;
		}
	}
	
	@Override
	public void visit(MulopTerm mulopTerm) {
		Obj factor = mulopTerm.getFactor().obj;
		
		if(factor == null) {
			// ERROR
			return;
		}
		
		if((factor.getKind() == Obj.Con && factor.getType().equals(Tab.intType)) ||
				(factor.getKind() == Obj.Var && factor.getType().equals(Tab.intType)) ||
				(factor.getType().getKind() == Struct.Array && factor.getType().getElemType().equals(Tab.intType))) {
			// OK
			mulopTerm.obj = factor;
		}
		else {
			report_info("Nepoklapanje tipova u izrazu. Semantička greška", mulopTerm);
			errorDetected  = true;
		}
	}
	
    @Override
    public void visit(IntLiteral intLiteral) {
        intLiteral.obj = new Obj(Obj.Con, "", Tab.intType, intLiteral.getValue(), 0);
    }

    @Override
    public void visit(CharLiteral charLiteral) {
        charLiteral.obj = new Obj(Obj.Con, "", Tab.charType, charLiteral.getValue(), 0);
    }
    
    @Override
    public void visit(BoolLiteral boolLiteral) {
        boolLiteral.obj = new Obj(Obj.Con, "", boolType);
    }
    
    @Override
    public void visit(TermExpr termExpr) {
        termExpr.obj = termExpr.getTerm().obj;
    }
    
    @Override
    public void visit(FactorTerm factorTerm) {
        factorTerm.obj = factorTerm.getFactor().obj;
    }
    
    @Override
    public void visit(NumberFactor numberFactor) {
    	numberFactor.obj = new Obj(Obj.Con, "", Tab.intType, numberFactor.getValue(), 1);
    }

    @Override
    public void visit(CharFactor charFactor) {
        charFactor.obj = new Obj(Obj.Con, "", Tab.charType, charFactor.getValue(), 1);
    }
    
    
    @Override
    public void visit(BoolFactor boolFactor) {
        boolFactor.obj = new Obj(Obj.Con, "", boolType, boolFactor.getValue() ? 1 : 0, 1);
    }
    
    @Override
    public void visit(IdentMethodArgument identMethodArgument) {
        String ident = identMethodArgument.getIdent();
        
        Obj identObj = findInCurrentOrSomeOuterScope(ident);
        
        if(identObj.equals(Tab.noObj)) {
        	// ERROR: Method argument not found
        	report_info("Argument funkcije nije definisan. Semantička greška", identMethodArgument);
			errorDetected  = true;
        }
        else {
        	// OK
        	queue.add(identObj.getType());
        }
    }
    
    
    @Override
    public void visit(IdentVectorMethodArgument identMethodArgument) {
        String ident = identMethodArgument.getIdent();
        
        Obj identObj = findInCurrentOrSomeOuterScope(ident);
        
        if(identObj.equals(Tab.noObj)) {
        	// ERROR: Method argument not found
        	report_info("Argument funkcije nije definisan. Semantička greška", identMethodArgument);
			errorDetected  = true;
        }
        else {
        	// OK
        	queue.add(identObj.getType());
        }
    }
    
    @Override
    public void visit(LiteralMethodArgument literalMethodArgument) {
    	Obj literalObj = literalMethodArgument.getLiteral().obj;
    	Struct type = literalObj.getType();
    	queue.add(type);
    }
    

	public void visit(Program program) {
		if(!mainDeclared) {
			report_info("Semantička greška: Funkcija main nije definisana", program);
			errorDetected  = true;
		}
		
		if(breakFound) {
			report_info("Neispravna upotreba 'break' iskaza. Semantička greška", null);
			errorDetected  = true;
		}
		
		if(continueFound) {
			report_info("Neispravna upotreba 'continue' iskaza. Semantička greška", null);
			errorDetected  = true;
		}
		
		Tab.chainLocalSymbols(program.getProgramName().obj);
		Tab.closeScope();
	}
	
	public void visit(MethodDeclaration methodDeclaration) {
		String ident = methodDeclaration.getMethodSignature().getMethodSignature2().getName().getIdent();
		if(currentMethodReturnType != Tab.noType && !returnFound) {
			report_info("Nepostojeca return naredba u funkciji " + ident + ". Semantička greška", methodDeclaration);
			errorDetected  = true;
		}
		
		Tab.chainLocalSymbols(methodDeclaration.getMethodSignature().getMethodSignature2().obj);
		Tab.closeScope();
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
}

