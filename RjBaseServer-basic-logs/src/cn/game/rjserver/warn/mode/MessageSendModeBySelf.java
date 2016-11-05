/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.warn.mode;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.game.rjserver.basicutils.html.HtmlUrlConnection;
import cn.game.rjserver.log.GameEntityLogger;
import cn.game.rjserver.warn.Global;
import cn.game.rjserver.warn.WarnContext;
import cn.game.rjserver.warn.cmd.WarnCmdManager;

/**
 * @author wangzhifeng(rallen) 报警模式(自主记录)
 */
public class MessageSendModeBySelf extends MessageSendModeImpl {

	static GameEntityLogger logger = GameEntityLogger.getLog("warn");

	public MessageSendModeBySelf(WarnCmdManager warnManager) {
		super(warnManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean sendMessage(int warnLevel, int warnId, int mode, String content, boolean needAnalysis) {
		// TODO Auto-generated method stub
		logger.i(warnLevel + "\t" + warnId + "\t" + content);
		return false;
	}

	// 发送短信
	@Override
	public boolean sendMessageBySms(int warnLevel, int warnId, String content) {
		// TODO Auto-generated method stub

		boolean result = true;
		try {
			content = URLEncoder.encode(content, "GBK");
			String pts[] = Global.MANAGER_PHONE.split(",");
			for (String pt : pts) {
				String url = "http://218.241.67.233:9000/QxtSms/QxtFirewall?OperID=modoutg&OperPass=123456&DesMobile="
						+ pt + "&Content=" + content + "&ContentType=15";
				String html = HtmlUrlConnection.getHtml(url, "GBK");
				System.out.println(html);
				// logger.i("| msg=" + content);
				SAXReader saxReader = new SAXReader();
				Document document = null;
				document = saxReader.read(new ByteArrayInputStream(html.getBytes("GBK")));
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
				log.d("MessageSendModeBySelf：sendMessageBySms():" + html);
			}
			//
			this.warnManager.addSendedTimesRecord(warnId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean sendMessageByEmail(int warnLevel, int warnId, EmailEntity email, String content) {
		// TODO Auto-generated method stub
		return false;
	}

}
