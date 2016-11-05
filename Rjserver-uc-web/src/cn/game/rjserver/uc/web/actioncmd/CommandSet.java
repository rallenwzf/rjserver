/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.actioncmd;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.game.rjserver.basicutils.lang.ClassUtil;
import cn.game.rjserver.uc.web.Config;

/**
 * 接口命令管理器
 * 
 * @author rallen
 * 
 */
public final class CommandSet {

	private static Logger log = Logger.getLogger(CommandSet.class);

	/**
	 * 缓存接口命令对象
	 **/
	private static final Map<Integer, ActionCommand> cmdCache = new HashMap<Integer, ActionCommand>();

	/**
	 * 该类不能实例化
	 */
	private CommandSet() {

	}

	// --------------------------------------------------------------- Public

	/**
	 * 加载命令集合
	 * 
	 * @param configFile
	 */
	public static void load() throws Exception {
		Package pack = Config.class.getPackage();
		Set<Class<?>> allClasses = ClassUtil.getClasses(pack);

		for (Class<?> clazz : allClasses) {
			Cmd cmd = clazz.getAnnotation(Cmd.class);
			if (cmd != null) {
				try {
					ActionCommand ac = (ActionCommand) clazz.newInstance();
					cmdCache.put(cmd.action(), ac);
					System.out.println("action id=" + cmd.action() + " |class=" + ac.getClass().getName() + " |name=" + cmd.desc());
				} catch (Exception e) {
					log.error("code : " + cmd.action() + " , " + e.getMessage());
					e.printStackTrace();
				}
			}
		}

		log.info("cmdCache size : " + cmdCache.size());
	}

	public static void load(Package pack) throws Exception {
		Set<Class<?>> allClasses = ClassUtil.getClasses(pack);

		for (Class<?> clazz : allClasses) {
			Cmd cmd = clazz.getAnnotation(Cmd.class);
			if (cmd != null) {
				try {
					cmdCache.put(cmd.action(), (ActionCommand) clazz.newInstance());
				} catch (Exception e) {
					log.error("code : " + cmd.action() + " , " + e.getMessage());
					e.printStackTrace();
				}
			}
		}

		log.info("cmdCache size : " + cmdCache.size());
	}

	public void addCmd(int action, ActionCommand cmd) {
		if (cmdCache.containsKey(action)) {
			log.warn("code is cached cmd=" + cmdCache.get(action).getClass().getName());
		} else {
			cmdCache.put(action, cmd);
		}
	}

	/**
	 * 缓存中获取接口命令
	 * 
	 * @param code
	 * @return
	 */
	public static ActionCommand getCommand(int action) {
		return cmdCache.get(action);
	}

	/**
	 * 是否存在接口命令
	 * 
	 * @param code
	 * @return
	 */
	public static boolean isCommand(int action) {
		return cmdCache.containsKey(action);
	}
}
