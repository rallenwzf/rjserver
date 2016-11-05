/*********************************************
    Copyright (c) 2009 by rayjar.net
 *********************************************/

/**
 * RAYJAR_ROBOT 
 * 2009-9-10
 * com.rajar.robot.spider.WebExplore.java
 */
package cn.game.rjserver.utils.sms.htmlparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.SimpleNodeIterator;

/**
 * @author rajar.net wzf 网页html获取 2009-9-10
 */
public class WebExplore implements Serializable {

	public static String DEFAULT_SQL_ENCODE = "utf-8";

	/** a int field as name CONNECTION_TIMEOUT */
	public static int CONNECTION_TIMEOUT = 30 * 1000;

	/** a int field as name SOCKET_TIMEOUT */
	public static int SOCKET_TIMEOUT = 20 * 1000;

	/** a String field as name DEFAULT_ENCODE */
	public static String DEFAULT_ENCODE = "gb2312";

	/** a int field as name MAX_CONNECTION_PERHOST */
	public static int MAX_CONNECTION_PERHOST = 5;

	/** a int field as name MAX_TOTAL_CONNECTIONS */
	public static int MAX_TOTAL_CONNECTIONS = 40;

	/** a Logger field as name logger */
	static Logger logger = Logger.getLogger(WebExplore.class.getName());

	/**
	 * 获得ConnectionManager，设置相关参数 a MultiThreadedHttpConnectionManager field as
	 * name manager
	 */
	private MultiThreadedHttpConnectionManager manager;

	/** a String field as name agentIp */
	private String agentIp;

	/** a int field as name proxy */
	private int proxy;

	/** a HttpClient field as name httpClient */
	private HttpClient httpClient;

	/** a List<Header> field as name mHeaderList */
	private List<Header> headerList;

	/** a String field as name url */
	private String url;

	/** a String field as name source */
	private String source;

	private HttpConfiguration httpConfiguration;

	// protected
	protected String encode;

	HttpMethodBase httpMethod;

	/**
	 * containing WebExplore object (WebExplore constructor)
	 */
	public WebExplore() {
	}

	/**
	 * containing WebExplore object (WebExplore constructor)
	 * 
	 * @param manager
	 */
	public WebExplore(MultiThreadedHttpConnectionManager manager) {
		this.manager = manager;
	}

	/**
	 * containing WebExplore object (WebExplore constructor)
	 * 
	 * @param manager
	 * @param agentIp
	 * @param proxy
	 */
	public WebExplore(MultiThreadedHttpConnectionManager manager, String agentIp, int proxy) {
		this.manager = manager;
		this.agentIp = agentIp;
		this.proxy = proxy;
	}

	/**
	 * @return the WebExplore's source
	 * @throws IOException
	 */
	public String getSource() throws IOException {
		if (source == null) {
			source = getGetResponse();
			if (source == null)
				return source;
			source = source.replaceAll("&nbsp;", " ");
			source = source.replaceAll("&amp;", "&");
			// source = source.replaceAll("&lt;", "<");
			// source = source.replaceAll("&gt;", ">");
			source = source.replaceAll("&ldquo;", "“");
			source = source.replaceAll("&rdquo;", "”");
			source = source.replaceAll("&quot;", "\"");
			source = source.replaceAll("&raquo;", "");
			source = source.replaceAll("&#9743;", "");
			source = source.replaceAll("<cite>|</cite>", "");
			source = source.replaceAll("<em>|</em>", "");
			sourceBaseSplit("tbody", "table");
			sourceBaseSplit("<br>", "\n");
			sourceBaseSplit("<br/>", "\n");
			sourceBaseSplit("</p>", "\n</p>");
		}
		return source;
	}

	/**
	 * 把reg替换为tag containing a (WebExplore)'s sourceBaseSplit method
	 * 
	 * @param reg
	 * @param tag
	 *            void
	 */
	private void sourceBaseSplit(String reg, String tag) {
		Pattern p_script = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(source);
		source = m_script.replaceAll(tag); // 过滤script标签
	}

	/**
	 * @param source
	 *            the WebExplore's source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the WebExplore's url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the WebExplore's url to set
	 */
	public void setUrl(String url) {
		if (url == null)
			throw new IllegalArgumentException("url cannot be null");
		this.url = url;
		this.headerList = null;
		this.source = null;
	}

	public void setUrlHtml(String url, String source) {
		this.url = url;
		this.source = source;
	}

	/**
	 * @return the WebExplore's manager
	 */
	public MultiThreadedHttpConnectionManager getManager() {
		if (manager == null) {
			manager = new MultiThreadedHttpConnectionManager();
			manager.getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
			manager.getParams().setSoTimeout(SOCKET_TIMEOUT);
			manager.getParams().setDefaultMaxConnectionsPerHost(MAX_CONNECTION_PERHOST);
			manager.getParams().setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
		}
		return manager;
	}

	/**
	 * @param manager
	 *            the WebExplore's manager to set
	 */
	public void setManager(MultiThreadedHttpConnectionManager manager) {
		this.manager = manager;
	}

	/**
	 * @return the WebExplore's agentIp
	 */
	public String getAgentIp() {
		return agentIp;
	}

