package com.buss.util;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.buss.util.source.BaseClassInfo;
import com.buss.util.source.ExpressionScanner;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;

public class SourceUtilTest {

	private static File f = new File("source.java");;

	@BeforeClass
	public static void init() throws IOException {

		FileUtil.writeFile("package parse;\r\n" + "\r\n" + "import java.io.Serializable;\r\n"
				+ "import java.util.List;\r\n" + "import static java.lang.System.out;\r\n" + "\r\n" + "/**\r\n"
				+ " * comment1\r\n" + " * \r\n" + " * @author Tony\r\n" + " *\r\n" + " */\r\n" + "/*\r\n"
				+ " * comment2\r\n" + " */\r\n" + "\r\n"
				+ "@TestAnn(val1 = \"1\", val2 = { \"2\", \"3\", \"4\" }) // 测试单行注释\r\n"
				+ "public class TestBean<T, S> extends User implements Serializable, Runnable {\r\n" + "\r\n"
				+ "	static {\r\n" + "		int field1 = 0;\r\n" + "		// print test\r\n"
				+ "		out.println(\"EndPosTable = \" + field1);\r\n" + "	}\r\n"
				+ "	private static final long serialVersionUID = -7592621923340536380L;\r\n" + "	/**\r\n"
				+ "	 * 字段1 \"\"\"\"\"\"\r\n" + "	 */\r\n" + "	private parse.User field1 = new User() {\r\n"
				+ "	};\r\n" + "	private String field2 = \"1\\\\;2\\\" {  \\';3;\"; // 字段2\r\n"
				+ "	private String field3; // 字段3\r\n" + "	private List<parse.User> userList; // userList\r\n"
				+ "	private String field5, field4;\r\n" + "\r\n" + "	public User getField1() {\r\n"
				+ "		return field1;\r\n" + "	}\r\n" + "\r\n" + "	public void setField1(User field1) {\r\n"
				+ "		this.field1 = field1;\r\n" + "	}\r\n" + "\r\n" + "	public String getField2() {\r\n"
				+ "		return field2;\r\n" + "	}\r\n" + "\r\n" + "	public void setField2(String field2) {\r\n"
				+ "		this.field2 = field2;\r\n" + "	}\r\n" + "\r\n" + "	public String getField3() {\r\n"
				+ "		return field3;\r\n" + "	}\r\n" + "\r\n" + "	public void setField3(String field3) {\r\n"
				+ "		this.field3 = field3;\r\n" + "	}\r\n" + "\r\n" + "	public String getField5() {\r\n"
				+ "		return field5;\r\n" + "	}\r\n" + "\r\n" + "	public void setField5(String field5) {\r\n"
				+ "		this.field5 = field5;\r\n" + "	}\r\n" + "\r\n" + "	public String getField4() {\r\n"
				+ "		return field4;\r\n" + "	}\r\n" + "\r\n" + "	public void setField4(String field4) {\r\n"
				+ "		this.field4 = field4;\r\n" + "	}\r\n" + "\r\n" + "	public List<User> getUserList() {\r\n"
				+ "		return userList;\r\n" + "	}\r\n" + "\r\n"
				+ "	public void setUserList(List<User> userList) {\r\n" + "		this.userList = userList;\r\n"
				+ "	}\r\n" + "\r\n" + "	public void testMethod() {\r\n" + "		int age = 18, name = 14;\r\n"
				+ "//		System.out.println(name);\r\n" + "		if (age > 19) {\r\n" + "\r\n"
				+ "			out.println(\"adul\\t+_{++== = （ ? \");\r\n" + "		} else {\r\n"
				+ "			out.println(\"child\");\r\n" + "		}\r\n" + "		for (int i = 0; i < 10; i++) {\r\n"
				+ "			out.println(i);\r\n" + "		}\r\n" + "		while (age > 0) {\r\n"
				+ "			out.println(age);\r\n" + "			age--;\r\n" + "		}\r\n"
				+ "		StringBuilder s = // test\r\n" + "				new StringBuilder();\r\n"
				+ "		s.append(\"age:\").append(age).append(\",name:\").append(name);\r\n" + "	}\r\n" + "\r\n"
				+ "	@Override\r\n" + "	public void run() {\r\n" + "	}\r\n" + "\r\n" + "}\r\n" + "\r\n"
				+ "class TestBean1 {\r\n" + "\r\n" + "}\r\n" + "", f);
	}

	@AfterClass
	public static void destroy() {
		FileUtil.delete(f);
	}

	@Test
	public void testParse() throws IOException {

		new SourceUtil();

		JCCompilationUnit unit = SourceUtil.parseFile(f.getAbsolutePath());

		BaseClassInfo classInfo = SourceUtil.parseUnit(unit);

		Assert.assertEquals(classInfo.getPackageName(), "parse");

		Assert.assertEquals(classInfo.getBlockList().size(), 1);

		Assert.assertEquals(classInfo.getImportMap().size(), 12);

		Assert.assertEquals(classInfo.getImportStaticMap().size(), 1);

		Assert.assertEquals(classInfo.getFieldList().size(), 7);

		Assert.assertEquals(classInfo.getMethodList().size(), 14);
	}

	@Test
	public void testParse1() throws IOException {

		JCCompilationUnit unit = SourceUtil.parseFile(f.getAbsolutePath());

		new ExpressionScanner().scan(unit, null);
	}
}
