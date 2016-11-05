/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.mina.analyze.impl;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import cn.game.rjserver.basicutils.exception.ExceptionUtils;
import cn.game.rjserver.framework.UserManager;
import cn.game.rjserver.framework.executor.GameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Constants;
import cn.game.rjserver.framework.protocol.Header;
import cn.game.rjserver.framework.protocol.Property;
import cn.game.rjserver.framework.protocol.analyze.AgreementAnalyzeAbsimpl;
import cn.game.rjserver.framework.protocol.analyze.CommandFormatter;
import cn.game.rjserver.framework.protocol.analyze.ServerToCommand;
import cn.game.rjserver.log.MyLogger;

/**
 * 客户端
 * 
 * @author Administrator
 */
public class AgreementClientAnalyzeAdapter extends AgreementAnalyzeAbsimpl {

	static MyLogger log = MyLogger.getLog("agreement");

	@Override
	public Header decodeHead(Object bufObj, GameIoSession session) {
		// TODO Auto-generated method stub
		IoBuffer buf = (IoBuffer) bufObj;
		Header h = new Header();
		// int l = buf.limit();
		byte protocolVersion = buf.get();
		h.setProtocolVersion(protocolVersion);
		byte packgeHeaderLength = buf.get();
		h.setPackgeHeaderLength(packgeHeaderLength);
		short dataLength = buf.getShort();
		h.setDataLength(dataLength);
		// short appID = buf.getShort();
		// h.setAppID(appID);
		// byte modeID = buf.get();
		// h.setModeID(modeID);
		return h;
	}

