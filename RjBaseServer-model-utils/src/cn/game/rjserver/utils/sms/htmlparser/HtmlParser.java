/*********************************************
    Copyright (c) 2009 by rayjar.net
 *********************************************/

/**
 * RAYJAR_ROBOT 
 * 2009-9-11
 * com.rajar.robot.spider.HtmlParser.java
 */
package cn.game.rjserver.utils.sms.htmlparser;

import java.io.IOException;
import java.io.Serializable;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;


/**
 * @author rajar.net wzf
 * 
 *         2009-9-11
 */
public class HtmlParser implements Serializable {

	/** a long field as name serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** a WebExplore field as name webExplore */
	private WebExplore webExplore;

	private Parser parser;

	private int mediaType;

	/**
	 * containing HtmlParser object (HtmlParser constructor)
	 */
	public HtmlParser() {
	}

	/**
	 * containing HtmlParser object (HtmlParser constructor)
	 * 
	 * @param webExplore
	 */
	public HtmlParser(WebExplore webExplore) {
		this.webExplore = webExplore;
	}

	/**
	 * containing HtmlParser object (HtmlParser constructor)
	 * 
	 * @param webExplore
	 */
	public HtmlParser(String url) {
		getWebExplore().setUrl(url);
	}

	/**
	 * @return the HtmlParser's webExplore
	 */
	public WebExplore getWebExplore() {
		if (this.webExplore == null) {
			webExplore = new WebExplore();
		}
		return webExplore;
	}

	/**
	 * @param webExplore
	 *            the HtmlParser's webExplore to set
	 */
	public void setWebExplore(WebExplore webExplore) {
		this.webExplore = webExplore;
	}

	/**
	 * @return the HtmlParser's url
	 */
	public String getUrl() {
		return getWebExplore().getUrl();
	}

	/**
	 * @param url
	 *            the HtmlParser's url to set
	 */
	public void setUrl(String url) {
		getWebExplore().setUrl(url);
	}

	/**
	 * @return the HtmlParser's source
	 * @throws IOException
	 */
	public String getSource() throws IOException {
		return getWebExplore().getSource();
	}

	/**
	 * @param source
	 *            the HtmlParser's source to set
	 */
	public void setSource(String source) {
		getWebExplore().setSource(source);
	}

	/**
	 * 设置内容，不做html getmethod采集
	 * 
	 * @doc class explain:
	 * @param url
	 * @param source
	 */
	public void setUrlHtml(String url, String source) {
		getWebExplore().setUrlHtml(url, source);
	}

	/**
	 * @return the HtmlParser's agentIp
	 */
	public String getAgentIp() {
		return getWebExplore().getAgentIp();
	}

	/**
	 * @return the HtmlParser's proxy
	 */
	public int getProxy() {
		return getWebExplore().getProxy();
	}

	/**
	 * 
	 * containing a (HtmlParser)'s setAgent method
	 * 
	 * @param ip
	 * @param proxy
	 *            void
	 */
	public void setAgent(String ip, int proxy) {
		getWebExplore().setAgent(ip, proxy);
	}

	/**
	 * 
	 * containing a (HtmlParser)'s close method void
	 */
	public void close() {
		if (webExplore != null) {
			getWebExplore().close();
			webExplore = null;
		}
		if (parser != null) {
			parser = null;
		}
	}
//
//	/**
//	 * 设置提取模式（可单独使用，也可内置于Visitor中使用，提供和填充Visitor需要访问的内容） containing a (HtmlParser)'s WebHtmlWithProvider method
//	 * 
//	 * @param htmlFilter
//	 *            void
//	 */
//	public void WebHtmlWithProvider(AHtmlProvider htmlProvider) {
//		if (htmlProvider != null) {
//			htmlProvider.setHtmlParser(this);
//		}
//	}
//
//	/**
//	 * 设置html内容采集的访问模式 (主要用于如：访问内容、访问列表、访问下页地址等) 内置调用提取模式 containing a (HtmlParser)'s WebHtmlWithFilter method
//	 * 
//	 * @param htmlFilter
//	 *            void
//	 */
//	public void WebHtmlWithVisitor(CommonVisitor visitor) {
//		if (visitor != null) {
//			visitor.setHtmlParser(this);
//		}
//	}

	/**
	 * @return the HtmlParser's parser
	 */
	public Parser getParser() {
		if (parser == null)
			parser = new Parser();
		return parser;
	}

	/**
	 * @param parser
	 *            the HtmlParser's parser to set
	 */
	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public int getMediaType() {
		return mediaType;
	}

	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * containing a (HtmlParser)'s getHtmlElements method
	 * 
	 * @param html
	 *            String
	 * @param cls
	 *            exp:Div,Span
	 * @return SimpleNodeIterator
	 * @throws ParserException
	 */
	public SimpleNodeIterator getHtmlElements(String html, Class cls) throws ParserException {
		this.getParser().setResource(html);
		NodeList nodeList = this.getParser().parse(new NodeClassFilter(cls));
		return nodeList.elements();
	}

	public NodeList getHtmlElementsList(String html, Class cls) throws ParserException {
		this.getParser().setResource(html);
		NodeList nodeList = this.getParser().parse(new NodeClassFilter(cls));
		return nodeList;
	}
}
