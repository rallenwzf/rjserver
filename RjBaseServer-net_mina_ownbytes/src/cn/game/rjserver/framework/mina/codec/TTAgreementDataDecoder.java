/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.mina.codec;

import java.io.ByteArrayOutputStream;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import cn.game.rjserver.framework.mina.MinaGameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Constants;
import cn.game.rjserver.framework.protocol.Header;
import cn.game.rjserver.framework.protocol.analyze.AgreementAnalyzeFactory;

/**
 * @author Administrator （接收到数据）自定义解码Agreement imp
 *         <p>
 *         弟一种：lements ProtocolDecoder
 *         <p>
 *         第二种：extends CumulativeProtocolDecoder 重写doDecode(IoSession session,
 *         IoBuffer buf, ProtocolDecoderOutput out)
 */
public class TTAgreementDataDecoder implements ProtocolDecoder {
	static Logger logger = Logger.getLogger(TTAgreementDataDecoder.class.getName());

	// private boolean readHeader;

	@Override
	public void decode(IoSession session, IoBuffer buf, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		// while (!buf.hasRemaining()) {
		// 取包头
		// Header h = null;
		// if (!readHeader) {
		// h = AgreementAnalyzeFactory.getInstance().getAnalyze()
		// .decodeHeader(buf);
		// readHeader = true;
		// }
		System.out.println("buf.remaining()=" + buf.remaining());
		IoBuffer newBuf = IoBuffer.allocate(buf.remaining()).setAutoExpand(true);
		newBuf.order(buf.order());
		newBuf.put(buf);
		newBuf.flip();
		byte b1 = newBuf.get();
		System.out.println("读：byte=" + b1);
		int i = newBuf.getInt();
		System.out.println("读：int=" + i);
		String str = newBuf.getString(newBuf.getUnsignedShort(), Constants.DECODER);
		System.out.println("读：int=" + i + "__string=" + str);

		IoBuffer wbuf = IoBuffer.allocate(64).setAutoExpand(true);
		wbuf.put(b1);
		wbuf.putInt(i);
		str = str + "，测试成功";
		byte b[] = str.getBytes(Constants.ENCODER.charset());
		wbuf.putShort((short) b.length);
		wbuf.put(b);
		System.out.println("写：byte=" + b1 + "__int=" + i + "__string=" + str);
		// Agreement ag = AgreementAnalyzeFactory.getInstance().getAnalyze()
		// .decode(newBuf, new MinaGameIoSession(session));
		// out.write(ag);
		// }
		wbuf.flip();
		out.write(wbuf);

	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

}
