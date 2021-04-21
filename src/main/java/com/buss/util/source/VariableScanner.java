package com.buss.util.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

/**
 * 变量解析
 * 
 * @author Tony
 *
 */
public class VariableScanner extends TreeScanner<Void, Void> implements BaseClassInfo {

	/**
	 * 变量列表
	 */
	private final List<JCVariableDecl> variableList = new ArrayList<JCVariableDecl>();
	/**
	 * 所有代码块列表
	 */
	private final List<JCBlock> blockList = new ArrayList<JCBlock>();
	/**
	 * 代码块列表
	 */
	private final List<JCBlock> blockListNew = new ArrayList<JCBlock>();
	/**
	 * 方法列表
	 */
	private final List<JCMethodDecl> methodList = new ArrayList<JCMethodDecl>();

	/**
	 * 成员变量列表
	 */
	private final List<JCVariableDecl> fieldList = new ArrayList<JCVariableDecl>();

	/**
	 * 包名
	 */
	private final String packageName;

	/**
	 * 导入列表
	 */
	private final Map<String, String> importMap = new HashMap<String, String>();

	/**
	 * 静态导入
	 */
	private final Map<String, String> importStaticMap = new HashMap<String, String>();
	/**
	 * 全部导入集合
	 */
	private final List<String> allList = new ArrayList<String>();

	/**
	 * 解析代码，获取代码中的成员变量
	 * @param unitTree 代码语法树
	 */
	public VariableScanner(JCCompilationUnit unitTree) {
		// 扫描方法、代码块以及属性
		this.scan(unitTree, null);
		
		for (JCBlock block : blockList) {
			if (isInnerCode(methodList, block, unitTree)) {
				continue;
			}
			blockListNew.add(block);
		}

		// 排除代码块以及方法中的属性定义
		for (JCVariableDecl var : variableList) {
			if (isInnerCode(methodList, var, unitTree)) {
				continue;
			}
			if (isInnerCode(blockList, var, unitTree)) {
				continue;
			}
			fieldList.add(var);
		}
		// 包名
		packageName = unitTree.getPackageName().toString();

		// 默认导入类
		addMap(importMap, "int");
		addMap(importMap, "byte");
		addMap(importMap, "char");
		addMap(importMap, "long");
		addMap(importMap, "short");
		addMap(importMap, "float");
		addMap(importMap, "double");
		addMap(importMap, "boolean");
		addMap(importMap, "java.lang.String");
		addMap(importMap, "java.lang.Class");

		// 自定义导入与静态导入解析
		unitTree.getImports().forEach((i) -> {
			if (!i.isStatic()) {
				if (i.toString().endsWith(".*")) {
					allList.add(i.toString().replace(".*", ""));
				} else {
					addMap(importMap, i.toString());
				}
			} else {
				addMap(importStaticMap, i.toString());
			}
		});
	}

	private void addMap(Map<String, String> paramMap, String clazz) {
		if (clazz.indexOf(".") < 0) {
			paramMap.put(clazz, clazz);
			return;
		}
		paramMap.put(clazz.substring(clazz.lastIndexOf(".") + 1), clazz);
	}

	private boolean isInnerCode(List<? extends JCTree> blockList, JCTree tree1, JCCompilationUnit unitTree) {
		for (JCTree block : blockList) {
			if (block.getStartPosition() <= tree1.getStartPosition()
					&& block.getEndPosition(unitTree.endPositions) >= tree1.getEndPosition(unitTree.endPositions)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Void visitVariable(VariableTree variableTree, Void param) {
		variableList.add((JCVariableDecl) variableTree);
		return super.visitVariable(variableTree, param);
	}

	@Override
	public Void visitBlock(BlockTree blockTree, Void param) {
		blockList.add((JCBlock) blockTree);
		return super.visitBlock(blockTree, param);
	}

	@Override
	public Void visitMethod(MethodTree methodTree, Void param) {
		methodList.add((JCMethodDecl) methodTree);
		return super.visitMethod(methodTree, param);
	}
	
	@Override
	public List<JCVariableDecl> getFieldList() {
		return fieldList;
	}

	@Override
	public List<JCMethodDecl> getMethodList() {
		return methodList;
	}
	
	@Override
	public List<JCBlock> getBlockList() {
		return blockListNew;
	}
	
	@Override
	public Map<String, String> getImportMap() {
		return importMap;
	}
	
	@Override
	public Map<String, String> getImportStaticMap() {
		return importStaticMap;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}
	
	public List<String> getAllList() {
		return allList;
	}
}