	@Override
	public Agreement decodeBody(Agreement ag, Object bufObj, GameIoSession session) {
		// TODO Auto-generated method stub
		IoBuffer buf = (IoBuffer) bufObj;
		ag.setServer(true);// 服务器协议
		ag.setCmdId((short) -1);
		// 读消息头
		try {
			String uid = "0";
			if (session != null && session.getAttribute(UserManager.SESSION_KEY_UTID) != null) {
				uid = session.getAttribute(UserManager.SESSION_KEY_UTID).toString();
			}
			short cmdID = buf.getShort();
			ag.setCmdId(cmdID);
			session.setCmdid(cmdID);
			// 过滤测试协议
			// if (cmdID == Command.test.test) {
			// // 接到客户端心跳检测，即可回复心跳检测
			// try {
			// log.i("uid=" + uid + " 读：" + "cmdID=[" + cmdID + "]");
			// } catch (Exception e) {
			// }
			// } else {
			boolean b = false;
			String logstr = "";
			for (CommandFormatter ct : this.getCommandFormatters()) {
				if (ct.accept(cmdID)) {
					ag = ct.decodeBody(buf, ag);
					logstr = "uid=" + uid + " |sid=" + session.getId() + " 读：";
					b = true;
					break;
					// return ag;
				}
			}
			if (!b) {
				logger.debug("未查找到CommandTransform cmdid=" + cmdID);
				log.i("read error：" + ag.toString());
			} else {
				if (ag.getHeader().getModeID() == 10) {
					session.setAttribute("IsManagerAG", 1);
					try {
						long serNum = buf.getLong();
						ag.setSerialNumber(serNum + "");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				log.i(logstr + ag.toString());
			}
			// }
			// 设置为读取完成，防止mina buf循环
			// buf.position(buf.limit());
		} catch (Exception e) {
			logger.error("[cmdid=" + ag.getCmdId() + "] " + ExceptionUtils.getTrace(e));
		}
		return ag;
	}

	@Override
	public Agreement decode(Object bufObj, GameIoSession session) {
		// TODO Auto-generated method stub
		IoBuffer buf = (IoBuffer) bufObj;
		Agreement ag = new Agreement();
		// Agreement.SHOW_HEADER_LOG=true;
		ag.setServer(true);// 服务器协议
		ag.setCmdId((short) -1);
		// 读消息头
		try {

			String uid = "0";
			if (session != null && session.getAttribute(UserManager.SESSION_KEY_UTID) != null) {
				uid = session.getAttribute(UserManager.SESSION_KEY_UTID).toString();
			}
			Header h = new Header();
			// int l = buf.limit();
			byte protocolVersion = buf.get();
			h.setProtocolVersion(protocolVersion);
			byte packgeHeaderLength = buf.get();
			h.setPackgeHeaderLength(packgeHeaderLength);
			short dataLength = buf.getShort();
			h.setDataLength(dataLength);
			// short appID = buf.getShort();
			// h.setAppID(appID);
			// byte modeID = buf.get();
			// h.setModeID(modeID);

			// 读消息体
			ag.setHeader(h);
			short cmdID = buf.getShort();
			ag.setCmdId(cmdID);

			session.setCmdid(cmdID);
			// 过滤测试协议
			// if (cmdID == Command.test.test) {
			// // 接到客户端心跳检测，即可回复心跳检测
			// try {
			// log.i("uid=" + uid + " 读：" + "cmdID=[" + cmdID + "]");
			// } catch (Exception e) {
			// }
			// } else {
			String logstr = "";
			boolean b = false;
			for (CommandFormatter ct : this.getCommandFormatters()) {
				if (ct.accept(cmdID)) {
					ag = ct.decodeBody(buf, ag);
					logstr = "uid=" + uid + " |sid=" + session.getId() + " 读：";
					b = true;
					break;
					// return ag;
				}
			}
			if (!b) {
				logger.debug("未查找到CommandTransform cmdid=" + cmdID);
				log.i("read error：" + ag.toString());
			} else {
				if (ag.getHeader().getModeID() == 10) {
					session.setAttribute("IsManagerAG", 1);
					try {
						long serNum = buf.getLong();
						ag.setSerialNumber(serNum + "");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				log.i(logstr + ag.toString());
			}
			// }
			// 设置为读取完成，防止mina buf循环
			// buf.position(buf.limit());
		} catch (Exception e) {
			logger.error("[cmdid=" + ag.getCmdId() + "] " + ExceptionUtils.getTrace(e));
		}
		return ag;
	}

	@Override
	public void encode(Agreement ag, Object bufObj, GameIoSession session) {
		// TODO Auto-generated method stub
		IoBuffer buf = (IoBuffer) bufObj;
		// 包头
		try {
			String uid = "0";
			if (session != null && session.getAttribute(UserManager.SESSION_KEY_UTID) != null) {
				uid = session.getAttribute(UserManager.SESSION_KEY_UTID).toString();
			}
			ag.setServer(false);// 客户端协议，发送给服务器
			Header h = ag.getHeader();
			if (h == null) {
				h = ServerToCommand.getInstance().getDefaultClientHeader();
				ag.setHeader(h);
			}
			if (h.getModeID() == 10) {
				//
				ag.getHeader().setModeID((byte) 10);
				if (ag.getAttrsList() != null) {
					ag.getAttrsList().add(new Property(Constants.DATATYPE_LONG, ag.getSerialNumber()));
				}
			}
			buf.put(h.getProtocolVersion());// 协议的版本
			buf.put(h.getPackgeHeaderLength());
			// 信息头+消息体
			short s = (short) (h.getPackgeHeaderLength() + Constants.CLIENTREQUEST_CONTENT_HEADER_LENGTH + ag.getDataLength(true));
			h.setDataLength(s);
			buf.putShort(s);// 数据包长度....

			buf.putShort(h.getAppID());
			buf.put(h.getModeID());
			// buf.put((byte) ag.getDataNum());
			// buf.put(ag.getCmdType());
			// // 消息体长度,包含指令号与数据体的长度。
			// short ts = (short) (ag.getDataLength(false) + 2);
			// buf.putShort(ts);
			// 信息体
			short cid = ag.getCmdId();
			buf.putShort(cid);
			// 数据体
			if (ag.getAttrsList() != null) {
				for (int i = 0; i < ag.getAttrsList().size(); i++) {
					Property p = ag.getAttrsList().get(i);
					PropertyUtils.encodeProperty(p, buf);
				}
			}
			// //将缓存的位置设为位置
			// buf.limit(buf.position());
			// //将缓存的位置设为0
			// buf.position(0);
			log.i("utid=" + uid + " |sid=" + session.getId() + " 写：" + ag.toString());

		} catch (Exception e) {
			logger.error(ExceptionUtils.getTrace(e));
		}

	}

}
