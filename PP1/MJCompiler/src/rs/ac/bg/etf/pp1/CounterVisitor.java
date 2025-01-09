package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;


public class CounterVisitor extends VisitorAdaptor {
	
	protected int count;
	
	public int getCount() {
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor{
		
		public void visit(ScalarMethodParameter methodParameter) {
			count++;
		}
		
		public void visit(VectorMethodParameter methodParameter) {
			count++;
		}
	}
	
	public static class VarCounter extends CounterVisitor{
		
		public void visit(SingleVarDecl varDecl) {
			count++;
		}
		
		public void visit(SingleVarDeclVector varDecl) {
			count++;
		}
		
		public void visit(MultipleVarDecl varDecl) {
			count++;
		}
		
		public void visit(MultipleVarDeclVector varDecl) {
			count++;
		}
	}
	
}
