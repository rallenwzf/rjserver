/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.utils.sms;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.tags.BulletList;

import cn.game.rjserver.utils.sms.htmlparser.HtmlParser;
import cn.game.rjserver.utils.sms.htmlparser.ParserTool;


/**
 * @author Administrator
 * 
 */
public class ClientIpAddressParser {
	private static ClientIpAddressParser ipParser;
	public Vector<String> ipList = new Vector<String>();
	private Thread thread = null;
	private IpAddressCallback callback;
	private static HtmlParser htmlParser = new HtmlParser();

	public static ClientIpAddressParser getInstatnce() {
		if (ipParser == null) {
			ipParser = new ClientIpAddressParser();
		}
		return ipParser;
	}

	public static void setIpParser(ClientIpAddressParser ipParser) {
		ClientIpAddressParser.ipParser = ipParser;
	}

	public ClientIpAddressParser() {

	}

	public ClientIpAddressParser(IpAddressCallback callback) {
		this.callback = callback;
	}

	public void addIp(String ip) {
		if (ip != null) {
			ipList.add(ip);
		}
	}

	public void remove(String ip) {
		ipList.remove(ip);
	}

	public void start() {
		if (callback == null) {
			throw new RuntimeException();
		}
		if (thread == null) {
			thread = new MyThread();
			thread.start();
		}
	}

	private class MyThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				if (ipList.size() == 0) {
					sleep(4 * 1000);
				} else {
					String ip = ipList.remove(0);
					if (callback.checkIp(ip)) {
						callback.parserIpAddress(ip, parseHtmlIpaddr(ip));
					}
					sleep(2 * 1000);
				}
			} catch (Exception e) {
			}
		}
	}

	public IpAddressCallback getCallback() {
		return callback;
	}

	public void setCallback(IpAddressCallback callback) {
		this.callback = callback;
	}

	public String parseHtmlIpaddr(String ip) {
		htmlParser.setUrl("http://www.ip138.com/ips8.asp?ip=" + ip
				+ "&action=2");
		try {
			System.out.println("http://www.ip138.com/ips8.asp?ip=" + ip
					+ "&action=2");
			String html = htmlParser.getSource();
			Node node = ParserTool.getHtmlElement(html, BulletList.class,
					"class", "ul1");
			// NodeList nl=node.getChildren();
			// for(int i=0;i<nl.size();i++){
			// System.out.println(nl.elementAt(i).toPlainTextString().split("：")[1]);
			// }
			String addr = node.getFirstChild().toPlainTextString();
			if (addr.split("：").length > 1) {
				addr = addr.split("：")[1];
			}
			return addr;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
