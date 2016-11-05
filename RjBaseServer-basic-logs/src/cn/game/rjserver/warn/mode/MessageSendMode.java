/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.warn.mode;

import cn.game.rjserver.log.MyLogger;
import cn.game.rjserver.warn.cmd.WarnInfo;

/**
 * @author wangzhifeng(rallen) 报警模式
 */
public interface MessageSendMode {
	static MyLogger log = MyLogger.getLog("mylog_warn");

	

	/**
	 * @param warnInfo
	 * @return
	 */
	public boolean sendMessage(WarnInfo warnInfo);

	/**
	 * @return
	 */
	public boolean dailyReset();
}
