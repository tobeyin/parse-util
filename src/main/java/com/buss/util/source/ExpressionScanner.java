package com.buss.util.source;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.util.TreeScanner;

public class ExpressionScanner extends TreeScanner<Void, Void> {

	@Override
	public Void visitExpressionStatement(ExpressionStatementTree arg0, Void arg1) {
		return super.visitExpressionStatement(arg0, arg1);
	}
	
	@Override
	public Void visitCompilationUnit(CompilationUnitTree arg0, Void arg1) {
		return super.visitCompilationUnit(arg0, arg1);
	}
}