	/**
	 * @return the WebExplore's proxy
	 */
	public int getProxy() {
		return proxy;
	}

	/**
	 * 
	 * containing a (WebExplore)'s setAgent method
	 * 
	 * @param ip
	 * @param proxy
	 *            void
	 */
	public void setAgent(String ip, int proxy) {
		if (ip != null && !ip.equals("") && proxy > 0) {
			this.agentIp = ip;
			this.proxy = proxy;
			logger.debug("使用代理服务器链接 ip:" + agentIp + "  端口:" + proxy);
			getHttpClient().getHostConfiguration().setProxyHost(new ProxyHost(agentIp, proxy));
		}
	}

	/**
	 * @return the WebExplore's httpClient
	 */
	public HttpClient getHttpClient() {
		if (httpClient == null) {
			// default httpClient
			httpClient = new HttpClient(getManager());
			httpClient
					.getParams()
					.setParameter(
							"httpclient.useragent",
							"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB6.5; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; InfoPath.2; IEShow Toolbar; IEShow LinkWanToolBar)");
			setAgent(agentIp, proxy);
		}
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public List<Header> getHeaderList() {

		if (headerList == null) {
			// set default Header
			headerList = new ArrayList<Header>();
			headerList.add(new Header("Accept", "image/gif, image/x-xbitmap, image/jpeg, " + "image/pjpeg, application/xaml+xml, application/vnd.ms-xpsdocument, "
					+ "application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, " + "application/vnd.ms-powerpoint, application/msword, application/x-silverlight, "
					+ "application/QVOD, */*"));
			headerList.add(new Header("Accept-Language", "zh-cn"));
			// headerList.add(new Header("Accept-Encoding", "gzip, deflate"));
			headerList
					.add(new Header(
							"User-Agent",
							"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB6.5; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; InfoPath.2; IEShow Toolbar; IEShow LinkWanToolBar)"));
			// headerList.add(new Header("Connection", "Keep-Alive"));
		}
		return headerList;
	}

	public void setHeaderList(List<Header> headerList) {
		this.headerList = headerList;
	}

	/**
	 * @doc class explain:
	 * @return
	 * @throws IOException
	 */
	public String getGetResponse() throws IOException {
		if (url == null || url.trim().equals("")) {
			return null;
		}
		GetMethod get = new GetMethod(url);
		get.setFollowRedirects(getHttpConfiguration().isFollowRedirects());
		return getResponse(get);
	}

	/**
	 * @doc class explain:
	 * @return
	 * @throws IOException
	 */
	public String getPostResponse() throws IOException {
		if (url == null || url.trim().equals("")) {
			return null;
		}
		PostMethod post = new PostMethod(url);
		post.setFollowRedirects(getHttpConfiguration().isFollowRedirects());
		return getResponse(post);
	}

	/**
	 * @doc class explain:
	 * @param nameValuePair
	 * @return
	 * @throws IOException
	 */
	public String getPostResponse(NameValuePair[] nameValuePair) throws IOException {
		if (url == null || url.trim().equals("")) {
			return null;
		}
		PostMethod post = new PostMethod(url);
		if (nameValuePair != null) {
			post.setRequestBody(nameValuePair);
		}

		return getResponse(post);
	}

	/**
	 * get html by EntityEnclosingMethod //synchronized
	 * 
	 * @doc class explain:
	 * @param method
	 * @return
	 * @throws IOException
	 */
	public String getResponse(HttpMethodBase method) throws IOException {
		this.setHttpMethod(method);
		StringBuffer resultBuffer = new StringBuffer();
		method.setFollowRedirects(getHttpConfiguration().isFollowRedirects());
		String result = null;
		try {
			if (getHeaderList() != null) {
				for (Iterator<Header> iter = getHeaderList().iterator(); iter.hasNext();) {
					Header header = (Header) iter.next();
					method.addRequestHeader(header);
				}
			}
			/** 做post登录时需要存储cookies */
			// Cookie[] cookies = this.getHttpClient().getState().getCookies();
			// this.getHttpClient().getState().addCookies(cookies);
			// String cos = null;
			// for (int i = 0; i < cookies.length; i++) {
			// // logger.debug("________" + cookies[i].getName() + "---v=" +
			// // cookies[i].getValue());
			// if (i > 0) {
			// cos += ";" + cookies[i].getName() + "="
			// + cookies[i].getValue();
			// } else {
			// cos = cookies[i].getName() + "=" + cookies[i].getValue();
			// }
			// }
			// if (cos != null) {
			// method.addRequestHeader(new Header("Cookie", cos));
			// // logger.debug("addRequestHeader_Cookie=" + cos);
			// }

			int statusCode = getHttpClient().executeMethod(method);
			method.setFollowRedirects(false);
			if (method.getFollowRedirects()) {
				statusCode = HttpStatus.SC_OK;
			}
			if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_PARTIAL_CONTENT) {
				// 取内容
				InputStream in1 = method.getResponseBodyAsStream();
				String responseCharSet = method.getResponseCharSet();
				Header hce = method.getResponseHeader("Content-Encoding");
				if (null != hce) {
					// logger.debug(hce.getName() + ":" + hce.getValue());
					if (hce.getValue().toLowerCase().equals("gzip")) {
						in1 = new GZIPInputStream(method.getResponseBodyAsStream());
					}
				}
				// getHttpClient().getParams().setContentCharset(null);
				// getHttpClient().getParams().setContentCharset(responseCharSet);
				BufferedReader in = new BufferedReader(new InputStreamReader(in1, responseCharSet));
				logger.debug("method.getResponseCharSet()=" + responseCharSet);
				String inputLine = null;
				while ((inputLine = in.readLine()) != null) {
					resultBuffer.append(inputLine.trim());
					resultBuffer.append("\n");
				}
				in.close();

				String source = resultBuffer.toString();
				// return source;

				// iso-8859-1 is the default reading encode
				Header header = method.getResponseHeader("Content-Type");
				String headerContentType = null;
				if (header != null) {
					headerContentType = header.getValue();
				}
				method.releaseConnection();
				// result = source;
				result = this.converterStringCode(source, headerContentType, responseCharSet);
			} else if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 从头中取出转向的地址
				Header locationHeader = method.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					logger.debug(url + " 获取失败，当前地址将跳转到" + location);
				} else {
					logger.debug("Location field value is null.");
				}
			} else {
				logger.debug("statusCode=" + statusCode);
			}

