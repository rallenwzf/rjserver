package cn.game.rjserver.framework.response.group.action.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.game.rjserver.basicutils.exception.ExceptionUtils;
import cn.game.rjserver.framework.UserManager;
import cn.game.rjserver.framework.executor.GameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Message;
import cn.game.rjserver.framework.protocol.MessageRequestQueue;
import cn.game.rjserver.framework.response.group.ResponseAction;
import cn.game.rjserver.framework.response.group.ResponseActionAdapter;
import cn.game.rjserver.framework.response.group.ResponseActionManager;

/**
 * 
 * <li>接收到得消息队列的处理</li>
 */
public class RequestMessageThreadAction extends ResponseActionAdapter implements Runnable {
	static Logger logger = Logger.getLogger(RequestMessageThreadAction.class.getName());

	ResponseActionManager actionManager;

	/**
	 * 取请求队列扔给EventAction做分配处理
	 * 
	 * @param actionManager
	 */
	public RequestMessageThreadAction(ResponseActionManager actionManager) {
		//
		this.actionManager = actionManager;
	}

	public void run() {

		long m_longTimeStamp = 0;

		while (true) {
			m_longTimeStamp = System.currentTimeMillis();
			int nMsgCount = 0;
			List<Message> listMessages = MessageRequestQueue.getInstance().get(10);
			if (listMessages.size() > 0) {
				for (int ii = 0; ii < listMessages.size(); ii++) {
					try {
						dowithEachMessage(listMessages.get(ii));

					} catch (Exception e) {
						logger.error(ExceptionUtils.getTrace(e));
					}
					nMsgCount++;
				}

			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void dowithEachMessage(Message msg) {
		//
		GameIoSession session = null;
		if (msg.getSession() != null) {
			session = msg.getSession();
		} else {
			String key = msg.getSessionKey();
			session = UserManager.getInstance().getUserSession(key);
		}
		if (session != null) {
			try {
				Agreement ag = (Agreement) msg.getContent();
				ResponseAction action = actionManager.findAction(ag);
				if (action != null) {
					logger.debug("收到消息开始处理：{sessionID=" + session.getId() + "} {date}=" + new Date().getTime() + " {CMD}=" + ag.getCmdId() + " {action}=" + action.getClass().getSimpleName());
					action.messageReceived(session, ag);
				} else {
					logger.debug("CmdID:[" + ag.getCmdId() + "]未查找到处理该协议的action");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			logger.error("msg.getPlayId() is null");
		}
	}
}
