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

	@Override
	public void visit(ProgramName programName) {
		programName.obj = Tab.insert(Obj.Prog, programName.getIdent(), Tab.noType);
		Tab.openScope();
		report_info("Otvoren opseg za program " + programName.getIdent(), programName);
	}
	
	@Override
	public void visit(MethodSignature2 methodSignature) {
		String ident = methodSignature.getName().getIdent();
		
		Obj methodObj = findInCurrentScope(ident);
		// Pretražuju se PROGRAM i UNIVERSE opsezi.
		
		if(methodObj == Tab.noObj) {
			methodSignature.obj = Tab.insert(Obj.Meth, ident, currentMethodReturnType);
			currentMethod = methodSignature.obj;
			Tab.openScope();
			report_info("Obradjuje se funkcija " + ident, methodSignature);
		}
		else {
			report_info("Semanticka greska: Dvostruka definicija funkcije " + ident, methodSignature);
			errorDetected = true;
		}
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
			report_info("Semanticka greska: return naredba mora sadržati povratnu vrednost", ret);
			errorDetected = true;
		}
	}
	
	@Override
	public void visit(ReturnWithExpr ret) {
		if(currentMethodReturnType == Tab.noType) {
			report_info("Semanticka greska: void metoda ne sme imati povratnu vrednost u return naredbi", ret);
			errorDetected = true;
			return;
		}
		
		Struct retType = ret.getExpr2().obj.getType();
		
		if(retType != currentMethodReturnType) {
			report_info("Semanticka greska: neslaganje povratnog tipa funkcije sa predeklarisanim", ret);
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
				report_info("Semantička greška: Tip konstante nije u skladu sa dodeljenom vrednošću. " + ident, constant);
				errorDetected  = true;
			}
			else {
				constantObj = Tab.insert(0, ident, currentType);
				constantObj.setAdr(constant.getLiteral().obj.getAdr());
			}
		}
		else {
			report_info("Semantička greška: Detektovana dvostruka definicija konstante " + ident, constant);
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
			report_info("Semantička greška: Detektovana dvostruka deklaracija promenljive " + ident, singleVarDecl);
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
			report_info("Semantička greška: Detektovana dvostruka deklaracija promenljive " + ident, singleVarDeclVector);
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
			report_info("Semantička greška: Detektovana dvostruka deklaracija promenljive " + ident, multipleVarDecl);
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
			report_info("Semantička greška: Detektovana dvostruka deklaracija promenljive " + ident, multipleVarDeclVector);
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

