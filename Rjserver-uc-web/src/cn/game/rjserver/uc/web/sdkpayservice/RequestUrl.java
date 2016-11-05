/**
 * 
 */
package cn.game.rjserver.uc.web.sdkpayservice;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 * 
 */
public class RequestUrl {
	static Logger logger = Logger.getLogger(RequestUrl.class);

	public static void runUrl(final String url) {
		ServerExecutor.getInstance().addTask(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				logger.info("_______push：" + url);
				String html = getHtml(url, "utf-8");
				logger.info("_______push—result：" + html);
			}
		});
	}

	public static void runUrl(final String url, final String params) {
		ServerExecutor.getInstance().addTask(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				logger.info("_______push：" + url);
				String html = getPostHtml(url, params, "utf-8");
				logger.info("_______push—result：" + html);
			}
		});
	}

	public static void main(String args[]) {
		String url = "http://123.103.20.4:8286/sync/recCuSyncData?result=0&softgood=20%E9%92%BB%E7%9F%B3&gamecode=200736&customorderno=100002469&pkey=50ccf2d6ebce27fdae1516c52e4b4637&paymentid=SMSB18A48BE9BCB48E883D73CFD3F4BB90A96414&errorstr=&softserver=&date=2014-01-23 16:59:27&customer=3746&channelid=001&playername=%E7%8E%A9%E5%AE%B62003746&paytype=sms&company=%E6%B7%B1%E5%9C%B3%E5%B8%82%E5%BE%B7%E4%BD%B3%E4%BF%A1%E6%81%AF%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&money=200";
		String turl = null;
		try {
			turl = URLEncoder.encode(url, "UTF-8");
			System.out.println(turl);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String html = getHtml(turl, "utf-8");
		System.out.println(html);
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

	public static String getPostHtml(String httpUrl, String params, String code) {
		OutputStream os = null;
		InputStream is = null;
		try {

			URL url = new URL(httpUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// body
			os = conn.getOutputStream();
			byte[] bypes = params.toString().getBytes();
			os.write(bypes);// 输入参数
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
	//
	// public static String getPostXmlHtml(String httpUrl, YdcpNewDataObj
	// dataObj, String code) {
	// OutputStream os = null;
	// InputStream is = null;
	// try {
	//
	// URL url = new URL(httpUrl);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("POST");
	// conn.setRequestProperty("accept", "*/*");
	// conn.setRequestProperty("connection", "Keep-Alive");
	// conn.setRequestProperty("user-agent",
	// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
	// conn.setDoOutput(true);
	// conn.setDoInput(true);
	// // body
	// os = conn.getOutputStream();
	// StringBuffer buf = new StringBuffer();
	// buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	// buf.append("<request>");
	// buf.append("<userId>" + dataObj.getUserId() + "</userId>");
	// buf.append("<cpServiceId>" + dataObj.getCpId() + "</cpServiceId>");
	// buf.append("<consumeCode>" + dataObj.getConsumeCode() +
	// "</consumeCode>");
	// buf.append("<cpParam>" + dataObj.getCpparam() + "</cpParam>");
	// buf.append("<hRet>" + dataObj.gethRet() + "</hRet>");
	// buf.append("<status>" + dataObj.getStatus() + "</status>");
	// buf.append("<transIDO>" + dataObj.getTransIDO() + "</transIDO>");
	// buf.append("<versionId>" + dataObj.getContentId() + "</versionId>");
	// buf.append("</request>");
	// os.write(buf.toString().getBytes());// 输入参数
	// os.flush();
	// os.close();
	//
	// int nRespCode = conn.getResponseCode();
	// System.out.println(nRespCode);
	// is = conn.getInputStream();
	// int count = 0;
	// while (count == 0) {
	// count = is.available();
	// }
	// byte[] b = new byte[count];
	// int readCount = 0;
	// while (readCount < count) {
	// readCount += is.read(b, readCount, count - readCount);
	// }
	// is.close();
	// return new String(b, code);
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// os.close();
	// is.close();
	// } catch (Exception e) {
	// }
	// }
	// return null;
	// }
}
