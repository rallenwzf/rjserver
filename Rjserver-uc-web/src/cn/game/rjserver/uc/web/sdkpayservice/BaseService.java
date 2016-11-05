package cn.game.rjserver.uc.web.sdkpayservice;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class BaseService
 */
public class BaseService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static CharsetEncoder ENCODER = Charset.forName("UTF-8").newEncoder();
	public static Logger logger = Logger.getLogger(BaseService.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BaseService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.dispose(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
		this.dispose(request, response);
	}

	public void dispose(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected String getBackContent(boolean b) {
		String str = "";
		if (b) {
			str = "0";
		} else {
			str = "参数信息获取失败";
		}
		return str;
	}

	protected String getRequestURL(HttpServletRequest request) {
		if (request == null) {
			return "";
		}
		String url = "";
		String enUrl = "";
		try {
			url = request.getContextPath();
			url = url + request.getServletPath();
			url = url + "?" + request.getQueryString();
			// enUrl = java.net.URLEncoder.encode(url, "utf-8");
			enUrl = url;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return enUrl;
	}

	protected boolean isSpace(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		}
		return false;
	}
}
