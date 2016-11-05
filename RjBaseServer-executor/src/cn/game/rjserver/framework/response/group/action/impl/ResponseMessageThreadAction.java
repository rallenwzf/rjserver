package cn.game.rjserver.framework.response.group.action.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.game.rjserver.basicutils.exception.ExceptionUtils;
import cn.game.rjserver.framework.UserManager;
import cn.game.rjserver.framework.executor.GameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Message;
import cn.game.rjserver.framework.protocol.MessageResponseQueue;
import cn.game.rjserver.framework.response.group.ResponseActionAdapter;

/**
 * 
 * <li>对需响应的信息队列的发送处理</li>
 */
public class ResponseMessageThreadAction extends ResponseActionAdapter implements Runnable {
	static Logger logger = Logger.getLogger(ResponseMessageThreadAction.class.getName());

	public ResponseMessageThreadAction() {
		//
	}

	public void run() {

		long m_longTimeStamp = 0;

		while (true) {
			m_longTimeStamp = System.currentTimeMillis();
			int nMsgCount = 0;
			List<Message> listMessages = MessageResponseQueue.getInstance().get(10);
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
		try {
			String key = msg.getSessionKey();
			if (key != null) {
				GameIoSession s = UserManager.getInstance().getUserSession(key);
				this.messageCallbacked(s, (Agreement) msg.getContent());
			} else {
				logger.error("msg.getSessionKey() is null");
			}
		} catch (Exception e) {
			logger.debug(e);
		}

	}

}
