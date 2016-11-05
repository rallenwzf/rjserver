/**
 * rallen wang@gmail.com
 */
package cn.game.rjserver.basicutils.html;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author wangzhifeng(rallen)
 */
public class HtmlUrlConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String getHtml(String httpUrl, String code) {
		OutputStream os = null;
		InputStream is = null;
		try {

			URL url = new URL(httpUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 设置通信密码
			// conn.addRequestProperty(ServerConfig.SystemAttrConfig.GameServerManager_pwd_headkey,
			// ServerConfig.SystemAttrConfig.GameServerManager_Pwd);
			// body
			os = conn.getOutputStream();
			// os.write(formatAg());
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
			return new String(b, code);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				is.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

}
