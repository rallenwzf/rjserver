/**
 * 
 */
package cn.game.rjserver.uc.web.serverlet.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.PropertyConfigurator;

import cn.game.rjserver.uc.InitDcsConfig;
import cn.game.rjserver.uc.web.actioncmd.CommandSet;
import cn.game.rjserver.uc.web.sdkpayservice.App;
import cn.game.rjserver.uc.web.sdkpayservice.heyouxi.HeyouxiConsumeCodeUtils;

/**
 * @author dandan
 * 
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {
	private static ServletContext context = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		context = arg0.getServletContext();

		initLog4j();

		InitDcsConfig.setC3p0dbConf(context.getRealPath("/WEB-INF/conf/c3p0db.properties"));
		InitDcsConfig.getInstace();

		try {
			CommandSet.load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String pushUrl = context.getInitParameter("pushUrl");
		if (pushUrl != null && !pushUrl.trim().equals("")) {
			App.pushUrl = pushUrl.split(",");
		}
		String pushMethod = context.getInitParameter("pushMethod");
		if (pushMethod != null && !pushMethod.trim().equals("")) {
			App.pushMethod = pushMethod.split(",");
		}

		//
		String prefix = context.getRealPath(File.separator);
		initDatas(prefix);

	}

	/*
	 * init log4j
	 */
	public void initLog4j() {
		// LOG or WARN
		String configPath = context.getRealPath("/WEB-INF/conf");
		System.out.println(configPath);
		PropertyConfigurator.configure(configPath + "/log4j.properties");

	}

	public void initDatas(String prefix) {
		// 加载计费文件
		// String xml = prefix + "WEB-INF/conf/heyouxi_consumecode.xml";
		// HeyouxiConsumeCodeUtils.load(xml, "UTF-8");
	}

}
