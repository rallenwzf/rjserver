/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.mina.codec;

import java.io.ByteArrayOutputStream;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import cn.game.rjserver.framework.configuration.Configuration;
import cn.game.rjserver.framework.mina.MinaGameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Constants;
import cn.game.rjserver.framework.protocol.Header;
import cn.game.rjserver.framework.protocol.analyze.AgreementAnalyzeFactory;

/**
 * @author Administrator （接收到数据）自定义解码Agreement imp
 *         <p>
 *         处理分包
 */
public class AgreementDataDecoder3 implements ProtocolDecoder {
	static Logger logger = Logger.getLogger(AgreementDataDecoder3.class.getName());

	// private final AttributeKey BUFFER = new AttributeKey(getClass(),
	// "buffer");// 之前buf
	// private final AttributeKey BUFFER_AG = new AttributeKey(getClass(),
	// "buffer_Agreement");// 上一个包
	// private final AttributeKey BUFFER_REMAIN = new AttributeKey(getClass(),
	// "buffer_remain");// 剩余长度
	private final String BUFFER = "buffer";// 之前buf
	private final String BUFFER_AG = "buffer_Agreement";// 上一个包
	private final String BUFFER_REMAIN = "buffer_remain";// 剩余需要的长度

	// private boolean readHeader;

	@Override
	public void decode(IoSession session, IoBuffer buf, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		// 首包

		MinaGameIoSession mgsession = new MinaGameIoSession(session);
		/**
		 * test end
		 */
		IoBuffer recdBuf = IoBuffer.allocate(buf.remaining()).setAutoExpand(true);
		recdBuf.order(buf.order());
		recdBuf.put(buf);
		recdBuf.flip();
		// logger.debug("ProtocolDecoder IoBuffer有数据可读 {sessionID=" +
		// session.getId() + "} ");

		if (session.getAttribute(BUFFER) == null) {
			// 首包
			// int limitLength = recdBuf.limit();
			int limitLength = recdBuf.remaining();
			// logger.debug("首包-----------" + limitLength);
			if (limitLength < Constants.CLIENTREQUEST_HEAD_LENGTH) {
				// 初始首包都不够，直接忽略解析
				return;
			}
			Header h = AgreementAnalyzeFactory.getInstance().getAnalyze().decodeHead(recdBuf, mgsession);
			Agreement ag = new Agreement();
			ag.setHeader(h);
			logger.debug("首包-- {sessionID=" + session.getId() + "}-----limitLength=" + limitLength + "------Header.dataLength=" + h.getDataLength());
			if (h.getDataLength() > 1000) {
				logger.debug("首包-- {sessionID=" + session.getId() + "}----fffffffffff----Header.dataLength=" + ag.toString());
			}
			if (h.getDataLength() == limitLength) {
				// 单包，完整包
				Agreement cag = AgreementAnalyzeFactory.getInstance().getAnalyze().decodeBody(ag, recdBuf, mgsession);
				out.write(cag);
			} else if (h.getDataLength() > limitLength) {
				// 包不够
				int needLength = h.getDataLength() - limitLength;
				logger.debug("首包-- {sessionID=" + session.getId() + "}---包不够————————needLength=" + needLength);
				storeRemainingInSession(recdBuf, session, needLength, ag);
			} else if (h.getDataLength() < limitLength) {
				// 存在粘包
				int needBody = h.getDataLength() - ag.getHeader().getPackgeHeaderLength();
				IoBuffer remainbuf = IoBuffer.allocate(limitLength).setAutoExpand(true);
				remainbuf.order(recdBuf.order());
				byte needbs[] = new byte[needBody];
				recdBuf.get(needbs);
				remainbuf.put(needbs);
				remainbuf.flip();
				logger.debug("首包-- {sessionID=" + session.getId() + "}---存在粘包————————needBody=" + needBody);
				//
				// 粘包剩余的
				byte remainbs[] = new byte[limitLength - h.getDataLength()];
				IoBuffer newbuf = IoBuffer.allocate(limitLength).setAutoExpand(true);
				newbuf.order(recdBuf.order());
				recdBuf.get(remainbs);
				newbuf.put(remainbs);
				newbuf.flip();
				try {
					Agreement cag = AgreementAnalyzeFactory.getInstance().getAnalyze().decodeBody(ag, remainbuf, mgsession);
					out.write(cag);
				} finally {
					this.removeBuffer(session);
				}

				this.decode(session, newbuf, out);
			}
		} else {
			// 子包
			int limitLength = recdBuf.remaining();
			IoBuffer lastBuf = (IoBuffer) session.getAttribute(BUFFER);
			int needLength = Integer.parseInt(session.getAttribute(BUFFER_REMAIN).toString());
			Agreement ag = (Agreement) session.getAttribute(BUFFER_AG);
			logger.debug("子包-----recdBuf.remaining()=" + limitLength + " |needLength=" + needLength + " |lastBuf=" + lastBuf.limit());
			if (needLength == limitLength) {
				// 完整子包，叠加之前的包
				lastBuf.put(recdBuf);
				lastBuf.flip();
				try {
					Agreement cag = AgreementAnalyzeFactory.getInstance().getAnalyze().decodeBody(ag, lastBuf, mgsession);
					out.write(cag);
				} finally {
					this.removeBuffer(session);
				}
			} else if (needLength > limitLength) {
				// 包不够，叠加之前的包再存储
				lastBuf.put(recdBuf);
				lastBuf.flip();
				int r = needLength - limitLength;
				storeRemainingInSession(lastBuf, session, r, ag);
			} else if (needLength < limitLength) {
				// 粘包
				logger.debug("子包--粘包---recdBuf.remaining()=" + limitLength + " |needLength=" + needLength + " |lastBuf=" + lastBuf.limit());
				byte needbs[] = new byte[needLength];
				recdBuf.get(needbs);
				lastBuf.put(needbs);
				lastBuf.flip();
				try {
					Agreement cag = AgreementAnalyzeFactory.getInstance().getAnalyze().decodeBody(ag, lastBuf, mgsession);
					out.write(cag);
				} finally {
					this.removeBuffer(session);
				}
				// 粘包剩余的
				byte remainbs[] = new byte[limitLength - needLength];
				IoBuffer newbuf = IoBuffer.allocate(limitLength).setAutoExpand(true);
				newbuf.order(recdBuf.order());
				recdBuf.get(remainbs);
				newbuf.put(remainbs);
				newbuf.flip();
				this.decode(session, newbuf, out);
			}
		}
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		removeBuffer(session);
	}

	private void doRemainStickBuffer(IoBuffer buf, int readednum, int limitLength) {

	}

	private void removeBuffer(IoSession session) {
		session.removeAttribute(BUFFER);
		session.removeAttribute(BUFFER_REMAIN);
		session.removeAttribute(BUFFER_AG);
	}

	private void storeRemainingInSession(IoBuffer recdBuf, IoSession session, int remain, Agreement ag) {
		final IoBuffer remainingBuf = IoBuffer.allocate(recdBuf.capacity()).setAutoExpand(true);
		remainingBuf.order(recdBuf.order());
		remainingBuf.put(recdBuf);
		session.setAttribute(BUFFER, remainingBuf);
		session.setAttribute(BUFFER_REMAIN, remain);
		session.setAttribute(BUFFER_AG, ag);
	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {
		// TODO Auto-generated method stub

	}

}
