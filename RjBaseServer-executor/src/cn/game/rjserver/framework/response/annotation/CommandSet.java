/**
 * rallen(wzf)
 */
package cn.game.rjserver.framework.response.annotation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.game.rjserver.basicutils.lang.ClassUtil;

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
	private static final Map<Integer, CommandAction> cmdCache = new HashMap<Integer, CommandAction>();

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
	public static void load(Class claRef) throws Exception {
		Package pack = claRef.getPackage();
		load(pack);
	}

	public static void load(Package pack) throws Exception {
		Set<Class<?>> allClasses = ClassUtil.getClasses(pack);

		for (Class<?> clazz : allClasses) {
			Cmd cmd = clazz.getAnnotation(Cmd.class);
			if (cmd != null) {
				try {
					cmdCache.put(cmd.cmdid(), (CommandAction) clazz.newInstance());

					System.out.println(cmd.cmdid() + "___" + cmd.desc());
				} catch (Exception e) {
					log.error("code : " + cmd.cmdid() + " , " + e.getMessage());
					e.printStackTrace();
				}
			}
		}

		log.info("cmdCache size : " + cmdCache.size());
	}

	public void addCmd(int cmdid, CommandAction cmd) {
		if (cmdCache.containsKey(cmdid)) {
			log.warn("code is cached cmd=" + cmdCache.get(cmdid).getClass().getName());
		} else {
			cmdCache.put(cmdid, cmd);
		}
	}

	/**
	 * 缓存中获取接口命令
	 * 
	 * @param code
	 * @return
	 */
	public static CommandAction getCommand(int cmdid) {
		return cmdCache.get(cmdid);
	}

	/**
	 * 是否存在接口命令
	 * 
	 * @param code
	 * @return
	 */
	public static boolean isCommand(int cmdid) {
		return cmdCache.containsKey(cmdid);
	}
}
