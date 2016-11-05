package cn.game.rjserver.uc.web.sdkpayservice;

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
import cn.game.rjserver.uc.web.sdkpayservice.heyouxi.ConsumecodeObj;
import cn.game.rjserver.uc.web.sdkpayservice.heyouxi.HeyouxiConsumeCodeUtils;

/**
 * 移动基地和游戏回调服务接口<br>
 * 透传参数采用16进制编码
 */
public class TestService extends BaseService {
	private static final long serialVersionUID = 1L;
	private static int channelid = 0;

	/**
	 * @see BaseService#BaseService()
	 */
	public TestService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void dispose(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");

		ConsumecodeObj ccobj = HeyouxiConsumeCodeUtils.getConsumecodeObj("101251000001");
		CallToGameserver.callBack10510(103, "1010000011", ccobj.getConsumeCode(), true, ccobj.getMoneyNum(),
				ccobj.getCurrencyNum(), new String[] { ccobj.getAttrType(), ccobj.getAttrCode(), ccobj.getAttrNum() });

	}

}
