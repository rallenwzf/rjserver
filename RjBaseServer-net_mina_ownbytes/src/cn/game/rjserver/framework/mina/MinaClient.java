package cn.game.rjserver.framework.mina;

import java.io.ObjectInputStream.GetField;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {
	public SocketConnector socketConnector;
	/**
	 * 缺省连接超时时间
	 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 5;
	public String HOST = "192.168.1.128";
	public int PORT = 9999;
	private IoHandler handler;
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;
	public ConnectFuture connectFuture = null;

	public MinaClient() {

	}

	public void start() {
		socketConnector = new NioSocketConnector();

		// 长连接
		// socketConnector.getSessionConfig().setKeepAlive(true);

		socketConnector.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);

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

			socketConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(factory));

		} else {
			// 自定义扩展coder
			socketConnector.getFilterChain().addLast("codec",
					new ProtocolCodecFilter(this.getEncoder(), this.getDecoder()));
		}

		socketConnector.setHandler(this.getHandler());

		InetSocketAddress addr = new InetSocketAddress(HOST, PORT);
		connectFuture = socketConnector.connect(addr);
		System.out.println("HOST=" + HOST + " | PORT=" + PORT);
		connectFuture.awaitUninterruptibly();

		long sid = connectFuture.getSession().getId();
		System.out.println("client session id=" + sid);
	}

	public SocketConnector getSocketConnector() {
		return socketConnector;
	}

	public void setSocketConnector(SocketConnector socketConnector) {
		this.socketConnector = socketConnector;
	}

	public IoHandler getHandler() {
		return handler;
	}

	public void setHandler(IoHandler handler) {
		this.handler = handler;
	}

	public ProtocolEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(ProtocolEncoder encoder) {
		this.encoder = encoder;
	}

	public ProtocolDecoder getDecoder() {
		return decoder;
	}

	public void setDecoder(ProtocolDecoder decoder) {
		this.decoder = decoder;
	}

	public ConnectFuture getConnectFuture() {
		return connectFuture;
	}

	public void setConnectFuture(ConnectFuture connectFuture) {
		this.connectFuture = connectFuture;
	}

	public static void main(String[] args) throws InterruptedException {
		MinaClient clent = new MinaClient();
		for (int i = 0; i < 1; i++) {
			System.err.println(i);
			clent.getConnectFuture().getSession().write("Hello World " + i);
		}
		clent.getSocketConnector().dispose();
		// System.exit(0);
	}

}
