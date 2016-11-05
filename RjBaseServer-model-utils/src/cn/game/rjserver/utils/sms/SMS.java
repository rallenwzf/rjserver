/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.utils.sms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.game.rjserver.utils.sms.htmlparser.HtmlParser;

/**
 * @author Administrator
 *         <p>
 *         手机短信业务
 */
public class SMS {
	static Logger logger = Logger.getLogger(SMS.class);
	private static SMS sms;
	private String urlPix;
	private String phoneName;
	private String contentName;

	public static SMS getInstance() {
		if (sms == null) {
			sms = new SMS();
		}
		return sms;
	}

	public void setConf(String urlPix, String phoneName, String contentName) {
		this.urlPix = urlPix;
		this.phoneName = phoneName;
		this.contentName = contentName;
	}

	//
	// /**
	// * @param args
	// */
	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	// HtmlParser p = new HtmlParser();
	// p.setUrl("http://218.241.67.233:9000/QxtSms/QxtFirewall?OperID=modoutg&OperPass=123456&DesMobile=15801412517&Content=%D6%D0%CE%C4%B6%CC%D0%C5abc&ContentType=15");
	// try {
	// String str = p.getSource();
	// System.out.println(str);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// public SMS() {
	//
	// }
	//
	/**
	 * 发送短信
	 * 
	 * @param telphone
	 * @param content
	 * @return
	 */
	public boolean sendMessage(String telphone, String content) {
		boolean result = true;
		try {
			HtmlParser p = new HtmlParser();
			content = URLEncoder.encode(content, "GBK");
			p.setUrl(urlPix + "&" + phoneName + "=" + telphone + "&" + contentName + "=" + content);
			String str = p.getSource();
			logger.debug(urlPix);
			logger.debug("telphone=" + telphone + "| msg=" + content);
			SAXReader saxReader = new SAXReader();
			Document document = null;
			document = saxReader.read(new ByteArrayInputStream(str.getBytes("GBK")));
			String codePath = "//response/code";
			Element element = (Element) document.selectNodes(codePath).get(0);
			if (element == null) {
				result = false;
			} else {
				int r = Integer.parseInt(element.getTextTrim());
				// 01,02,03成功
				if (r > 3) {
					result = false;
				}
			}
			document.clearContent();
			document = null;
			System.out.println(str);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
