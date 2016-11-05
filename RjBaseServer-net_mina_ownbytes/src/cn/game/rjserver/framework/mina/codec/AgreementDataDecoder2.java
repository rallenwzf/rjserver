/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.mina.codec;

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
import cn.game.rjserver.framework.protocol.Header;
import cn.game.rjserver.framework.protocol.analyze.AgreementAnalyzeFactory;



/**
 * @author Administrator （接收到数据）自定义解码Agreement imp
 *         <p>
 *         lements ProtocolDecoder
 *         <p>
 *         CumulativeProtocolDecoder
 */
public class AgreementDataDecoder2 extends CumulativeProtocolDecoder {
	static Logger logger = Logger.getLogger(AgreementDataDecoder2.class
			.getName());

	// private boolean readHeader;

	// @Override
	// public void decode(IoSession session, IoBuffer buf,
	// ProtocolDecoderOutput out) throws Exception {
	// // TODO Auto-generated method stub
	// // while (!buf.hasRemaining()) {
	// // 取包头
	// // Header h = null;
	// // if (!readHeader) {
	// // h = AgreementAnalyzeFactory.getInstance().getAnalyze()
	// // .decodeHeader(buf);
	// // readHeader = true;
	// // }
	//Agreement ag = AgreementAnalyzeFactory.getInstance().getAnalyze()
	// .decode(buf, session);
	// out.write(ag);
	// // }
	//
	// }

	@Override
	protected boolean doDecode(IoSession session, IoBuffer buf,
			ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		// 取包头、包体
		IoBuffer newBuf = IoBuffer.allocate(
                buf.remaining() ).setAutoExpand(true);
        newBuf.order(buf.order());
        newBuf.put(buf);
        newBuf.flip();
		Agreement ag = AgreementAnalyzeFactory.getInstance().getAnalyze()
				.decode(newBuf, new MinaGameIoSession(session));
		out.write(ag);
		return false;
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
