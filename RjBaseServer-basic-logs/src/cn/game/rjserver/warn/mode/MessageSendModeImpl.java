/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.warn.mode;

import cn.game.rjserver.warn.Global;
import cn.game.rjserver.warn.WarnContext;
import cn.game.rjserver.warn.cmd.WarnCmdManager;
import cn.game.rjserver.warn.cmd.WarnInfo;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public class MessageSendModeImpl implements MessageSendMode {
	WarnCmdManager warnManager;

	public MessageSendModeImpl(WarnCmdManager warnManager) {
		this.warnManager = warnManager;
	}

	@Override
	public boolean sendMessage(WarnInfo warnInfo) {
		// TODO Auto-generated method stub
		log.d(warnInfo.getWarnId() + "____" + warnInfo.getContent());
		if (warnInfo.getMode() == Global.WARNMODE.MODE_BASIC) {
			this.sendMessage(warnInfo.getWarnLevel(), warnInfo.getWarnId(),
					warnInfo.getMode(), warnInfo.getContent(),
					warnInfo.isNeedAnalysis());
		} else if (warnInfo.getMode() == Global.WARNMODE.MODE_SMS) {
			boolean b = this.sendMessageBySms(warnInfo.getWarnLevel(),
					warnInfo.getWarnId(), warnInfo.getContent());
			// 记录已发生次数
			this.warnManager.addSendedTimesRecord(warnInfo.getWarnId());
		} else if (warnInfo.getMode() == Global.WARNMODE.MODE_EMAIL) {
			this.sendMessageByEmail(warnInfo.getWarnLevel(),
					warnInfo.getWarnId(), Global.getEmailEntity(),
					warnInfo.getContent());
		}
		return true;
	}

	@Override
	public boolean dailyReset() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 普通推送
	 * 
	 * @param warnLevel
	 * @param warnId
	 * @param content
	 * @param needAnalysis
	 * @return
	 */
	public boolean sendMessage(int warnLevel, int warnId, int mode,
			String content, boolean needAnalysis) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 直接推送短信
	 * 
	 * @param warnLevel
	 * @param warnId
	 * @param content
	 * @return
	 */
	public boolean sendMessageBySms(int warnLevel, int warnId, String content) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * email模式推送
	 * 
	 * @param warnLevel
	 * @param warnId
	 * @param email
	 * @param content
	 * @return
	 */
	public boolean sendMessageByEmail(int warnLevel, int warnId,
			EmailEntity email, String content) {
		// TODO Auto-generated method stub
		return false;
	}

}
