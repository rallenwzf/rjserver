/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.log.push;

import cn.game.rjserver.log.entity.HyLog;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public interface Pusher {
	/**
	 * @param entity 输出读取的日志
	 */
	public void pushHiyouLog(HyLog entity);
}
