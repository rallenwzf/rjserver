/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.warn.cmd;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import cn.game.rjserver.log.MyLogger;
import cn.game.rjserver.warn.Global;
import cn.game.rjserver.warn.util.xml.WarnCommandXml;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public class WarnCmdManager {
	public static String WARN_COMMAND_XML = System.getProperty("user.dir")
			+ "/file.logAndWarn.config/WarnCommand.xml";
	public static int BADWARN_DAY_TIMES = 3;
	MyLogger logger = MyLogger.getLog("LogAndWarn");
	
	public Map<Integer, WarnInfo> warnMap = Collections
			.synchronizedMap(new HashMap<Integer, WarnInfo>());
	public Map<Integer, Integer> sendedTimesMap = Collections
			.synchronizedMap(new HashMap<Integer, Integer>());// 已发短信记录

	public WarnCmdManager() {
		loadWarnCommandXml(null);
	}

	/**
	 * @param filePath
	 */
	public void loadWarnCommandXml(String filePath) {
		if (filePath == null || !filePath.equals("")) {
			filePath = WARN_COMMAND_XML;
		}
		WarnCommandXml xml = new WarnCommandXml();
		xml.setXmlFilePath(filePath);
		List<Element> elements;
		try {
			elements = xml.getDocument().selectNodes("//root/warnList");
			if (elements != null && elements.size() > 0) {
				if (elements != null) {
					for (int i = 0; i < elements.size(); i++) {
						Element el = elements.get(i);
						try {
							int warnLevel = Integer.parseInt(el
									.attributeValue("level"));
							List<Element> warns = el.selectNodes("warn");
							if (warns != null) {
								for (int j = 0; j < warns.size(); j++) {
									try {
										Element warn = warns.get(j);
										int warnId = Integer.parseInt(warn
												.attributeValue("warnId"));
										String content = warn
												.attributeValue("content");
										WarnInfo warnInfo = new WarnInfo();
										warnInfo.setWarnLevel(warnLevel);
										warnInfo.setContent(content);
										warnInfo.setWarnId(warnId);
										warnMap.put(warnId, warnInfo);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							// System.out.println(el.asXML());
							// System.out
							// .println("**********************************");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Map<Integer, WarnInfo> getWarnMap() {
		return warnMap;
	}

	public Map<Integer, Integer> getSendedTimesMap() {
		return sendedTimesMap;
	}

	public void clear() {
		sendedTimesMap.clear();
	}

	public synchronized void addSendedTimesRecord(int warnId) {
		if (this.sendedTimesMap.containsKey(warnId)) {
			this.sendedTimesMap
					.put(warnId, this.sendedTimesMap.get(warnId) + 1);
		} else {
			this.sendedTimesMap.put(warnId, 1);
		}
	}

	/**
	 * @param warnCmdId
	 * @return
	 */
	public WarnInfo findWarnInfo(int warnCmdId) {
		WarnInfo warnInfo = warnMap.get(warnCmdId);
		if (warnInfo == null) {
			logger.d("warnCmdId=" + warnCmdId + " 此预警代号不存在");
			return null;
		} else {
			warnInfo = warnInfo.clone();
			if (warnInfo != null) {
				// mode
				if (warnInfo.getWarnLevel() == 1) {
					if (sendedTimesMap.containsKey(warnInfo.getWarnId())
							&& sendedTimesMap.get(warnInfo.getWarnId()) >= BADWARN_DAY_TIMES) {
						warnInfo.setMode(Global.WARNMODE.MODE_BASIC);
					} else {
						warnInfo.setMode(Global.WARNMODE.MODE_SMS);
					}
				} else {
					warnInfo.setMode(Global.WARNMODE.MODE_BASIC);
				}
				// needAnalysis
				if (warnInfo.getWarnLevel() == 1) {
					warnInfo.setNeedAnalysis(false);
				} else {
					if (warnInfo.getWarnLevel() > 4) {
						warnInfo.setNeedAnalysis(false);
					} else {
						warnInfo.setNeedAnalysis(true);
					}
				}
			}
			return warnInfo;
		}

	}

	public static void main(String[] args) {
		WarnCmdManager m = new WarnCmdManager();
		System.out.println(m.getWarnMap().size());
	}
}
