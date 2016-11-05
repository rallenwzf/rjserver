/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.sdk.uitls;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import cn.game.rjserver.uc.api.entity.ServersInfo;
import cn.game.rjserver.uc.sdk.Config;

/**
 * @author rallen
 * 
 */
public class ResponseParser {

	public static Document getDocument(String xml) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new ByteArrayInputStream(xml.trim().getBytes(Config.POST_RESPONSE_ENDODING)));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return document;
	}
}
