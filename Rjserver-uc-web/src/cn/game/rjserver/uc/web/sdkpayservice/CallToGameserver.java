/**
 * 
 */
package cn.game.rjserver.uc.web.sdkpayservice;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.game.rjserver.uc.entity.TgsmServers;
import cn.game.rjserver.uc.provider.GsmucServerProvider;
import cn.game.rjserver.uc.provider.UcProviderManager;

/**
 * @author Administrator 通知游戏服
 */
public class CallToGameserver {
	static CharsetEncoder ENCODER = Charset.forName("UTF-8").newEncoder();
	static Logger logger = Logger.getLogger(CallToGameserver.class);

	/*
	 * 含附属物
	 */
	public static void callBack10510(int serverid, String orderId, String consumcode, boolean resultb, int rmb,
			int coinnum, String attr[]) {
		logger.debug("准备通知游戏服——————10510");
		int attrcount = 0;
		if (attr == null || attr[0] == null || attr[0].equals("")) {
			attrcount = 0;
		} else {
			attrcount = 1;
		}
		/**
		 * 
		 */
		short dataLen = (short) (9 + (orderId.getBytes(ENCODER.charset()).length + 2)
				+ (consumcode.getBytes(ENCODER.charset()).length + 2) + 1 + 4 + 4 + 1);
		if (attrcount > 0) {
			dataLen += (short) ((attr[1].getBytes(ENCODER.charset()).length + 2) + 1 + 4);
		}

		ByteBuffer buffer = ByteBuffer.allocate(512);
		// header
		buffer.put((byte) 0);
		buffer.put((byte) 7);
		buffer.putShort((short) dataLen);
		buffer.putShort((short) 0);
		buffer.put((byte) 0);
		// content
		buffer.putShort((short) 10510);
		// datas
		byte b[] = orderId.getBytes(ENCODER.charset());
		buffer.putShort((short) b.length);
		buffer.put(b);
		//
		byte b2[] = consumcode.getBytes(ENCODER.charset());
		buffer.putShort((short) b2.length);
		buffer.put(b2);
		//
		buffer.put((byte) (resultb ? 0 : 1));

		buffer.putInt(rmb);
		buffer.putInt(coinnum);

		//
		buffer.put((byte) attrcount);
		if (attrcount > 0) {
			byte b3[] = attr[1].getBytes(ENCODER.charset());
			buffer.putShort((short) b3.length);
			buffer.put(b3);

			buffer.put((byte) Integer.parseInt(attr[0]));
			buffer.putInt(Integer.parseInt(attr[2]));
		}
		buffer.flip();

		// send
		// byte[] responseContent = buffer.array();
		sendWithOrderId(serverid, buffer);
	}

	private static void sendWithOrderId(int serverId, ByteBuffer buffer) {
		OutputStream out = null;
		Socket s = null;
		InputStream in = null;
		try {
			byte[] responseContent = new byte[buffer.limit()];
			buffer.get(responseContent);
			TgsmServers serverobj = UcProviderManager.getInstance().getServerProvider().getServer(serverId);
			String host = serverobj.getServerhosting();
			int port = 0;
			s = new Socket(host, port);
			out = s.getOutputStream();
			out.write(responseContent);
			out.flush();
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
				out = null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				s.close();
				s = null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}

}
