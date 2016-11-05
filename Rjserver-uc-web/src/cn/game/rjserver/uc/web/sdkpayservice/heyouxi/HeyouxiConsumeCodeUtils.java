/**
 * 
 */
package cn.game.rjserver.uc.web.sdkpayservice.heyouxi;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Administrator
 * 
 */
public class HeyouxiConsumeCodeUtils {
	static Logger logger = Logger.getLogger(HeyouxiConsumeCodeUtils.class);

	public static Map<String, ConsumecodeObj> consumecodeMaps = new HashMap<String, ConsumecodeObj>();

	public static void load(String xml, String code) {
		logger.debug("ConsumeCodeUtils->load：" + xml);
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(new FileInputStream(xml));

			consumecodeMaps.clear();
			Element root = document.getRootElement();
			List<Element> elementList = root.elements("item");
			for (int i = 0; i < elementList.size(); i++) {
				Element element = elementList.get(i);
				ConsumecodeObj obj = new ConsumecodeObj();
				obj.setConsumeCode(element.attributeValue("consumeCode"));
				obj.setCurrencyNum(Integer.parseInt(element
						.attributeValue("currencyNum")));
				obj.setMoneyNum(Integer.parseInt(element
						.attributeValue("moneyNum")));
				if (element.attributeValue("propsId") != null)
					obj.setPropsId(Integer.parseInt(element
							.attributeValue("propsId")));
				if (element.attributeValue("propsName") != null)
					obj.setPropsName(element.attributeValue("propsName"));

				obj.setAttrType(element.attributeValue("attrType"));
				obj.setAttrCode(element.attributeValue("attrCode"));
				obj.setAttrNum(element.attributeValue("attrNum"));
				consumecodeMaps.put(obj.getConsumeCode(), obj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("计费文件加载失败！");
		}
		logger.debug("ConsumeCodeUtils->load ：" + consumecodeMaps.size()
				+ " ************end****");
	}

	public static ConsumecodeObj getConsumecodeObj(String consumeCode) {
		if (consumecodeMaps.containsKey(consumeCode)) {
			return consumecodeMaps.get(consumeCode);
		}
		return null;
	}

}
