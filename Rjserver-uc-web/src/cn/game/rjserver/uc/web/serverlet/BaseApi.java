package cn.game.rjserver.uc.web.serverlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.game.rjserver.uc.web.Config;
import cn.game.rjserver.uc.web.actioncmd.CommandSet;

/**
 * Servlet implementation class UserApi
 */
public class BaseApi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(BaseApi.class);

	/**
	 * Default constructor.
	 */
	public BaseApi() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.dipose(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.dipose(request, response);
	}

	protected void dipose(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = null;
		try {
			request.setCharacterEncoding("UTF-8");
			logger.debug("request：" + request.getRequestURI() + "?" + request.getQueryString());
			System.out.println("request：" + request.getRequestURI() + "?"
					+ request.getQueryString());
			out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
			String pwd = request.getParameter("pwd");

			if (pwd == null && request.getRequestURI().indexOf("2") > 23) {
				String action = request.getRequestURI().substring(24, 27);
				System.out.println("__________action:" + action);
				String message = CommandSet.getCommand(Integer.parseInt(action)).execute(request);
				StringBuffer sf = new StringBuffer();
				// sf.append(getSuccXml(message));
				sf.append(message);
				out.write(sf.toString());
				out.flush();
				return;
			} else if (pwd == null || !pwd.equals(getCommunicationPwd())) {

				out.write(getErrorXml(1, "校验码错误"));
				out.flush();
			} else {
				String action = request.getParameter("action");// cmd
				if (action == null || !CommandSet.isCommand(Integer.parseInt(action))) {
					out.write(getErrorXml(2, "无法查找到请求action处理，action=" + action));
					out.flush();
				} else {
					String message = CommandSet.getCommand(Integer.parseInt(action)).execute(
							request);

					StringBuffer sf = new StringBuffer();
					sf.append(getSuccXml(message));
					out.write(sf.toString());
					out.flush();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write(getErrorXml(-1, "请求异常error=" + e.getMessage()));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	protected String getCommunicationPwd() {
		return Config.UC_Api_Pwd;
	}

	protected String getErrorXml(int code, String error) {
		StringBuffer sf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sf.append("<response>");
		sf.append("<resultCode>");
		sf.append(code);
		sf.append("</resultCode>");
		sf.append("<message>");
		sf.append(error);
		sf.append("</message>");
		sf.append("</response>");
		return sf.toString();
	}

	protected String getSuccXml(String message) {
		StringBuffer sf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sf.append("<response>");
		sf.append("<resultCode>");
		sf.append(0);
		sf.append("</resultCode>");
		sf.append("<message>");
		sf.append(message);
		sf.append("</message>");
		sf.append("</response>");
		return sf.toString();
	}

}
