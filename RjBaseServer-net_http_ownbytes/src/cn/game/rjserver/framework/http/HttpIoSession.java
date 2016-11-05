/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.game.rjserver.basicutils.file.GzipUtil;
import cn.game.rjserver.framework.executor.AbstractorGameIoSession;
import cn.game.rjserver.framework.http.buffer.IoBuffer;
import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.analyze.AgreementAnalyzeFactory;

/**
 * @author wangzhifeng(rallen)
 */
public class HttpIoSession extends AbstractorGameIoSession {
	static Logger logger = Logger.getLogger(HttpIoSession.class.getName());
	HttpServletRequest request;
	HttpServletResponse response;
	Agreement clientAgreement;
	private Agreement serverAgreement = null;

	public HttpIoSession(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;

	}

	@Override
	public void write(Object obj) {
		// TODO Auto-generated method stub
		if (this.isGroup()) {
			try {
				Agreement sag = (Agreement) obj;
				if (serverAgreement == null) {
					serverAgreement = sag;
				} else {
					serverAgreement.addAgreement(sag);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			immediateWrite(obj);
		}
	}

	private void immediateWrite(Object obj) {
		// TODO Auto-generated method stub
		try {
			Agreement ag = (Agreement) obj;
			int s = AgreementAnalyzeFactory.getInstance().getAnalyze().getEncodeAgreementLength(ag);
			IoBuffer buf = IoBuffer.allocate(s);
			buf.setAutoExpand(true);
			byte agCount = (byte) ag.getAgreementCount();
			buf.put(agCount);
			AgreementAnalyzeFactory.getInstance().getAnalyze().encode(ag, buf, this);
			buf.flip();
			//
			System.out.println(this.isS2cIsGzip() + "______isS2cIsGzip________");
			boolean isCompress = false;
			//
			if (this.isS2cIsGzip()) {
				byte[] bdatas = new byte[buf.limit()];
				buf.get(bdatas);
				byte datas[] = null;
				try {
					// buf.shrink();
					datas = GzipUtil.compress(bdatas);
					if (datas.length < bdatas.length) {
						isCompress = true;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (isCompress) {
					// 压缩
					IoBuffer ibuf = IoBuffer.allocate(datas.length);
					ibuf.setAutoExpand(true);
					// 加压缩标识
					ibuf.put((byte) 1);
					// 加压缩大小
					ibuf.putInt(datas.length);
					// 加压缩后内容
					ibuf.put(datas);
					// buf.shrink();
					ibuf.flip();
					// 发送
					ag.setGzipLength(ibuf.limit());
					write(ibuf);
				} else {
					// 不压缩
					IoBuffer ibuf = IoBuffer.allocate(datas.length);
					ibuf.setAutoExpand(true);
					// 加压缩标识
					ibuf.put((byte) 0);
					// 加原协议数据
					ibuf.put(bdatas);
					ibuf.flip();
					// 发送
					write(ibuf);
				}
			} else {
				// 发送
				write(buf);
			}

			// System.out.println("___发送(agCount=" + agCount + ")：" +
			// ag.toString());
			// log.debug("___发送(agCount=" + agCount + ")：" + ag.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean write(IoBuffer buf) {
		OutputStream os = null;
		try {
			byte[] b = new byte[buf.limit()];
			buf.get(b);
			os = response.getOutputStream();
			os.write(b);
			os.flush();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public Agreement receive() {
		// TODO Auto-generated method stub
		Agreement ag = null;
		try {
			int nContentLength = request.getContentLength();
			if (receiveListener != null) {
				receiveListener.receivedContentLength(nContentLength);
				if (receiveListener.isCansole()) {
					return null;
				}
			}
			if (nContentLength >= 0) {
				byte[] abyteData = new byte[nContentLength];
				int nCount = 0;
				int nTemp = 0;
				InputStream is;
				is = request.getInputStream();
				if (receiveListener != null) {
					receiveListener.startRead();
				}
				if (timesReceiveSize <= 0) {
					timesReceiveSize = nContentLength;
				}
				int tempSize = 0;
				while (nCount < nContentLength) {
					int remainSize = nContentLength - nCount;
					if (remainSize > timesReceiveSize) {
						tempSize = timesReceiveSize;
					} else {
						tempSize = remainSize;
					}
					nTemp = is.read(abyteData, nCount, tempSize);
					nCount = nCount + nTemp;
					if (receiveListener != null) {
						receiveListener.reading(nCount);
						if (receiveListener.isCansole()) {
							return null;
						}
					}
				}
				if (receiveListener != null) {
					receiveListener.readFinished(abyteData);
				}
				ag = read(abyteData);

			} else {
				logger.debug("MyTestApi.dispose() : nContentLength<0. exit. " + nContentLength);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ag;
	}

	public Agreement getClientAgreement() {
		return clientAgreement;
	}

	/**
	 * @param abyteData
	 * @return
	 */
	private Agreement read(byte[] abyteData) {
		if (abyteData.length <= 0) {
			return null;
		}
		if (this.isC2sIsGzip()) {
			int lengs = abyteData.length;
			IoBuffer buff = IoBuffer.wrap(abyteData, 4, abyteData.length - 4);
			try {
				abyteData = GzipUtil.uncompress(buff.array());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Agreement ag = AgreementAnalyzeFactory.getInstance().getAnalyze().decode(buff, this);
			ag.setGzipLength(lengs);
			// System.out.println("___收到()：" + ag.toString());
			// log.debug("___收到()：" + ag.toString());
			clientAgreement = ag;
			return clientAgreement;
		} else {
			IoBuffer buff = IoBuffer.wrap(abyteData);
			Agreement ag = AgreementAnalyzeFactory.getInstance().getAnalyze().decode(buff, this);
			// System.out.println("___收到()：" + ag.toString());
			// log.debug("___收到()：" + ag.toString());
			clientAgreement = ag;
			return clientAgreement;
		}

	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public Set<Object> getAttributeKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemortAddress() {
		// TODO Auto-generated method stub
		return null;
	}

}
