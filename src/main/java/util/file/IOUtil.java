package util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOUtil {

	private static int defaultBufferSize = 8192;

	/**
	 * 通过文件路径返回一个文件读入流. 注意: 返回的文件读入流需要调用者手动关闭，通过pathname指定的文件必须存在.
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
	 * 通过文件一个文件读入流. 注意: 返回的文件读入流需要调用者手动关闭，通过pathname指定的文件必须存在.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileInputStream getFIn(File file) throws IOException {
		FileInputStream fi = new FileInputStream(file);
		return fi;
	}

	/**
	 * 通过文件路径返回一个带缓冲区文件读入流，缓冲区大小采用默认值8192Byte. 注意:
	 * 返回的文件读入流需要调用者手动关闭，通过pathname指定的文件必须存在.
	 * 
	 * @param pathname
	 * @return
	 * @throws IOException
	 */
	public static BufferedInputStream getBuffIn(String pathname)
			throws IOException {
		return getBuffIn(new File(pathname));
	}

	/**
	 * 通过文件返回一个带缓冲区文件读入流，缓冲区大小采用默认值8192Byte. 注意:
	 * 返回的文件读入流需要调用者手动关闭，通过pathname指定的文件必须存在.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */

	public static BufferedInputStream getBuffIn(File file) throws IOException {
		return getBuffIn(file, defaultBufferSize);
	}

	/**
	 * 通过文件返回一个带缓冲区文件读入流，可以指定缓冲区大小. 注意: 返回的文件读入流需要调用者手动关闭，通过pathname指定的文件必须存在.
	 * 
	 * @param file
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public static BufferedInputStream getBuffIn(File file, int size)
			throws IOException {
		BufferedInputStream bin = new BufferedInputStream(getFIn(file), size);
		return bin;
	}

	/**
	 * 通过文件路径返回一个文件输出流. 注意: 返回的文件读入流需要调用者手动关闭，通过pathname指定的文件必须存在.
	 * 
	 * 
	 * @param pathname
	 * @param append
	 * @return
	 * @throws IOException
	 */
	public static FileOutputStream getFOut(String pathname, boolean append)
			throws IOException {
		File file = new File(pathname);
		return getFOut(file, append);
	}

	/**
	 * 通过文件返回一个文件输出流. 注意: 返回的文件读入流需要调用者手动关闭，通过pathname指定的文件必须存在.
	 * 
	 * @param pathname
	 * @param append
	 * @return
	 * @throws IOException
	 */
	public static FileOutputStream getFOut(File file, boolean append)
			throws IOException {
		FileOutputStream fo = new FileOutputStream(file, append); 
		return fo;
	}

	/**
	 * 通过文件路径返回一个带缓冲区文件输出流，缓冲区大小采用默认值8192Byte. 注意:
	 * 返回的文件输出流需要调用者手动关闭，通过pathname指定的文件必须存在.
	 * 
	 * @param pathname
	 * @param append
	 *            ：是否对该文件追加
	 * @return
	 * @throws IOException
	 */
	public static BufferedOutputStream getBuffOut(String pathname,
			boolean append) throws IOException {
		return getBuffOut(new File(pathname), append, defaultBufferSize);
	}

	/**
	 * 通过文件路径一个带缓冲区文件输出流，缓冲区大小采用默认值8192Byte. 注意:
	 * 返回的文件输出流需要调用者手动关闭，通过pathname指定的文件必须存在.
	 * 
	 * @param
	 * @param append
	 * @return
	 * @throws IOException
	 */
	public static BufferedOutputStream getBuffOut(File file, boolean append)
			throws IOException {
		return getBuffOut(file, append, defaultBufferSize);
	}

	/**
	 * 通过文件返回一个带缓冲区文件输出流，可以指定缓冲区大小. 注意: 返回的文件输出流需要调用者手动关闭，通过pathname指定的文件必须存在.
	 * 
	 * @param file
	 * @param append
	 * @param buffSize
	 * @return
	 * @throws IOException
	 */
	public static BufferedOutputStream getBuffOut(File file, boolean append,
			int buffSize) throws IOException {
		BufferedOutputStream bo = new BufferedOutputStream(
				new FileOutputStream(file, append), buffSize);
		return bo;
	}

	/**
	 * 返回一个文件读出流,会默认在本地创建了一个文件，该文件输出流默认不能进行追加
	 * 
	 * @return
	 * @throws IOException
	 */
	public static FileOutputStream getDefaultFOut() throws IOException {
		File file = FileUtil.getDefaultFile();
		FileOutputStream fo = new FileOutputStream(file);
		return fo;
	}

	/**
	 * 通过文件路径返回一个文件输出流，若文件存在，则返回指定文件，是否追加可以通过append指定。若文件路径不存在会自动创建相关目录和路径.
	 * isDir 指定新创建的文件路径是否为目录.若创建文件出现异常会自动删除新创建的文件和路径.
	 * 
	 * @param pathname
	 * @param append
	 * @param isDir
	 * @return
	 * @throws IOException
	 */
	public static FileOutputStream createFOut(String pathname, boolean append,
			boolean isDir) throws IOException {
		File file = new File(pathname);
		if (file.exists()) {
			return getFOut(file, append);
		} else {
			FileUtil.createDirAndFile(pathname, isDir);
			return getFOut(file, false);// 新文件不需要指定是否可以追加
		}
	}

	/**
	 * 通过文件路径返回一个文件输出流，若文件存在，则返回指定文件，该文件不可追加，原内容会在写入被清空。若文件路径不存在会自动创建相关目录和路径.
	 * isDir 指定新创建的文件路径是否为目录.若创建文件出现异常会自动删除新创建的文件和路径.
	 * 
	 * @param pathname
	 * @param append
	 * @param isDir
	 * @return
	 * @throws IOException
	 */
	public static FileOutputStream createFOut(String pathname, boolean isDir)
			throws IOException {
		File file = new File(pathname);
		if (file.exists()) {
			return getFOut(file, true);
		} else {
			FileUtil.createDirAndFile(pathname, isDir);
			return getFOut(file, false);// 新文件不需要指定是否可以追加
		}
	}

	public static void main(String[] args) throws IOException {
		createFOut("E:\\iotest\\a\\a\\a.txt", true, false);
	}
}