			return result;
		} finally {
			method.releaseConnection();
		}
	}

	/**
	 * set default value containing a (WebExplore)'s reset method void
	 */
	public void close() {
		httpClient = null;
	}

	/**
	 * 对文档进行编码转换
	 * 
	 * @doc class explain:
	 * @param source
	 * @param headerContentType
	 * @param responseCharSet
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String converterStringCode(String source, String headerContentType, String responseCharSet) {
		if (source == null || source.trim().equals("")) {
			logger.debug(url + "未获取到内容" + responseCharSet);
			return null;
		}
		encode = null;
		if (headerContentType != null && !headerContentType.trim().equals("")) {
			String headerContentTypeCode = "";
			try {
				headerContentTypeCode = headerContentType.split("charset=")[1].trim();
				encode = headerContentTypeCode;
			} catch (Exception e) {
			}
			String httpEquivContentTypeCode = getMateCharset(source);
			if (httpEquivContentTypeCode != null && !httpEquivContentTypeCode.equals("") && !httpEquivContentTypeCode.equals(headerContentTypeCode)) {
				encode = httpEquivContentTypeCode;
			}
		}

		if (this.getHttpConfiguration().getDefaultCode() != null) {
			encode = this.getHttpConfiguration().getDefaultCode();
		} else {
			// if (encode == null) {
			// encode = DEFAULT_ENCODE;
			// }
		}
		if (encode != null && !encode.equalsIgnoreCase(responseCharSet)) {
			logger.debug("内容转码:" + responseCharSet + "——" + encode);
			if (responseCharSet != null && encode != null && !encode.trim().equals("") && encode.length() < 10) {
				try {
					System.out.println("内容转码:" + responseCharSet + "——" + encode);
					source = new String(source.getBytes(responseCharSet), this.encode);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		logger.debug("内容获取完成 ，使用的编码:" + this.encode + ",url=" + url);
		return source;
	}

	/**
	 * @param source
	 * @return
	 */
	private String getMateCharset(String source) {
		String httpEquivContentTypeCode = "";
		try {
			// 取html meta页面编码
			SimpleNodeIterator metaTags = ParserTool.getHtmlElements(source, MetaTag.class);
			if (metaTags == null) {
				return source;
			}
			boolean metaEncode = false;
			while (metaTags.hasMoreNodes()) {
				MetaTag p_Tag = (MetaTag) metaTags.nextNode();
				String httpEquiv = p_Tag.getHttpEquiv();
				if (httpEquiv != null && httpEquiv.equalsIgnoreCase("Content-Type")) {
					// System.out.println("----"+httpEquiv+"  "+p_Tag.getAttribute("content"));
					httpEquivContentTypeCode = p_Tag.getAttribute("content").split("charset=")[1].trim();
					metaEncode = true;
					return httpEquivContentTypeCode;
				}
			}
			if (metaEncode == false) {
				metaTags = ParserTool.getHtmlElements(source, MetaTag.class);
				while (metaTags.hasMoreNodes()) {
					MetaTag p_Tag = (MetaTag) metaTags.nextNode();
					String mcode = p_Tag.getAttribute("charset");
					logger.debug("mcode--" + mcode);
					if (mcode != null && !mcode.equals("")) {
						return mcode;
					}
				}
			}
		} catch (Exception es) {
		}
		return null;
	}

	public String getEncode() {
		return encode;
	}

	public HttpConfiguration getHttpConfiguration() {
		if (httpConfiguration == null)
			httpConfiguration = new HttpConfiguration();
		return httpConfiguration;
	}

	public void setHttpConfiguration(HttpConfiguration httpConfiguration) {
		this.httpConfiguration = httpConfiguration;
	}

	public HttpMethodBase getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethodBase httpMethod) {
		this.httpMethod = httpMethod;
	}
}
