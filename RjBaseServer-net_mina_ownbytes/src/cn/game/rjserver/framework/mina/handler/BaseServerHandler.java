package cn.game.rjserver.framework.mina.handler;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.transport.socket.SocketSessionConfig;

import cn.game.rjserver.framework.UserManager;
import cn.game.rjserver.framework.mina.MinaGameIoSession;

/**
 * @doc
 * @author Administrator
 * 
 */
public class BaseServerHandler extends IoHandlerAdapter {
	static Logger logger = Logger.getLogger(BaseServerHandler.class.getName());

	public BaseServerHandler() {

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
		IoSessionConfig cfg = session.getConfig();
		if (cfg instanceof SocketSessionConfig) {
			((SocketSessionConfig) cfg).setReceiveBufferSize(2048);
			((SocketSessionConfig) cfg).setReadBufferSize(2048);
			((SocketSessionConfig) cfg).setKeepAlive(true);
			((SocketSessionConfig) cfg).setSoLinger(0); // 这个是根本解决调用关闭时连接未被断开问题的设置
			// this.getAcceptor().getSessionConfig().
			cfg.setIdleTime(IdleStatus.BOTH_IDLE, 30);
			cfg.setWriteTimeout(30);// 设置写超时，期望尽快得知客户端是否断线
			cfg.setReaderIdleTime(30);
			((SocketSessionConfig) cfg).setOobInline(true);
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
		session.close(false);
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		String str = message.toString();
		System.out.println("Message read:" + str);
		Date date = new Date();
		session.write(date.toString());
		System.out.println("Message written...");
		try {
			String user = (String) session.getAttribute("user");
			if (user != null && !user.equals("") && !UserManager.users.contains(user)) {
				UserManager.getInstance().addUser(new MinaGameIoSession(session), user, "account");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// logger.warn("sessionIdle IDLE ：" + session.getIdleCount(status));
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		UserManager.getInstance().removeUser(new MinaGameIoSession(session));
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}

}
