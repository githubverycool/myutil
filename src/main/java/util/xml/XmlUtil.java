package util.xml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtil {

	private static SAXReader saxReader;

	public static synchronized SAXReader getSAXReader() {
		if (saxReader == null) {
			saxReader = new SAXReader();
		}
		return saxReader;
	}

	/**
	 * 将document 转换成map 注意 节点名称相同会保存在list中存放到map中
	 * 
	 * @param doc
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> domToMap(Document doc) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator<?> iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), elementToMap(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	/**
	 * 将节点转换成map 注意 节点名称相同会保存在list中存放到map中
	 * 
	 * @param e
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map elementToMap(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = elementToMap(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	/**
	 * 获取一个在该项目下以当前时间创建的空txt文件
	 * 
	 * @return
	 */
	public static File getDefaultFile() {
		File file = new File(System.getProperty("user.dir") + File.separator
				+ System.currentTimeMillis() + ".txt");
		if (file.exists()) {
			return getDefaultFile();
		}
		return file;
	}

	/**
	 * 通过一个url链接将获取的内容写到本地默认文件中
	 * 
	 * @param url
	 * @return 返回保存在本地文件的路径
	 * @throws IOException
	 */

	private static final int CONN_TIMEOUT = 60000;
	private static final int READ_TIMEOUT = 12000000;
	private static final String REQUEST_METHOD = "GET";

	public static String writeLocal(String url) throws IOException {
		HttpURLConnection connect = null;
		InputStream in = null;
		String pathname = null;
		try {
			URL urlObj = new URL(url);
			connect = (HttpURLConnection) urlObj.openConnection();
			connect.setConnectTimeout(CONN_TIMEOUT);
			connect.setReadTimeout(READ_TIMEOUT);
			connect.setRequestMethod(REQUEST_METHOD);
			in = connect.getInputStream();
			pathname = write(in);
		} catch (IOException e) {
			throw new IOException("通过url获取输入流写入本地文件异常!", e);
		} finally {
			if (in != null) {
				in.close();
			}
			if (connect != null) {
				connect.disconnect();
			}
		}
		return pathname;
	}

	public static File writeLocalFile(String url) throws IOException {
		return new File(writeLocal(url));
	}

	/**
	 * 将InputStream数据读出写入本地文件 注意 传入进来的 读入流，该方法并不会关闭
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static String write(InputStream in) throws IOException {
		String dir = System.getProperty("user.dir") + File.separator
				+ System.currentTimeMillis() + ".xml";
		FileOutputStream fo = null;
		try {
			fo = getFOut(dir, false);
			int len = 0;
			byte[] b = new byte[8092];
			while ((len = in.read(b)) != -1) {
				fo.write(b, 0, len);
			}
		} catch (IOException e) {
			File file = new File(dir);
			if (file.exists()) {
				file.delete();
			}
			throw new IOException("输入流写入本地异常!", e);
		} finally {
			if (fo != null) {
				fo.close();
			}

		}
		return dir;
	}

	/**
	 * 通过文件路径返回一个文件读入流 注意 返回的文件读入流需要调用者手动关闭
	 * 
	 * @param pathname
	 * @return
	 * @throws IOException
	 */
	public static FileInputStream getFIn(String pathname) throws IOException {
		File file = new File(pathname);
		return getFIn(file);
	}

	/**
	 * 通过文件返回一个文件读入流 注意 返回的文件读入流需要调用者手动关闭
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileInputStream getFIn(File file) throws IOException {
		FileInputStream fi = new FileInputStream(file);
		return fi;
	}

	public static BufferedInputStream getBIn(File file) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(getFIn(file));
		return bin;
	}

	/**
	 * 通过文件返回一个文件读出流 注意 返回的文件读出流需要调用者手动关闭
	 * 
	 * @param pathname
	 * @param append
	 * @return
	 * @throws IOException
	 */
	public static FileOutputStream getFOut(String pathname, boolean append)
			throws IOException {
		File file = new File(pathname);
		FileOutputStream fo = new FileOutputStream(file, append);
		return fo;
	}

	/**
	 * 返回一个文件读出流,默认在本地创建了一个文件，该文件默认不能进行追加
	 * 
	 * @return
	 * @throws IOException
	 */
	public static FileOutputStream getDefaultFOut() throws IOException {
		File file = getDefaultFile();
		FileOutputStream fo = new FileOutputStream(file);
		return fo;
	}
}
