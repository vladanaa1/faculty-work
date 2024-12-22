package rs.ac.bg.etf.pp1;
import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticPass extends VisitorAdaptor {

	boolean errorDetected = false;
	int printCallCount = 0;
	int VarDeclCount = 0; // globalne prom.
	int ConstDeclCount = 0; // globalne konst.
	Obj currentMethod = null;
	Struct currentMethodReturnType = Tab.noType;
	boolean returnFound = false;
	boolean mainDeclared = false;
	int nVars;
	private Struct currentType = Tab.noType;
	
	Stack<Obj> constants = new Stack<Obj>();

	Logger log = Logger.getLogger(getClass());

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
	public void visit(ScalarMethodParameter scalarMethodParameter) {
		String ident = scalarMethodParameter.getIdent();
		Tab.insert(1, ident, currentType);
	}
	
	@Override
	public void visit(VectorMethodParameter vectorMethodParameter) {
		String ident = vectorMethodParameter.getIdent();
		Tab.insert(1, ident, currentType);
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
			// Ok
		}
		else {
			// Error
			report_info("Nepoklapanje tipova u dodeli vrednosti. Semantička greška", designatorAssign);
			errorDetected = true;
		}
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
			// Error
			report_info("Nedeklarisana funkcija. Semantička greška", functionCall);
			errorDetected = true;
		}
		else {
			if(designatorObj.getKind() == 3) {
				// OK
			}
			else {
				// Error
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
				designatorIdent.obj = new Obj(designatorObj.getKind(), ident, designatorObj.getType());
			}
		}
		else {
			// Found in local scope
			designatorIdent.obj = new Obj(designatorObj.getKind(), ident, designatorObj.getType());
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
	public void visit(NonVoidReturnType ret) {
		String retType = ret.getType().getTypeName();
		if(retType.equals("int")) {
			currentMethodReturnType = Tab.intType;
		}
		else if(retType.equals("char")) {
			currentMethodReturnType = Tab.charType;
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
	public void visit(Return ret) {
		if(currentMethodReturnType != Tab.noType) {
			report_info("Semantička greška: return naredba mora sadržati povratnu vrednost", ret);
			errorDetected = true;
		}
	}
	
	@Override
	public void visit(ReturnWithExpr ret) {
		if(currentMethodReturnType == Tab.noType) {
			report_info("Semantička greška: void metoda ne sme imati povratnu vrednost u return naredbi", ret);
			errorDetected = true;
			return;
		}
		
		Struct retType = ret.getExpr2().obj.getType();
		
		if(retType != currentMethodReturnType) {
			report_info("Semantička greška: neslaganje povratnog tipa funkcije sa predeklarisanim", ret);
			errorDetected = true;
		}
		else {
			returnFound = true;
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
		else {
			report_info("Semantička greška: Nedefinisan tip " + typeName, type);
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
		// treba dodatno za niz!!!
		String ident = singleVarDeclVector.getName().getIdent();
		
		Obj varObj = findInCurrentScope(ident);
		// Pretražuju se PROGRAM i UNIVERSE opsezi.
		
		if(varObj == Tab.noObj) {
			varObj = Tab.insert(1, ident, currentType);
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
		// treba dodatno za niz!!!
		String ident = multipleVarDeclVector.getName().getIdent();
		
		Obj varObj = findInCurrentScope(ident);
		// Pretražuju se PROGRAM i UNIVERSE opsezi.
		
		if(varObj == Tab.noObj) {
			varObj = Tab.insert(1, ident, currentType);
		}
		else {
			report_info("Detektovana dvostruka deklaracija promenljive " + ident + " .Semantička greška", multipleVarDeclVector);
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
		
		if(factor.getKind() == Obj.Con && factor.getType().equals(Tab.intType)) {
			// OK
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
    
    /*
    @Override
    public void visit(BoolFactor boolFactor) {
        boolFactor.obj = new Obj(Obj.Con, "", MJTab.BOOL_TYPE, boolFactor.getValue() ? 1 : 0, 1);
    }
    */

    /*
    @Override
    public void visit(BoolLiteral boolLiteral) {
        boolLiteral.obj = new Obj(Obj.Con, "", Tab.boolType, boolLiteral.getValue(), 0);
    }
	*/

	public void visit(Program program) {
		if(!mainDeclared) {
			report_info("Semantička greška: Funkcija main nije definisana", program);
			errorDetected  = true;
		}
		
		Tab.chainLocalSymbols(program.getProgramName().obj);
		Tab.closeScope();
	}
	
	public void visit(MethodDeclaration methodDeclaration) {
		String ident = methodDeclaration.getMethodSignature().getMethodSignature2().getName().getIdent();
		if(currentMethodReturnType != Tab.noType && !returnFound) {
			report_info("Semantička greška: Nepostojeca return naredba u funkciji " + ident, methodDeclaration);
			errorDetected  = true;
		}
		
		Tab.chainLocalSymbols(methodDeclaration.getMethodSignature().getMethodSignature2().obj);
		Tab.closeScope();
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
}

