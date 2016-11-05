package cn.game.rjserver.db.support.jdbc;

import java.io.*;
import java.util.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: w-infinity
 * </p>
 * 
 * @author yanhai.jiang
 * @version 1.0
 */

public class DbInfo {
	private static String filePath;
	private String dbUrl = null;
	private String className = null;
	private String usr = null;
	private String pwd = null;

	public String getDbUrl() {
		return dbUrl;
	}

	public String getClassName() {
		return className;
	}

	public String getUsr() {
		return usr;
	}

	public String getPwd() {
		return pwd;
	}

	public void setDbInfo() throws IOException {
		try {
			Properties properties = new Properties();
			String filePath = getFilePath();
			File file = new File(filePath);
			FileInputStream fileInputStream = new FileInputStream(file);
			properties.load(fileInputStream);

			dbUrl = (String) properties.get("dbUrl");
			className = (String) properties.get("className");
			usr = (String) properties.get("usr");
			pwd = (String) properties.get("pwd");

			fileInputStream.close();

		} catch (IOException ie) {
			ie.printStackTrace();
			throw ie;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getFilePath() {
		return filePath;
	}

	public static void setFilePath(String filePath) {
		DbInfo.filePath = filePath;
	}

}