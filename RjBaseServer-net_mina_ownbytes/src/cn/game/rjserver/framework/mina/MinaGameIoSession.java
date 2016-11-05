/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.mina;

import java.net.InetSocketAddress;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import cn.game.rjserver.framework.executor.AbstractorGameIoSession;
import cn.game.rjserver.framework.executor.ReceiveListener;

/**
 * @author wangzhifeng(rallen)
 */
public class MinaGameIoSession extends AbstractorGameIoSession {
	private IoSession session;

	public MinaGameIoSession(IoSession session) {
		this.session = session;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	@Override
	public void write(Object obj) {
		// TODO Auto-generated method stub
		session.write(obj);
	}

	@Override
	public Object receive() {
		// TODO Auto-generated method stub
		System.out.println("BaseServerHandler messageReceived 得到接受数据");
		return null;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return session.getId();
	}

	@Override
	public Object getAttribute(Object key) {
		// TODO Auto-generated method stub
		return session.getAttribute(key);
	}

	@Override
	public Object setAttribute(Object key, Object value) {
		// TODO Auto-generated method stub
		return session.setAttribute(key, value);
	}

	@Override
	public Object removeAttribute(Object key) {
		// TODO Auto-generated method stub
		return session.removeAttribute(key);
	}

	@Override
	public boolean containsAttribute(Object key) {
		// TODO Auto-generated method stub
		return session.containsAttribute(key);
	}

	@Override
	public short getCmdid() {
		// TODO Auto-generated method stub
		Object obj = getAttribute(ATTR_KEY_CMDID);
		if (obj != null) {
			return Short.parseShort(obj.toString());
		}
		return -1;
	}

	@Override
	public void setCmdid(short cmdid) {
		// TODO Auto-generated method stub
		setAttribute(ATTR_KEY_CMDID, cmdid);
	}

	@Override
	public Object close(boolean immediately) {
		// TODO Auto-generated method stub
		return session.close(immediately);
	}

	@Override
	public String getRemortAddress() {
		// TODO Auto-generated method stub
		InetSocketAddress sa = (InetSocketAddress) session.getRemoteAddress();
		// System.out.println("session.getLocalAddress() :"+session.getLocalAddress().getClass().getName());
		return sa.getAddress().getHostAddress();
	}

	@Override
	public Set<Object> getAttributeKeys() {
		// TODO Auto-generated method stub
		return session.getAttributeKeys();
	}

}
