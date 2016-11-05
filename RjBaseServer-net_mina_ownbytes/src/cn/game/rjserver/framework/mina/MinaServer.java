/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import cn.game.rjserver.framework.configuration.Configuration;
import cn.game.rjserver.framework.configuration.ServerConfiguration;
import cn.game.rjserver.framework.mina.handler.BaseServerHandler;

/**
 * @author Administrator
 */
public class MinaServer {
	static Logger logger = Logger.getLogger(MinaServer.class.getName());
	private IoAcceptor acceptor;
	private IoHandler handler;
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;
	private int port = 9999;
	private String host = "127.0.0.1";

	public MinaServer() {

	}

	public MinaServer(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start(boolean b) {
		// 配置
		this.getAcceptor().setHandler(this.getHandler());
		// SocketSessionConfig cfg = (SocketSessionConfig)
		// this.getAcceptor().getSessionConfig();
		// cfg.setReadBufferSize(102400);
		// cfg.setKeepAlive(true);
		// cfg.setSoLinger(0); // 这个是根本解决调用关闭时连接未被断开问题的设置
		// // this.getAcceptor().getSessionConfig().
		// cfg.setIdleTime(IdleStatus.BOTH_IDLE, 10);

		// 启动
		try {
			if (this.getHost() == null) {
				this.getAcceptor().bind(new InetSocketAddress(this.getPort()));
			} else {
				this.getAcceptor().bind(new InetSocketAddress(this.getHost(), this.getPort()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("服务器启动...." + this.getHost() + ":" + this.getPort());
	}

	public void start() {
		this.filter();
		start(true);
	}

	public void stop() {
		this.getAcceptor().unbind();
	}

	/**
	 * Acceptor设置相关filter
	 */
	public void filter() {

		LoggingFilter filter = new LoggingFilter();
		// filter.setExceptionCaughtLogLevel(LogLevel.DEBUG);
		// filter.setMessageReceivedLogLevel(LogLevel.DEBUG);
		// filter.setMessageSentLogLevel(LogLevel.DEBUG);
		// filter.setSessionClosedLogLevel(LogLevel.DEBUG);
		// filter.setSessionCreatedLogLevel(LogLevel.DEBUG);
		// filter.setSessionIdleLogLevel(LogLevel.DEBUG);
		// filter.setSessionOpenedLogLevel(LogLevel.DEBUG);
		this.getAcceptor().getFilterChain().addLast("logger", filter);
		/**
		 * 协议编码和 解码 filter
		 */
		if (this.getDecoder() == null || this.getEncoder() == null) {

			// this.getAcceptor().getFilterChain().addLast(
			// "codec",
			// new ProtocolCodecFilter(new TextLineCodecFactory(Charset
			// .forName("UTF-8"))));
			PrefixedStringCodecFactory factory = new PrefixedStringCodecFactory(Charset.forName("UTF-8"));
			factory.setEncoderPrefixLength(2);
			factory.setEncoderMaxDataLength(102400);
			factory.setDecoderPrefixLength(2);
			factory.setDecoderMaxDataLength(102400);

			this.getAcceptor().getFilterChain().addLast("codec", new ProtocolCodecFilter(factory));

		} else {
			// 自定义扩展coder
			this.getAcceptor().getFilterChain()
					.addLast("codec", new ProtocolCodecFilter(this.getEncoder(), this.getDecoder()));
		}

		/**
		 * 配置任务处理线程池，转发I/O事件的执行者执行某个线程模型， 同时允许每个会话事件同步处理。
		 * <p>
		 * handler
		 */
		// ExecutorFilter 异步处理放在过滤编码之后进行处理
		// java.util.concurrent.Executor threadPool = Executors
		// .newFixedThreadPool(100);

		// java.util.concurrent.Executor threadPool = Executors
		// .newCachedThreadPool();
		String poolConf = Configuration.getConfig("threadpool");
		if (ServerConfiguration.IS_RUN_MINA_THREADPOOLS) {
			logger.info("开启mina任务处理线程池");
			ServerConfiguration.IS_RUN_MINA_THREADPOOLS = true;
			// java.util.concurrent.Executor threadPool =
			// Executors.newFixedThreadPool(100);
			java.util.concurrent.Executor threadPool = Executors.newCachedThreadPool();
			this.getAcceptor().getFilterChain().addLast("exector", new ExecutorFilter(threadPool));
			// acceptor.getFilterChain().addLast("ThreadPool",
			// new ExecutorFilter(Executors.newCachedThreadPool()));

		}
	}

	/**
	 * 
	 */
	public void dispose() {
		try {
			this.getAcceptor().dispose(true);
		} catch (Exception e) {
		}
	}

	public IoAcceptor getAcceptor() {
		if (acceptor == null) {
			acceptor = new NioSocketAcceptor();
		}
		return acceptor;
	}

	public void setAcceptor(IoAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	public IoHandler getHandler() {
		if (handler == null) {
			handler = new BaseServerHandler();
		}
		return handler;
	}

	public void setHandler(IoHandler handler) {
		this.handler = handler;
	}

	public ProtocolEncoder getEncoder() {
		return encoder;
	}

	public ProtocolDecoder getDecoder() {
		return decoder;
	}

	public void setCoder(ProtocolEncoder encoder, ProtocolDecoder decoder) {
		this.encoder = encoder;
		this.decoder = decoder;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
