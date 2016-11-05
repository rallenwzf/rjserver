/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol.analyze;

import org.apache.log4j.Logger;

import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Constants;
import cn.game.rjserver.framework.protocol.Header;
import cn.game.rjserver.framework.protocol.Property;

/**
 * @author Administrator
 */
public class ServerToCommand {
	public static final int succss = 0;
	public static final int fail = 1;
	private static ServerToCommand stc;
	public static Logger logger = Logger.getLogger(ServerToCommand.class.getName());

	public static ServerToCommand getInstance() {
		if (stc == null) {
			stc = new ServerToCommand();
		}
		return stc;
	}

	public Header getDefaultHeader() {
		Header h = new Header();
		h.setProtocolVersion((byte) 0);
		h.setPackgeHeaderLength((byte) Constants.SERVERREPONSE_HEAD_LENGTH);
		return h;
	}

	public Agreement setDefaultSuccssCommand(Agreement ag) {
		ag.addAttr(new Property(Constants.DATATYPE_BYTE, succss));
		return ag;
	}

	public Agreement setDefaultFailCommand(Agreement ag, int code, String str) {
		ag.addAttr(new Property(Constants.DATATYPE_BYTE, fail));
		ag.addAttr(new Property(Constants.DATATYPE_BYTE, code));
		ag.addAttr(new Property(Constants.DATATYPE_STRING, str == null ? "失败" : str));
		if (code == 0) {
			// 异常
			logger.error("出现异常client ag=" + ag.toString());
		}
		return ag;
	}

	public Agreement getDefaultNewFailCommand(Agreement agr, int code, String str) {
		Agreement ag = new Agreement();
		ag.setCmdId(agr.getCmdId());
		ag.addAttr(new Property(Constants.DATATYPE_BYTE, fail));
		ag.addAttr(new Property(Constants.DATATYPE_BYTE, code));
		ag.addAttr(new Property(Constants.DATATYPE_STRING, str == null ? "失败" : str));
		if (code == -1) {
			// 异常
			// logger.error("出现异常client ag=" + ag.toString());
		}
		return ag;
	}

	public Agreement getDefaultNewFailCommand(Agreement agr, int resultCode, int code, String str) {
		Agreement ag = new Agreement();
		ag.setCmdId(agr.getCmdId());
		ag.addAttr(new Property(Constants.DATATYPE_BYTE, resultCode));
		ag.addAttr(new Property(Constants.DATATYPE_BYTE, code));
		ag.addAttr(new Property(Constants.DATATYPE_STRING, str == null ? "失败" : str));
		if (code == -1) {
			// 异常
			logger.error("出现异常client ag=" + ag.toString());
		}
		return ag;
	}

	public Agreement getDefaultNewFailCommand(Agreement agr, String str) {
		return this.getDefaultNewFailCommand(agr, 0, str);
	}

	public Header getDefaultClientHeader() {
		Header h = new Header();
		h.setProtocolVersion((byte) 0);
		h.setPackgeHeaderLength((byte) Constants.CLIENTREQUEST_HEAD_LENGTH);
		h.setAppID((short) 0);
		h.setModeID((byte) 0);
		return h;
	}
}
