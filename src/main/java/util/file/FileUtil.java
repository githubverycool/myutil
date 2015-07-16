package util.file;

import java.io.File;
import java.io.IOException;

public class FileUtil {

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
	 * 根据路径名创建文件，若父目录不存在，会自动创建父目录和改文件.
	 * 若创建的文件已经存，则返回的是该文件
	 * 
	 * 若创建过程中出现异常，会删除新创建的目录和文件.
	 * 可以根据 isDir指示该路径是否为目录
	 * @param pathname
	 * @param isDir
	 * @return
	 * @throws IOException
	 */
	public static File createDirAndFile(String pathname, boolean isDir)
			throws IOException {
		File file = new File(pathname);
		String tempPath = null;
		File tempFile = new File(pathname);
		try {
			while (!tempFile.exists()) {
				tempPath = tempFile.getAbsolutePath();
				tempFile = tempFile.getParentFile();
			}
			if (isDir) {
				file.mkdirs();
			} else {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
		} catch (IOException e) {
			if (tempPath != null) {
				File f = new File(tempPath);
				if (f.isFile()) {
					f.delete();
				} else {
					deleteDirectory(tempPath);
				}
			}
			throw new IOException(e);
		}
		return file;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 根据路径删除文件
	 * @param sPath
	 * @return
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	public static File getFile(String pathname) {
		return new File(pathname);
	}

	public static void main(String[] args) throws IOException {
		String pathname = "E:\\iotest\\a\\a\\a.txt";
		createDirAndFile(pathname,false);
	}
}
