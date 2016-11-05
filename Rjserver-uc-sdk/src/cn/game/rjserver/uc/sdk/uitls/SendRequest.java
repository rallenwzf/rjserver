package cn.game.rjserver.uc.sdk.uitls;

import java.io.*;

import java.net.URL;
import java.net.URLConnection;

import cn.game.rjserver.uc.sdk.Config;

/**
 * @author rallen
 * 
 */
public class SendRequest {

	public SendRequest() {
	}

	/**
	 * 请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String sendPost(String url, String param) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		System.out.println(url);
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("Charset", "utf-8");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),
				Config.POST_RESPONSE_ENDODING));
		System.out.println("__________web请求_____realUrl:" + realUrl + "__________param:" + param);
		out.print(param);
		out.flush();
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(),
				Config.POST_RESPONSE_ENDODING));
		String line;
		while ((line = in.readLine()) != null) {
			result = (new StringBuilder(String.valueOf(result))).append("\n").append(line)
					.toString();
		}
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}

}