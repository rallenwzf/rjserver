/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.warn;

import cn.game.rjserver.warn.cmd.WarnCmdManager;
import cn.game.rjserver.warn.mode.MessageSendMode;

/**
 * @author wangzhifeng(rallen) 上下文管理器
 */
public class WarnContext {
	public int projectCode;// 当前业务项目代号
	private MessageSendMode sendMode;
	private WarnCmdManager cmdManager;

	public WarnContext(int projectCode) {
		this.projectCode = projectCode;
	}

	public MessageSendMode getSendMode() {
		if (sendMode == null) {
			throw new RuntimeException("MessageSendMode is null!");
		}
		return sendMode;
	}

	public void setSendMode(MessageSendMode sendMode) {
		this.sendMode = sendMode;
	}

	public WarnCmdManager getCmdManager() {
		return cmdManager;
	}

	public void setCmdManager(WarnCmdManager cmdManager) {
		this.cmdManager = cmdManager;
	}

	public int getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(int projectCode) {
		this.projectCode = projectCode;
	}

}
