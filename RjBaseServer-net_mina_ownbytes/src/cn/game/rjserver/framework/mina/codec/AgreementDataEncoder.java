package cn.game.rjserver.framework.mina.codec;

import java.io.DataOutputStream;
import java.io.UTFDataFormatException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import cn.game.rjserver.framework.mina.MinaGameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Constants;
import cn.game.rjserver.framework.protocol.analyze.AgreementAnalyzeFactory;

/**
 * @author Administrator 编码（发送给客户端）
 */
public class AgreementDataEncoder implements ProtocolEncoder {
	static Logger logger = Logger.getLogger(AgreementDataEncoder.class.getName());

	@Override
	public void encode(IoSession session, Object arg1, ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub

		Agreement ag = (Agreement) arg1;
		IoBuffer buf = IoBuffer.allocate(Constants.SERVERREPONSE_HEAD_LENGTH);
		buf.setAutoExpand(true); // 可自动扩展
		// 加 包头信息\消息体
		AgreementAnalyzeFactory.getInstance().getAnalyze().encode(ag, buf, new MinaGameIoSession(session));
		buf.flip();
		out.write(buf);
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}
}
