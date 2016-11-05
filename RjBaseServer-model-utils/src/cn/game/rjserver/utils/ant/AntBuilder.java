package cn.game.rjserver.utils.ant;
//package cn.game.pub.server.ant;
//
//import java.io.File;
//import org.apache.tools.ant.BuildException;
//import org.apache.tools.ant.DefaultLogger;
//import org.apache.tools.ant.Project;
//import org.apache.tools.ant.ProjectHelper;
//
//public class AntBuilder {
//
//	/**
//	 * 执行build.xml文件
//	 * 
//	 * @param build
//	 *            build.xml文件
//	 * @param level
//	 *            日志输出级别(Project.MSG_INFO)
//	 */
//	public static void exeBuildFile(String build, int level) {
//		File buildFile = new File(build);
//		Project p = new Project();
//		// 添加日志输出
//		DefaultLogger consoleLogger = new DefaultLogger();
//		consoleLogger.setErrorPrintStream(System.err);
//		consoleLogger.setOutputPrintStream(System.out);
//		// 输出信息级别
//		consoleLogger.setMessageOutputLevel(level);
//		p.addBuildListener(consoleLogger);
//		try {
//			p.fireBuildStarted();
//			p.init();
//			ProjectHelper helper = ProjectHelper.getProjectHelper();
//			helper.parse(p, buildFile);
//			p.executeTarget(p.getDefaultTarget());
//			p.fireBuildFinished(null);
//		} catch (BuildException e) {
//			p.fireBuildFinished(e);
//		}
//	}
// }