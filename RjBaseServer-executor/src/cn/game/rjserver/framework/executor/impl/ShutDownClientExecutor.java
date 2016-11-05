/**
 * rallen(wangzhifeng)
 * 
 */
package cn.game.rjserver.framework.executor.impl;


import cn.game.rjserver.basicutils.exception.ExceptionUtils;
import cn.game.rjserver.framework.UserManager;
import cn.game.rjserver.framework.executor.AbstractExecutor;
import cn.game.rjserver.framework.executor.GameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;



/**
 * @author rallen 断开客户端连接
 *         <p>
 *         不需要判断命令，不能放入迭代使用
 */
public class ShutDownClientExecutor extends AbstractExecutor {

	@Override
	public boolean accept(Agreement agreement) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 关闭连接，清除session及用戶信息
	 */
	@Override
	public Object execute(Agreement agreement, GameIoSession session) {
		// TODO Auto-generated method stub
		if (session != null) {
			try {
				UserManager.getInstance().removeUser(session);
			} catch (Exception e) {
				logger.error(ExceptionUtils.getTrace(e));

			} finally {
				session.close(true);
			}
			this.setExecuteState(EXECUTOR_STATE_SUCCS);
		}
		return null;
	}

	/**
	 * @param agreement
	 * @param session
	 * @param immediately 是否立即关闭连接
	 * @return
	 */
	public Object execute(Agreement agreement, GameIoSession session,
			boolean immediately) {
		// TODO Auto-generated method stub
		if (session != null) {
			try {
				UserManager.getInstance().removeUser(session, immediately);
			} catch (Exception e) {
				logger.error(ExceptionUtils.getTrace(e));

			} finally {
				session.close(immediately);
			}
			this.setExecuteState(EXECUTOR_STATE_SUCCS);
		}
		return null;
	}
}
