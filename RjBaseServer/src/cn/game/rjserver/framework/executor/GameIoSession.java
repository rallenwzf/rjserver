/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.executor;

import java.util.Set;

/**
 * @author wangzhifeng(rallen)
 */
public interface GameIoSession {
	/**
	 * @param ag
	 *            发送
	 */
	public void write(Object obj);

	/**
	 * @return 接收
	 */
	public Object receive();

	public void regitReceiveListener(ReceiveListener listener);

	public void removeReceiveListener();

	public ReceiveListener getReceiveListener();

	/**
	 * 每次接受读取的字节长度，默认为请求的总长度
	 * 
	 * @param timesSize
	 */
	public void setTimesReceiveSize(int timesSize);

	public int getTimesReceiveSize();

	/**
	 * 此次业务连接标识编号
	 */
	public long getId();

	/**
	 * 会话id
	 */
	public String getSessionId();

	/**
	 * @param key
	 * @return
	 */
	Object getAttribute(Object key);

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	Object setAttribute(Object key, Object value);

	/**
	 * @param key
	 * @return
	 */
	Object removeAttribute(Object key);

	Set<Object> getAttributeKeys();

	/**
	 * @param key
	 * @return
	 */
	boolean containsAttribute(Object key);

	Object close(boolean immediately);

	short getCmdid();

	void setCmdid(short cmdid);

	public String getRemortAddress();
}
