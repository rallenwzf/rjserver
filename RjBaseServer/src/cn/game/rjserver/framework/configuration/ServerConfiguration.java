/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.configuration;

/**
 * @author Administrator
 */
public class ServerConfiguration {

	// DataOutputStream.writeUTF数据
	public static final int CODE_SOCKET_UTFSTRING = 0;
	// 字节数组
	public static final int CODE_SOCKET_BYTES = 1;

	/**
	 * 客户端使用的字符形式
	 */
	public static int CODE_TYPE = CODE_SOCKET_UTFSTRING;

	public static String host = null;
	public static int port = 9999;

	/**
	 * 是否启动mina线程池
	 */
	public static boolean IS_RUN_MINA_THREADPOOLS = true;

	public static void reload(String configFileName) {
		Configuration.configure(configFileName);
		String str = null;
		str = Configuration.getConfig("host");
		if (str != null && !str.equals("")) {
			host = str;
		}

		str = Configuration.getConfig("port");
		if (str != null && !str.equals("")) {
			port = Integer.parseInt(str);
		}

		str = Configuration.getConfig("code_type");
		if (str != null && !str.equals("")) {
			CODE_TYPE = Integer.parseInt(str);
		}
		String poolConf = Configuration.getConfig("threadpool");
		if (poolConf != null && poolConf.trim().equals("true")) {
			ServerConfiguration.IS_RUN_MINA_THREADPOOLS = true;
		}
	}

	static {
		reload(null);
	}

}
