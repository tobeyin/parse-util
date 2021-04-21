package com.buss.util.source;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.util.TreeScanner;

public class MethodScanner extends TreeScanner<Void, Void> {

	@Override
	public Void visitMethodInvocation(MethodInvocationTree arg0, Void arg1) {
		return super.visitMethodInvocation(arg0, arg1);
	}
	
}
