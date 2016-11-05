/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Vector;

import cn.game.rjserver.framework.SystemTask;
import cn.game.rjserver.framework.UserManager;
import cn.game.rjserver.framework.executor.GameIoSession;

/**
 * @author Administrator
 */
public class MessageResponseQueue {
	private Vector<Message> messageList;
	private static MessageResponseQueue queue;

	public static synchronized MessageResponseQueue getInstance() {
		if (queue == null) {
			queue = new MessageResponseQueue();
		}
		return queue;
	}

	public MessageResponseQueue() {
		messageList = new Vector<Message>();
	}

	public void add(Message msg) {
		messageList.add(msg);
	}

	// 立即发送
	public void add(Message msg, boolean nodelay) {
		boolean b = false;
		try {
			if (nodelay) {
				String key = msg.getSessionKey();
				if (key != null) {
					GameIoSession s = UserManager.getInstance().getUserSession(key);
					s.write(msg.getContent());
					b = true;
				} else {
					if (msg.getSession() != null) {
						msg.getSession().write(msg.getContent());
						b = true;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (!b) {
			messageList.add(msg);
		}
	}

	public int size() {
		return messageList.size();
	}

	public synchronized List<Message> get(int count) {
		List<Message> listReturn = new Vector<Message>();
		int nLength = messageList.size();
		int nTemp = 0;
		if (nLength > 0) {
			// 取出后删除
			nTemp = (count >= nLength) ? nLength : count;
			for (int i = 0; i < nTemp; i++) {
				listReturn.add(messageList.remove(0));
			}
		}
		return listReturn;
	}

	/**
	 * 延时多长时间后发送
	 * 
	 * @param messageList
	 * @param delayMills
	 */
	public void delaySend(final List<Message> messageList, long delayMills) {
		System.out.println("延时发送协议》》》》》》");
		SystemTask task = new SystemTask();
		task.setTimerTask(new MyTimerTask(task, messageList));
		task.taskRun(delayMills);
	}

	class MyTimerTask extends TimerTask {
		SystemTask task;
		List<Message> msgList;

		public MyTimerTask(SystemTask task, List<Message> msgList) {
			this.task = task;
			this.msgList = msgList;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < msgList.size(); i++) {
				Message msg = msgList.get(i);
				try {
					String key = msg.getSessionKey();
					if (key != null) {
						GameIoSession s = UserManager.getInstance().getUserSession(key);
						s.write(msg.getContent());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.cancel();
			task.stop();
			task = null;
		}
	}
}
