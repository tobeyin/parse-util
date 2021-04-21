package com.buss.util;

import java.io.File;

import com.buss.util.source.BaseClassInfo;
import com.buss.util.source.VariableScanner;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.parser.ParserFactory;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.Context;

public class SourceUtil {

	private static final ParserFactory factory = getParserFactory();

	private static ParserFactory getParserFactory() {
		Context context = new Context();
		JavacFileManager.preRegister(context);
		return ParserFactory.instance(context);
	}

	public static JCCompilationUnit parseFile(String file) {
		return parseFile(new File(file));
	}

	public static JCCompilationUnit parseFile(File file) {
		return parse(FileUtil.readFile(file));
	}

	public static JCCompilationUnit parse(CharSequence source) {
		return factory.newParser(source, true, true, true).parseCompilationUnit();
	}

	public static BaseClassInfo parseUnit(JCCompilationUnit unit) {
		return new VariableScanner(unit);
	}
	
}
