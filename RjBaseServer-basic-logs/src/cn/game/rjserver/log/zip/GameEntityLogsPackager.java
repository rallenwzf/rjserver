/**
 * rallen.wang@gmail.com
 */
package cn.game.rjserver.log.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * @author wangzhifeng(rallen)
 */
public class GameEntityLogsPackager {
	private Logger logger = Logger.getLogger(this.getClass());

	private String logSourcePath;
	private String logTargetFilePath;
	private String logTargetFileName;
	private String logBakPath;

	public String getLogSourcePath() {
		return logSourcePath;
	}

	public void setLogSourcePath(String logSourcePath) {
		this.logSourcePath = logSourcePath;
	}

	public String getLogBakPath() {
		return logBakPath;
	}

	public void setLogBakPath(String logBakPath) {
		this.logBakPath = logBakPath;
	}

	public String getLogTargetFilePath() {
		return logTargetFilePath;
	}

	public void setLogTargetFilePath(String logTargetFilePath) {
		this.logTargetFilePath = logTargetFilePath;
	}

	public String getLogTargetFileName() {
		return logTargetFileName;
	}

	public void setLogTargetFileName(String logTargetFileName) {
		this.logTargetFileName = logTargetFileName;
	}

	/**
	 * 日志打包
	 */
	public void createPackLog() {
		System.out.println(logSourcePath);
		System.out.println(logBakPath);
		System.out.println(logTargetFilePath);
		logger.info(new Date() + " 执行日志打包开始!");
		if (logSourcePath == null || logBakPath == null || logTargetFilePath == null) {
			logger.info(new Date() + " 失败，日志打包类属性存在空值!");
		} else {

			try {
				(new File(logTargetFilePath)).mkdirs();
				copyDirectiory(logSourcePath, logBakPath);
				createZip(logBakPath, logTargetFilePath + File.separator + logTargetFileName);
				del(logSourcePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info(new Date() + " 执行日志打包完成!");
	}

	// 复制文件夹
	private void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator
						+ file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}

	// 复制文件
	private void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	private void createZip(String packPath, String zipPath) throws IOException {
		File inFile = new File(packPath);
		System.out.println("packPath=" + packPath);
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath));
		// zipFile(inFile, zos, "");
		File[] files = inFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory())
				zipFile(file, zos, "");
			else
				zipFile(file, zos, "");
		}
		zos.close();
	}

	private void zipFile(File inFile, ZipOutputStream zos, String dir) throws IOException {
		if (inFile.isDirectory()) {
			File[] files = inFile.listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isDirectory())
					zipFile(file, zos, inFile.getName() + File.separator);
				else
					zipFile(file, zos, inFile.getName());
			}
		} else {
			String entryName = null;
			if (!"".equals(dir))
				entryName = dir + "\\" + inFile.getName();
			else
				entryName = inFile.getName();

			ZipEntry entry = new ZipEntry(entryName);
			zos.putNextEntry(entry);
			InputStream is = new FileInputStream(inFile);
			int len = 0;
			while ((len = is.read()) != -1)
				zos.write(len);
			is.close();
		}
	}

	private void del(String filepath) throws IOException {
		File f = new File(filepath);// 定义文件路径
		if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
			if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
				f.delete();
			} else {// 若有则把文件放进数组，并判断是否有下级目录
				File delFile[] = f.listFiles();
				int i = f.listFiles().length;
				for (int j = 0; j < i; j++) {
					if (delFile[j].isDirectory()) {
						del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
					}
					delFile[j].delete();// 删除文件
				}
			}

			f.delete();// 删除主目录
		}

	}
}
