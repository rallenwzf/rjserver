package cn.game.rjserver.db.support.springhibernate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringManager {

	private static ApplicationContext context = null;

	public static synchronized void loadFileSystemXmlApplicationContext(String path) {
		if (context == null) {
			try {
				context = new FileSystemXmlApplicationContext(new String[] { path + "tdhy-etc.xml",
						path + "/tdhy-context.xml", path + "/tdhy-impl.xml", path + "/tdhy-pro.xml" });
			} catch (Exception e) {
			}
		}
	}

	public static void setServletContext(ApplicationContext ac) {
		context = ac;
	}

	/*
	 * get spring bean
	 */
	public static Object getBean(String name) {
		if (context == null) {
			return null;
		} else {
			return context.getBean(name);
		}
	}

	/*
	 * get spring webapplicationcontext
	 */
	public static ApplicationContext getApplicationContext() {
		if (context == null) {
			return null;
		} else {
			return context;
		}

	}
}