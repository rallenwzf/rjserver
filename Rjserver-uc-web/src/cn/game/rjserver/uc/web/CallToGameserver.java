/**
 * 
 */
package cn.game.rjserver.uc.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import cn.game.rjserver.utils.json.JsonUtil;


/**
 * 通知游戏服
 * 
 * @author wzf(rallen)
 * 
 */
public class CallToGameserver {

	public static void callExit(String httpUrl, long uid) {
		Map map = new HashMap();
		map.put("id", 1001);
		map.put("sid", 0);
		map.put("uid", uid);
		try {
			String jsonstr = JsonUtil.getJsonString4JavaObj(map);
			todoCallServer(httpUrl, jsonstr);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void todoCallServer(String httpUrl, String jsonstr) {
		OutputStream os = null;
		InputStream is = null;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 设置heard
			// conn.addRequestProperty(ServerConfig.SystemAttrConfig.GameServerManager_pwd_headkey,
			// ServerConfig.SystemAttrConfig.GameServerManager_Pwd);
			// body
			os = conn.getOutputStream();
			os.write(jsonstr.getBytes(Charset.forName("UTF-8")));

			os.flush();
			os.close();

			int nRespCode = conn.getResponseCode();
			System.out.println(nRespCode);
			is = conn.getInputStream();
			int count = 0;
			while (count == 0) {
				count = is.available();
			}
			byte[] b = new byte[count];
			int readCount = 0;
			while (readCount < count) {
				readCount += is.read(b, readCount, count - readCount);
			}
			is.close();

			// 解析
			// String resjsonstr = new String(b, Charset.forName("UTF-8"));
			// System.out.println(resjsonstr);
			// Map resmap = JsonUtil.getMap4Json(resjsonstr);
			// System.out.println("指令号：" + resmap.get("id") + "__" );

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				is.close();
			} catch (Exception e) {
			}
		}
	}
}
