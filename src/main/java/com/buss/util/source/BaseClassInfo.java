package com.buss.util.source;

import java.util.List;
import java.util.Map;

import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

public interface BaseClassInfo {
	public List<JCVariableDecl> getFieldList();

	public List<JCMethodDecl> getMethodList();

	public List<JCBlock> getBlockList();

	public Map<String, String> getImportMap();

	public Map<String, String> getImportStaticMap();

	public String getPackageName();
}
