/*********************************************
    Copyright (c) 2011 by rallen
 *********************************************/

/**
 * a4a-commons-robot 
 * 2011-1-24
 */
package cn.game.rjserver.utils.sms.htmlparser;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;
import org.htmlparser.util.SimpleNodeIterator;

/**
 * @author rallen 2011-1-24
 * @doc class explain:
 */
public class ParserTool {
	static Logger logger = Logger.getLogger(ParserTool.class.getName());

	/**
	 * 获取最终的纯文本
	 * 
	 * @param str
	 * @return
	 */
	public static String getPlainText(String str) {
		try {
			Parser parser = new Parser();
			parser.setInputHTML(str);

			StringBean sb = new StringBean();
			// 设置不需要得到页面所包含的链接信息
			sb.setLinks(false);
			// 设置将不间断空格由正规空格所替代
			sb.setReplaceNonBreakingSpaces(true);
			// 设置将一序列空格由一个单一空格所代替
			sb.setCollapse(true);
			parser.visitAllNodesWith(sb);
			str = sb.getStrings();

		} catch (ParserException e) {
			logger.info("解析失败");
		}
		return str;
	}

	// ParserUtils.trimAllTags(html, false);
	/**
	 * 获得网页节点指定的元素的集合
	 * 
	 * @param html
	 * @param cls
	 * @return SimpleNodeIterator
	 */
	public static SimpleNodeIterator getHtmlElements(String html, Class cls) {
		try {
			// logger.info(html);
			Parser parser = new Parser(html);
			NodeList nodeList = parser.parse(new NodeClassFilter(cls));
			return nodeList.elements();
		} catch (Exception e) {
			// e.printStackTrace();
			logger.info("解析失败");
			return null;
		}
	}

	public static NodeList getHtmlElementsList(String html, Class cls) {
		try {
			Parser parser = new Parser(html);
			NodeList nodeList = parser.parse(new NodeClassFilter(cls));
			return nodeList;
		} catch (Exception e) {
			logger.info("解析失败");
			return null;
		}
	}

	public static NodeList getHtmlElementsList(String html, String tagName) {
		try {
			Parser parser = new Parser(html);
			NodeList nodeList = parser
					.extractAllNodesThatMatch(new TagNameFilter(tagName));
			return nodeList;
		} catch (Exception e) {
			logger.info("解析失败");
			return null;
		}
	}

//	public static Node getHtmlElement(String html, SingleTag singleTag) {
//		try {
//			Parser parser = new Parser(html);
//			NodeList nodeList = parser.parse(new NodeClassFilter(TagDisposer
//					.getInstance().getClassFilter(singleTag.getTagName())));
//			for (int i = 0; i < nodeList.size(); i++) {
//				Node node = nodeList.elementAt(i);
//				try {
//					Tag tag = (Tag) node;
//					if (TagDisposer.getInstance().compareTo(tag, singleTag)) {
//						return node;
//					}
//					continue;
//				} catch (Exception ex) {
//					ex.printStackTrace();
//				}
//
//				Method method = node.getClass().getMethod("getAttribute",
//						new java.lang.Class[] { String.class });
//				Object obj = method.invoke(node, new Object[] { new String(
//						singleTag.getAttributeName()) });
//				if (obj != null) {
//					if (obj.toString().equals(singleTag.getAttributeValue())) {
//						return node;
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.info("解析失败");
//		}
//		return null;
//	}

	public static Node getHtmlElement(String html, Class cls, int idx) {
		try {
			Parser parser = new Parser(html);
			NodeList nodeList = parser.parse(new NodeClassFilter(cls));
			return nodeList.elementAt(idx);
		} catch (Exception e) {
			logger.info("解析失败");
		}
		return null;
	}

	public static Node getHtmlElement(String html, Class cls, String attrName,
			String attrValue) {
		try {
			Parser parser = new Parser(html);
			NodeList nodeList = parser.parse(new NodeClassFilter(cls));
			for (int i = 0; i < nodeList.size(); i++) {
				Node node = nodeList.elementAt(i);
				Method method = node.getClass().getMethod("getAttribute",
						new java.lang.Class[] { String.class });
				Object obj = method.invoke(node, new Object[] { new String(
						attrName) });
				if (obj != null) {
					if (obj.toString().equals(attrValue)) {
						return node;
					}
				}
			}
			return null;
		} catch (Exception e) {
			logger.info("解析失败");
		}
		return null;
	}

	/**
	 * 从html文档中 得到 指定的 元素的 html 字符串， 只返回一个
	 * 
	 * @param html
	 *            html文档
	 * @param cls
	 *            元素的类别
	 * @param name
	 *            元素的属性名
	 * @param value
	 *            元素的属性值
	 * @return
	 */
	public static String getHtmlElementHtml(String html, Class cls,
			String name, String value) {
		try {
			Node node = getHtmlElement(html, cls, name, value);
			node.toHtml();
		} catch (Exception ex) {
			logger.info("解析失败");
		}
		return null;
	}
}
