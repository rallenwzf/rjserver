/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.game.rjserver.framework.executor.AbstractorGameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.utils.json.JsonUtil;

/**
 * @author wangzhifeng(rallen)
 */
public class HttpIoSession extends AbstractorGameIoSession {

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
				System.out.println("MyTestApi.dispose() : nContentLength<0. exit. " + nContentLength);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ag;
	}

	@Override
	public String getSessionId() {
		// TODO Auto-generated method stub
		return request.getSession().getId();
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
		String jsonString = new String(abyteData, Charset.forName("UTF-8"));
		System.out.println(jsonString);
		if (this.isC2sIsGzip()) {
			// 解密解析
			// clientAgreement = ag;
			return clientAgreement;
		} else {
			// 解析
			try {
				Map map = JsonUtil.getMap4Json(jsonString);
				if (map != null) {
					Object obj = map.get("id");
					if (obj != null) {
						Agreement ag = new Agreement(Short.parseShort(obj.toString()));
						Object sidobj = map.get("sid");
						if (sidobj != null) {
							ag.setSid(Long.parseLong(sidobj.toString()));
						}
						ag.setDatas(map);
						ag.setJsonstr(jsonString);
						clientAgreement = ag;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return clientAgreement;
		}

	}

	private void immediateWrite(Object obj) {
		// TODO Auto-generated method stub
		try {
			Agreement ag = (Agreement) obj;
			// int s = AgreementAnalyzeFactory.getInstance().getAnalyze()
			// .getEncodeAgreementLength(ag);
			// 发送
			write(ag);

			// System.out.println("___发送(agCount=" + agCount + ")：" +
			// ag.toString());
			// log.debug("___发送(agCount=" + agCount + ")：" + ag.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean write(Agreement ag) {
		OutputStream os = null;
		PrintWriter pw = null;
		try {

			os = response.getOutputStream();
			//
			ag.getDatas().put("id", ag.getCmdId());
			ag.getDatas().put("s", ag.isSucc());
			String jsonstr = JsonUtil.getJsonString4JavaObj(ag.getDatas());
			jsonstr = jsonstr.replace("null", "nil");
			ag.setJsonstr(jsonstr);
			byte bs[] = jsonstr.getBytes(Charset.forName("UTF-8"));
			// response.setHeader("Content-Length", bs.length + "");
			os.write(bs);
			os.flush();
			// pw = response.getWriter();
			// pw.print(jsonstr.getBytes(Charset.forName("UTF-8")));
			// pw.flush();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return false;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public Object getAttribute(Object key) {
		// TODO Auto-generated method stub
		return request.getSession().getAttribute(key.toString());
	}

	@Override
	public Object setAttribute(Object key, Object value) {
		// TODO Auto-generated method stub
		request.getSession().setAttribute(key.toString(), value);
		return value;
	}

	@Override
	public Object removeAttribute(Object key) {
		// TODO Auto-generated method stub
		request.getSession().removeAttribute(key.toString());
		return true;
	}

	@Override
	public boolean containsAttribute(Object key) {
		// TODO Auto-generated method stub
		Object obj = this.getAttribute(key.toString());
		if (obj == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Set<Object> getAttributeKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemortAddress() {
		// TODO Auto-generated method stub
		return getRemortIP(request);
	}

	public String getRemortIP(HttpServletRequest request) {

		String address = request.getHeader("x-forwarded-for");
		if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
			address = request.getHeader("Proxy-Client-IP");
		} else {
			address = "x-f-f_" + address;
		}
		if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
			address = request.getHeader("WL-Proxy-Client-IP");
		} else {
			address = "p-c-i_" + address;
		}
		if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
			address = "r-a_" + request.getRemoteAddr();
		} else {
			address = "w-c-i_" + address;
		}
		return address;
	}
}
