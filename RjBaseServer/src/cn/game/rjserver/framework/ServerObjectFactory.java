/**
 * 
 */
package cn.game.rjserver.framework;

/**
 * @author wzf(rallen) 部分业务定制类获取接口
 */
public class ServerObjectFactory {
	private static ServerObjectFactory factory;

	/**
	 * @return
	 */
	public static ServerObjectFactory getInstance() {
		if (factory == null) {
			factory = new ServerObjectFactory();
		}
		return factory;
	}
}
