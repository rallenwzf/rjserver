/*********************************************
    Copyright (c) 2011 by rallen
 *********************************************/

package cn.game.rjserver.basicutils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rallen 2011-4-26
 */
public class FileUtils {
	/**
	 * 
	 * 删除文件，可以是单个文件或文件夹
	 * 
	 * @param fileName
	 *            待删除的文件名
	 * 
	 * @return 文件删除成功返回true,否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			// System.out.println("删除文件失败：" + fileName + "文件不存在");
			return false;
		} else {
			if (file.isFile()) {
				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * 
	 * @return 单个文件删除成功返回true,否则返回false
	 */

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			// System.out.println("删除单个文件" + fileName + "成功！");
			return true;
		} else {
			// System.out.println("删除单个文件" + fileName + "失败！");
			return false;
		}
	}

	/**
	 * 
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * 
	 * @return 目录删除成功返回true,否则返回false
	 */

	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			// System.out.println("删除目录失败" + dir + "目录不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			// System.out.println("删除目录失败");
			return false;

		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			System.out.println("删除目录" + dir + "失败！");
			return false;

		}
	}

	public static List<String> readFileList(String dir) {
		List<String> list = new ArrayList<String>();
		readFileList(list, dir);
		return list;
	}

	public static void readFileList(List<String> list, String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			// System.out.println("删除目录失败" + dir + "目录不存在！");
			return;
		}
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				list.add(files[i].getAbsolutePath());
			}
			// 删除子目录
			else {
				readFileList(list, files[i].getAbsolutePath());
			}
		}
	}

	public static boolean createFile(String fileName) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				String dir = fileName.split("\\.")[0];
				new File(dir).mkdirs();
			}
			return file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 读文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath, String code) {
		try {
			File f = new File(filePath);
			if (f.exists()) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(filePath)), code));
				String line;
				StringBuffer pageBuffer = new StringBuffer();
				// 循环读取内容
				while ((line = reader.readLine()) != null) {
					pageBuffer.append(line + "\n");
				}
				return pageBuffer.toString();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 读文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> readFileLine(String filePath, String code) {
		try {
			List<String> list = new ArrayList<String>();
			File f = new File(filePath);
			if (f.exists()) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(filePath)), code));
				String line;
				// StringBuffer pageBuffer = new StringBuffer();
				// 循环读取内容
				while ((line = reader.readLine()) != null) {
					// pageBuffer.append(line + "\n\r");
					// System.out.println(line);
					list.add(line);
				}
				return list;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 * @param content
	 * @param isAppend
	 */
	public static void writeFile(String filePath, String content, boolean isAppend, String code) {
		File f = new File(filePath);
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			// FileWriter fw=new FileWriter(filePath);
			// BufferedWriter bw = new BufferedWriter(out);
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath, isAppend), code);
			out.append(content);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件复制
	 * 
	 * @doc class explain: containing a copyFile method
	 * @param srcFilePath
	 * @param dstFilePath
	 *            void
	 */
	public static void copyFile(String srcFilePath, String dstFilePath) {
		try {
			// 旧地址
			FileChannel srcChannel = new FileInputStream(srcFilePath).getChannel();
			// 新地址
			FileChannel dstChannel = new FileOutputStream(dstFilePath).getChannel();
			// Copy file contents from source to destination
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
			// Close the channels
			srcChannel.close();
			dstChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
