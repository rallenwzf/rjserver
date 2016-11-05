/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.uc.utils;

import java.util.Random;

/**
 * @author wangzhifeng(rallen)
 */
public class UserIdFormat {

	public static String getClientUserId(long uid) {
		// 默认六位长度
		String userId = uid + "";
		int num = userId.length();
		if (num < 6) {
			for (int i = 0; i < 6 - num; i++) {
				userId = "0" + userId;
			}
		}
		return userId;
	}

	public static String getUserpwdString() {
		// String chars =
		// "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWSYZ";
		String chars = "abcdefghijklmnopqrstuvwxyz";
		String szString = "";
		for (int i = 0; i < 4; i++) {
			szString += new Random().nextInt(10);
		}
		String dString = "";
		for (int j = 0; j < 2; j++) {
			dString += chars.charAt(new Random().nextInt(chars.length())) + "";
		}
		return dString + szString;
	}

	public static long getServerUid(String clientUserId) {
		return Long.parseLong(clientUserId);
	}

	public static void main(String args[]) {
		System.out.println(getUserpwdString());
	}
}
