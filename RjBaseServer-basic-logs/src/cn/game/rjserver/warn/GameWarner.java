/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.warn;

import cn.game.rjserver.warn.cmd.WarnCmdManager;
import cn.game.rjserver.warn.cmd.WarnInfo;
import cn.game.rjserver.warn.mode.MessageSendModeByApi;

/**
 * @author wangzhifeng(rallen)
 */
public class GameWarner {
	private static GameWarner warner;
	private static WarnContext context;
	private static boolean isOpen = false;

	public synchronized static GameWarner getWarner() {
		if (warner == null) {
			warner = new GameWarner();
		}
		return warner;
	}

	public static WarnContext getContext() {
		if (context == null) {
			context = new WarnContext(Global.PROJECT_CODE);
			context.setCmdManager(new WarnCmdManager());
			context.setSendMode(new MessageSendModeByApi(context
					.getCmdManager()));
		}
		return context;
	}

	public static void setContext(WarnContext context) {
		GameWarner.context = context;
	}

	/**
	 * 
	 */
	public void dailyReset() {
		getContext().getCmdManager().clear();
		getContext().getSendMode().dailyReset();
	}

	public static boolean isOpen() {
		return isOpen;
	}

	public static void setOpen(boolean isOpen) {
		GameWarner.isOpen = isOpen;
	}

	/**
	 * 预警
	 * 
	 * @param warnCmdId
	 */
	public void warn(int warnCmdId) {
		if (!isOpen)
			return;

		try {

			getContext().getSendMode().sendMessage(
					getContext().getCmdManager().findWarnInfo(warnCmdId));
		} catch (Exception e) {
		}
	}

	/**
	 * 预警
	 * 
	 * @param warnCmdId
	 * @param note
	 *            备注
	 */
	public void warn(int warnCmdId, String note) {
		if (!isOpen)
			return;

		try {
			WarnInfo warnInfo = getContext().getCmdManager().findWarnInfo(
					warnCmdId);
			if (warnInfo != null) {
				if (note != null) {
					warnInfo.setContent(warnInfo.getContent() + " |" + note);
				}
				getContext().getSendMode().sendMessage(warnInfo);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 预警
	 * 
	 * @param warnCmdId
	 * @param note
	 *            备注
	 */
	public void warn(int warnCmdId, String note, boolean noTotal) {
		if (!isOpen)
			return;

		try {
			WarnInfo warnInfo = getContext().getCmdManager().findWarnInfo(
					warnCmdId);
			if (warnInfo != null) {
				if (note != null) {
					warnInfo.setContent(warnInfo.getContent() + " |" + note);
				}
				warnInfo.setNeedAnalysis(noTotal);
				getContext().getSendMode().sendMessage(warnInfo);
			}
		} catch (Exception e) {
		}
	}
}
