/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.sdk;

/**
 * @author rallen
 * 
 */
public class Config {
	public static String POST_RESPONSE_ENDODING = "utf-8";

	// 连接登录服务器
	// 192.168.1.128:8080 localhost
	public static String UserApi_Url = "http://localhost:8080/UcServer/UserApi";// 115.29.46.192
																				// ,sg123456
	public static String UC_Api_Pwd = "ESDS365DFT34934HUS7W4HDF78W4";

	public static String ServerApi_Url = "http://localhost:8080/UcServer/ServerApi";// 192.168.1.128

	public static String PayApi_Url = "http://localhost:8080/UcServer/PayApi/201";

	public static void setUcapiHostting(String hostting, String pwd) {
		if (pwd != null && !pwd.trim().equals("")) {
			UC_Api_Pwd = pwd;
		}
		if (hostting != null && !hostting.trim().equals("")) {
			UserApi_Url = hostting + "/UserApi";
			ServerApi_Url = hostting + "/ServerApi";
			PayApi_Url = hostting + "/PayApi";
		}
	}
}
