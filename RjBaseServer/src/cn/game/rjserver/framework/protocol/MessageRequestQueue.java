/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 已经线程安全
 * 
 * @author Administrator
 * 
 */
public class MessageRequestQueue {
	private List<Message> messageList;

	private static MessageRequestQueue queue;

	public static synchronized MessageRequestQueue getInstance() {
		if (queue == null) {
			queue = new MessageRequestQueue();
		}
		return queue;
	}

	public MessageRequestQueue() {
		messageList = new Vector<Message>();
	}

	public synchronized void add(Message msg) {
		messageList.add(msg);
	}

	public int getCount() {
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
}
