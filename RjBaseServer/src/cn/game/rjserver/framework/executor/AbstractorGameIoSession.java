/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.executor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author wangzhifeng(rallen)
 */
public abstract class AbstractorGameIoSession implements GameIoSession {
	public static String ATTR_KEY_CMDID = "cmdid";
	protected Map<Object, Object> attributes = new HashMap<Object, Object>();
	protected long id;
	protected ReceiveListener receiveListener;
	protected int timesReceiveSize = 1024;
	protected boolean s2cIsGzip;
	protected boolean c2sIsGzip;
	/**
	 * 需要业务处理的actionManager等待协议组处理完成后，调用
	 */
	protected boolean isGroup;

	public boolean isS2cIsGzip() {
		return s2cIsGzip;
	}

	public void setS2cIsGzip(boolean s2cIsGzip) {
		this.s2cIsGzip = s2cIsGzip;
	}

	public boolean isC2sIsGzip() {
		return c2sIsGzip;
	}

	public void setC2sIsGzip(boolean c2sIsGzip) {
		this.c2sIsGzip = c2sIsGzip;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public AbstractorGameIoSession() {
		id = new Random().nextLong();
	}

	@Override
	public void regitReceiveListener(ReceiveListener listener) {
		// TODO Auto-generated method stub
		receiveListener = listener;
	}

	@Override
	public void removeReceiveListener() {
		// TODO Auto-generated method stub
		receiveListener = null;
	}

	@Override
	public ReceiveListener getReceiveListener() {
		// TODO Auto-generated method stub
		return receiveListener;
	}

	@Override
	public void setTimesReceiveSize(int timesSize) {
		// TODO Auto-generated method stub
		timesReceiveSize = timesSize;
	}

	@Override
	public int getTimesReceiveSize() {
		// TODO Auto-generated method stub
		return timesReceiveSize;
	}

	@Override
	public abstract void write(Object obj);

	@Override
	public abstract Object receive();

	@Override
	public short getCmdid() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCmdid(short cmdid) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getSessionId() {
		// TODO Auto-generated method stub
		return id + "";
	}

	@Override
	public Object getAttribute(Object key) {
		// TODO Auto-generated method stub
		return attributes.get(key);
	}

	@Override
	public Object setAttribute(Object key, Object value) {
		// TODO Auto-generated method stub
		return attributes.put(key, value);
	}

	@Override
	public Object removeAttribute(Object key) {
		// TODO Auto-generated method stub
		return attributes.remove(key);
	}

	@Override
	public boolean containsAttribute(Object key) {
		// TODO Auto-generated method stub
		return attributes.containsKey(key);
	}

	@Override
	public Object close(boolean immediately) {
		// TODO Auto-generated method stub
		return null;
	}

}
