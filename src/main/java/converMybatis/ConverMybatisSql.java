package converMybatis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConverMybatisSql {
	public static void main(String[] args) throws IOException {
		createInsert();
		createUpdate();
		createInt();
		createTime();
		createDouble();
	}

	static void createUpdate() throws IOException {
		String pathname = "E:\\pythonPlot\\sql";
		File file = new File(pathname);
		File[] files = new File[0];
		if (file.isDirectory()) {
			files = file.listFiles();
		}
		String content = "";
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				content = readDataToUpdate(files[i]);
				System.out.println(files[i].getName());
				writerData(pathname + "\\update\\" + files[i].getName(),
						content);
			}
		}
	}

	static String readDataToUpdate(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		String[] attrs = null;
		StringBuilder head = new StringBuilder();
		head.append("UPDATE voole_global." + file.getName().split("\\.")[0]
				+ " SET ");
		while ((line = br.readLine()) != null) {
			attrs = line.split(" ", 2);
			String attrs0 = attrs[0];
			String attrs1=attrs[1];
			String sattrs0 = attrs0.substring(1, attrs0.length() - 1);
			String sub = "IFNULL(" + "#{" + sattrs0 + "}," + sattrs0 + ")";
			if (attrs1.indexOf("int") >= 0) {
				sub = "CAST(" + sub + " AS SIGNED)";
			} else if (attrs1.indexOf("DECIMAL") >= 0
					|| attrs1.indexOf("double") >= 0
					|| attrs1.indexOf("float") >= 0) {
				sub = "CAST(" + sub + " AS DECIMAL)";
			} else {
				System.out.println("String 类型");
			}
			head.append(attrs0+"=" + sub+",");
		}
		br.close();
		head.deleteCharAt(head.length() - 1);
		return head.toString();
	}

	static void createInsert() throws IOException {
		String pathname = "E:\\pythonPlot\\sql";
		File file = new File(pathname);
		File[] files = new File[0];
		if (file.isDirectory()) {
			files = file.listFiles();
		}
		String content = "";
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				content = readDataToInsert(files[i]);
				System.out.println(files[i].getName());
				writerData(pathname + "\\insert\\" + files[i].getName(),
						content);
			}
		}
	}

	static void writerData(String pathname, String content) throws IOException {
		File file = new File(pathname);
		file.getParentFile().mkdirs();
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		fw.write(content);
		fw.close();
	}

	static void createInt() throws IOException {
		String pathname = "E:\\pythonPlot\\sql";
		File file = new File(pathname);
		File[] files = new File[0];
		if (file.isDirectory()) {
			files = file.listFiles();
		}
		String content = "";
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				content = readDataToInt(files[i]);
				System.out.println(files[i].getName());
				writerData(pathname + "\\int\\" + files[i].getName(), content);
			}
		}
	}

	static void createDouble() throws IOException {
		String pathname = "E:\\pythonPlot\\sql";
		File file = new File(pathname);
		File[] files = new File[0];
		if (file.isDirectory()) {
			files = file.listFiles();
		}
		String content = "";
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				content = readDataToDouble(files[i]);
				System.out.println(files[i].getName());
				writerData(pathname + "\\double\\" + files[i].getName(),
						content);
			}
		}
	}

	static void createTime() throws IOException {
		String pathname = "E:\\pythonPlot\\sql";
		File file = new File(pathname);
		File[] files = new File[0];
		if (file.isDirectory()) {
			files = file.listFiles();
		}
		String content = "";
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				content = readDataToTime(files[i]);
				System.out.println(files[i].getName());
				writerData(pathname + "\\time\\" + files[i].getName(), content);
			}
		}
	}

	static String readDataToInt(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		String[] attrs = null;
		// String[] arr = { "char", "text",
		// "time","double","float","decimal","int","data" };
		String[] arr = { "int" };
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			attrs = line.split(" ", 2);
			String attrs0 = attrs[0];
			attrs0 = attrs0.substring(1, attrs0.length() - 1);
			String attrs1 = attrs[1];
			for (int i = 0; i < arr.length; i++) {
				if (attrs1.indexOf(arr[i]) >= 0) {
					sb.append("\"" + attrs0 + "\",");
				}
			}

		}
		br.close();
		if (sb.length() != 0) {
			return sb.deleteCharAt(sb.length() - 1).toString();
		}
		return "";
	}

	static String readDataToDouble(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		String[] attrs = null;
		// String[] arr = { "char", "text",
		// "time","double","float","decimal","int","data" };
		String[] arr = { "float", "double", "decimal" };
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			attrs = line.split(" ", 2);
			String attrs0 = attrs[0];
			attrs0 = attrs0.substring(1, attrs0.length() - 1);
			String attrs1 = attrs[1];
			boolean b = false;
			for (int i = 0; i < arr.length; i++) {
				if (attrs1.indexOf(arr[i]) >= 0) {
					b = true;
				}
			}
			if (b) {
				sb.append("\"" + attrs0 + "\",");
			}

		}
		br.close();
		if (sb.length() != 0) {
			return sb.deleteCharAt(sb.length() - 1).toString();
		}
		return "";
	}

	static String readDataToTime(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		String[] attrs = null;
		// String[] arr = { "char", "text",
		// "time","double","float","decimal","int","data" };
		String[] arr = { "time", "data" };
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			attrs = line.split(" ", 2);
			String attrs0 = attrs[0];
			attrs0 = attrs0.substring(1, attrs0.length() - 1);
			String attrs1 = attrs[1];
			boolean b = false;
			for (int i = 0; i < arr.length; i++) {
				if (attrs1.indexOf(arr[i]) >= 0) {
					b = true;
				}
			}
			if (b) {
				sb.append("\"" + attrs0 + "\",");
			}

		}
		br.close();
		if (sb.length() != 0) {
			return sb.deleteCharAt(sb.length() - 1).toString();
		}
		return "";
	}

	static String readDataToInsert(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		String[] attrs = null;
		StringBuilder head = new StringBuilder();
		head.append("INSERT INTO voole_global."
				+ file.getName().split("\\.")[0] + "(");
		StringBuilder values = new StringBuilder();
		values.append("VALUES(");
		while ((line = br.readLine()) != null) {
			attrs = line.split(" ", 2);
			String attrs0 = attrs[0];
			String attrs1 = attrs[1];
			head.append(attrs0 + ",");
			attrs0 = attrs0.substring(1, attrs0.length() - 1);// 去掉#{`key`}中
																// ``符号
			String sub = null;
			if (attrs1.indexOf("NOT NULL") >= 0) {// 如果不能为null
				if (attrs1.indexOf("DEFAULT") >= 0) {// 有默认值按照默认值
					sub = "IFNULL(" + "#{" + attrs0 + "},DEFAULT(" + attrs0
							+ "))";
				} else {// 既不能为null也没有默认值应该报异常，但是根据实际业务处理，将 string 类 按 “”当成默认值
						// 其它类型只能报异常
					if (attrs1.indexOf("char") >= 0
							|| attrs1.indexOf("text") >= 0) {
						sub = "IFNULL(" + "#{" + attrs0 + "},\"\")";
					}else{
						sub="#{"+attrs0+"}";
					}

				}
			} else {// 如果该字段可以为null 也要判断是否有默认值如果有默认值，走默认值
				if (attrs1.indexOf("DEFAULT") >= 0) {// 有默认值按照默认值
					sub = "IFNULL(" + "#{" + attrs0 + "},DEFAULT(" + attrs0
							+ "))";
				} else {
					sub = "#{" + attrs0 + "}";
				}
			}
			if (attrs1.indexOf("int") >= 0) {
				sub = "CAST(" + sub + " AS SIGNED)";
			} else if (attrs1.indexOf("DECIMAL") >= 0
					|| attrs1.indexOf("double") >= 0
					|| attrs1.indexOf("float") >= 0) {
				sub = "CAST(" + sub + " AS DECIMAL)";
			} else {
				System.out.println("String 类型");
			}

			values.append(sub + ",");
		}
		br.close();
		head.deleteCharAt(head.length() - 1);
		head.append(")");
		values.deleteCharAt(values.length() - 1);
		values.append(");");
		return head.append(values).toString();

	}
}
