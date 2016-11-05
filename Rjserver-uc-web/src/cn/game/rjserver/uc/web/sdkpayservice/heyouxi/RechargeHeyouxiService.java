package cn.game.rjserver.uc.web.sdkpayservice.heyouxi;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.web.sdkpayservice.App;
import cn.game.rjserver.uc.web.sdkpayservice.BaseService;
import cn.game.rjserver.uc.web.sdkpayservice.CallToGameserver;
import cn.game.rjserver.uc.web.sdkpayservice.RequestUrl;

/**
 * 移动基地和游戏回调服务接口<br>
 * 透传参数采用16进制编码
 */
public class RechargeHeyouxiService extends BaseService {
	private static final long serialVersionUID = 1L;
	private static int channelid = 0;

	/**
	 * @see BaseService#BaseService()
	 */
	public RechargeHeyouxiService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void dispose(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		if (request.getParameter("userId") != null) {
			// 登录接口
			this.disposeLogin(request, response);
		} else {
			// 付费接口
			this.disposePay(request, response);
		}
	}

	private void disposeLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 暂不需要通知游戏服做任何处理
		try {
			logger.debug("收到：" + getRequestURL(request));
			String userId = request.getParameter("userId");
			String key = request.getParameter("key");
			String cpId = request.getParameter("cpId");
			String cpServiceId = request.getParameter("cpServiceId");
			String channelId = request.getParameter("channelId");
			String cpparam = request.getParameter("p");
			if (cpparam != null && !cpparam.equals("")) {
				channelid = Integer.parseInt(cpparam, 16);
			}
			String region = request.getParameter("region");
			String Ua = request.getParameter("Ua");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//
		response.setCharacterEncoding("utf-8");
		String str = getBackContent(true);
		logger.debug("发送：" + str);
		response.getWriter().print(str);
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void disposePay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HeyouxiDataObj dataObj = null;
		String requestContent = null;
		try {
			int nContentLength = request.getContentLength();
			int timesReceiveSize = 2048;
			if (nContentLength >= 0) {
				byte[] abyteData = new byte[nContentLength];
				int nCount = 0;
				int nTemp = 0;
				InputStream is;
				is = request.getInputStream();
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

				}
				requestContent = new String(abyteData, "utf-8");
				dataObj = getPostDataObj(requestContent, "utf-8");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			// 推送
			this.callBackTo(dataObj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append("<response>");
		try {
			if (dataObj != null) {
				String msg = "";
				boolean succ = false;
				if (dataObj.gethRet().trim().equals("0")) {
					// z支付成功
					succ = true;
					msg = "Successful";
				} else {
					msg = "Failed";
				}
				// 通知游戏服务器支付结果
				// 计费透传参数（16字节）【使用16进制，】：订单号(10)_服务器id(4)_充值项(2)
				long orderid = 0;
				int serverid = 0;
				int itemid = 0;
				int rmb = 0; // 分
				try {
					orderid = Long.parseLong(dataObj.getCpparam().substring(0, 10), 16);
					serverid = Integer.parseInt(dataObj.getCpparam().substring(10, 14), 16);
					itemid = Integer.parseInt(dataObj.getCpparam().substring(14), 16);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("dataObj.getCpparam()=" + dataObj.getCpparam()
							+ " 接受数据有误，怎不是不是这个规则：订单号(10)_服务器id(4)_充值项(2)");
					e.printStackTrace();
				}
				//
				ConsumecodeObj ccobj = HeyouxiConsumeCodeUtils.getConsumecodeObj(dataObj.getConsumeCode());
				if (ccobj != null && ccobj.getPropsId() == itemid) {
					CallToGameserver.callBack10510(serverid, orderid + "", ccobj.getConsumeCode(), succ,
							ccobj.getMoneyNum(), ccobj.getCurrencyNum(),
							new String[] { ccobj.getAttrType(), ccobj.getAttrCode(), ccobj.getAttrNum() });

					buffer.append("<hRet>" + dataObj.gethRet() + "</hRet>");
					buffer.append("<message>" + msg + "</message>");
				} else {
					buffer.append("<message>error 计费代码不正确" + itemid + "_" + dataObj.getConsumeCode() + "</message>");
				}

//				// 添加计费记录
//				UcProviderManager
//						.getInstance()
//						.getUserProvider()
//						.addTsdkOrder(dataObj.getConsumeCode(), orderid + "", new Date().getTime() + "", rmb + "",
//								channelid + "", dataObj.gethRet());
			} else {
				buffer.append("<message>error 接受数据有误</message>");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		buffer.append("</response>");
		response.setCharacterEncoding("utf-8");
		logger.debug("发送：" + buffer.toString());
		response.getWriter().print(buffer.toString());
	}

	private void callBackTo(HeyouxiDataObj dataObj) {
		/*
		 * 推送支付结果
		 */
		if (App.pushUrl != null && dataObj != null) {
			for (int i = 0; i < App.pushUrl.length; i++) {
				String url = App.pushUrl[i];
				// if (App.pushMethod[i].equals("get")) {
				StringBuffer urlBuf = new StringBuffer();
				urlBuf.append(url);
				urlBuf.append("?userId=" + dataObj.getUserId());
				urlBuf.append("&packageID=" + dataObj.getPackageID());
				urlBuf.append("&consumeCode=" + dataObj.getConsumeCode());
				urlBuf.append("&cpparam=" + dataObj.getCpparam());
				urlBuf.append("&hRet=" + dataObj.gethRet());
				urlBuf.append("&status=" + dataObj.getStatus());
				urlBuf.append("&versionId=" + dataObj.getVersionId());
				urlBuf.append("&contentId=" + dataObj.getContentId());
				RequestUrl.runUrl(urlBuf.toString());
				// } else {
				// RequestUrl.runUrl(url, dataObj);
				// }
			}
		}
	}

	private HeyouxiDataObj getPostDataObj(String xml, String code) {
		logger.debug("收到：" + xml);
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(new ByteArrayInputStream(xml.getBytes(code)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(e);
		}
		try {
			Element root = document.getRootElement();
			HeyouxiDataObj obj = new HeyouxiDataObj();
			obj.setUserId(root.element("userId").getTextTrim());
			obj.setContentId(root.element("contentId").getTextTrim());
			obj.setConsumeCode(root.element("consumeCode").getTextTrim());
			obj.setCpId(root.element("cpId").getTextTrim());
			obj.sethRet(root.element("hRet").getTextTrim());
			obj.setStatus(root.element("status").getTextTrim());
			obj.setVersionId(root.element("versionId").getTextTrim());
			obj.setCpparam(root.element("cpparam").getTextTrim());
			obj.setPackageID(root.element("packageID").getTextTrim());
			return obj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(e);
		}

		return null;
	}
}
