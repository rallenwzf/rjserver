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
import cn.game.rjserver.log.LoggerConfig;
import cn.game.rjserver.warn.Global;
import cn.game.rjserver.warn.cmd.WarnCmdManager;

/**
 * @author wangzhifeng(rallen) 报警模式(主要应用中可重写)
 */
public class MessageSendModeByApi extends MessageSendModeImpl {
	// http://113.31.28.83:801/App/UserPasspostApp.aspx
	// http://113.31.28.83:7001/HiyouOffordGameServer/Monitor.do
	public int sendMessageApiErrorCount = 0;
	public int sendMessageApiEMSErrorCount = 0;
	public static boolean sendMessageApi = true;
	public static boolean sendMessageApiEMS = true;
	public static String MESSAGE_API = "http://192.168.0.101:8088/HiyouOffordGameServer/Monitor.do?op=AddWarnInfo";
	public static String SMS_API = "http://192.168.0.101:801/App/UserPasspostApp.aspx?op=sendmsg";
	public static String MESSAGE_API_CODE = "UTF-8";
	public static String SMS_API_CODE = "UTF-8";
	MessageSendModeBySelf self;

	public MessageSendModeByApi(WarnCmdManager warnManager) {
		super(warnManager);
		self = new MessageSendModeBySelf(warnManager);
		// TODO Auto-generated constructor stub
		load();
	}

	public static void load() {
		String str = LoggerConfig.getConfig("SendMode.MESSAGE_API");
		if (str != null && !str.equals("")) {
			MESSAGE_API = str;
		}
		str = LoggerConfig.getConfig("SendMode.SMS_API");
		if (str != null && !str.equals("")) {
			SMS_API = str;
		}
		str = LoggerConfig.getConfig("SendMode.MESSAGE_API_CODE");
		if (str != null && !str.equals("")) {
			MESSAGE_API_CODE = str;
		}
		str = LoggerConfig.getConfig("SendMode.SMS_API_CODE");
		if (str != null && !str.equals("")) {
			SMS_API_CODE = str;
		}
	}

	@Override
	public boolean dailyReset() {
		// TODO Auto-generated method stub
		sendMessageApi = true;
		sendMessageApiEMS = true;
		return super.dailyReset();
	}

	@Override
	public boolean sendMessage(int warnLevel, int warnId, int mode, String content, boolean needAnalysis) {
		// TODO Auto-generated method stub
		boolean result = true;
		try {
			String url = MESSAGE_API + "&ProjectID=" + Global.PROJECT_CODE + "&WarnMode=0&WarnLevel="
					+ warnLevel + "&WarnCmdId=" + warnId + "&WarnContent="
					+ URLEncoder.encode(content, MESSAGE_API_CODE) + "&ifTongji=" + (needAnalysis ? 1 : 0);
			String html = HtmlUrlConnection.getHtml(url, MESSAGE_API_CODE);
			System.out.println(html);

			SAXReader saxReader = new SAXReader();
			Document document = null;
			document = saxReader.read(new ByteArrayInputStream(html.getBytes("GBK")));
			String codePath = "//XML/ReturnEntity/ReturnResult";
			Element element = (Element) document.selectNodes(codePath).get(0);
			if (element == null) {
				result = false;
			} else {
				if (element.getTextTrim().equalsIgnoreCase("ok")) {
					result = true;
				} else {
					result = false;
				}
			}
			document.clearContent();
			document = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		if (result == false) {
			sendMessageApiErrorCount++;
			if (Global.PROJECT_SELF_WARN && sendMessageApi) {
				self.sendMessageBySms(warnLevel, warnId, "报警系统消息接收接口异常");
				sendMessageApiErrorCount = 0;
				sendMessageApi = false;
			}
			log.e("报警系统消息接收接口异常 count=" + sendMessageApiErrorCount);
		}
		return result;
	}

	// 发送短信
	@Override
	public boolean sendMessageBySms(int warnLevel, int warnId, String content) {
		// TODO Auto-generated method stub
		boolean result = true;
		try {
			// 做次只记录
			// this.sendMessage(warnLevel, warnId, Global.WARNMODE.MODE_SMS,
			// content, false);
			String url = SMS_API + "&SrcMobile=" + Global.MANAGER_PHONE + "&Content="
					+ URLEncoder.encode(content, SMS_API_CODE);
			String html = HtmlUrlConnection.getHtml(url, SMS_API_CODE);
			System.out.println("sendMessageBySms():" + html);

			SAXReader saxReader = new SAXReader();
			Document document = null;
			document = saxReader.read(new ByteArrayInputStream(html.getBytes("GBK")));
			String codePath = "//ReturnEntitys/ReturnEntity/State";
			Element element = (Element) document.selectNodes(codePath).get(0);
			if (element == null) {
				result = false;
			} else {
				if (element.getTextTrim().equalsIgnoreCase("ok")) {
					result = true;
				} else {
					result = false;
				}
			}
			document.clearContent();
			document = null;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		if (result == false) {
			sendMessageApiEMSErrorCount++;
			this.sendMessage(1, 0, 1, "报警系统短信发送接口异常", true);
			if (Global.PROJECT_SELF_WARN && sendMessageApiEMS) {
				self.sendMessageBySms(warnLevel, warnId, "报警系统短信发送接口异常");
				//
				sendMessageApiEMSErrorCount = 0;
				sendMessageApiEMS = false;
			}
			log.e("报警系统短信发送接口异常 count=" + sendMessageApiEMSErrorCount);
		}
		return true;
	}

	@Override
	public boolean sendMessageByEmail(int warnLevel, int warnId, EmailEntity email, String content) {
		// TODO Auto-generated method stub
		return false;
	}

}
