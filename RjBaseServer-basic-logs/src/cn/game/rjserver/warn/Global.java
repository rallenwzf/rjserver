/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.warn;

import java.io.File;

import cn.game.rjserver.log.LoggerConfig;
import cn.game.rjserver.warn.mode.EmailEntity;
import cn.game.rjserver.warn.mode.MessageSendModeByApi;

/**
 * @author wangzhifeng(rallen)
 */
public class Global {
	public static boolean PROJECT_SELF_WARN = true;
	public static int PROJECT_CODE = 1;
	public static String PROJECT_CODESTRING = "newddz";
	public static String PROJECT_NAME = "灵魂地狱";
	public static String MANAGER_PHONE = "15801412517";
	public static String MANAGER_NAME = "王智锋";
	public static String MANAGER_EMAIL = "rallen.wang@foxmail.com";
	private static EmailEntity emailEntity;

	public static class WARNMODE {
		public static final int MODE_BASIC = 0;
		public static final int MODE_SMS = 1;
		public static final int MODE_EMAIL = 2;
	}

	public static EmailEntity getEmailEntity() {
		return emailEntity;
	}

	public static void setEmailEntity(EmailEntity entity) {
		emailEntity = entity;
	}

	public static void load() {
		String str = LoggerConfig.getConfig("Global.PROJECT_SELF_WARN");
		if (str != null && str.equals("1")) {
			Global.PROJECT_SELF_WARN = true;
		} else {
			Global.PROJECT_SELF_WARN = false;
		}
		str = LoggerConfig.getConfig("Global.PROJECT_CODE");
		if (str != null && !str.equals("")) {
			Global.PROJECT_CODE = Integer.parseInt(str);
		}
		str = LoggerConfig.getConfig("Global.PROJECT_CODESTRING");
		if (str != null && !str.equals("")) {
			Global.PROJECT_CODESTRING = str;
		}
		str = LoggerConfig.getConfig("Global.PROJECT_NAME");
		if (str != null && !str.equals("")) {
			Global.PROJECT_NAME = str;
		}
		str = LoggerConfig.getConfig("Global.MANAGER_PHONE");
		if (str != null && !str.equals("")) {
			Global.MANAGER_PHONE = str;
		}
		str = LoggerConfig.getConfig("Global.MANAGER_NAME");
		if (str != null && !str.equals("")) {
			Global.MANAGER_NAME = str;
		}
		str = LoggerConfig.getConfig("Global.MANAGER_EMAIL");
		if (str != null && !str.equals("")) {
			Global.MANAGER_EMAIL = str;
		}
		//
		try {
			String logpath = LoggerConfig.getConfig("Global.PROJECT_LOGPATH");
			if (logpath != null && !logpath.equals("")) {
				String lps[] = logpath.split(",");
				for (String lp : lps) {
					File f = new File(lp);
					System.out.println(f.getAbsolutePath());
					if (!f.exists()) {
						f.mkdirs();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//
		MessageSendModeByApi.load();
	}

}
