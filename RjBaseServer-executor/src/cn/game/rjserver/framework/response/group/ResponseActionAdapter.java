/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.response.group;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.game.rjserver.framework.executor.AbstractExecutor;
import cn.game.rjserver.framework.executor.Executable;
import cn.game.rjserver.framework.executor.GameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;

/**
 * @doc 响应处理的适配器
 * @author Administrator
 */
public class ResponseActionAdapter extends AbstractExecutor implements ResponseAction {
	public static Logger logger = Logger.getLogger(ResponseActionAdapter.class.getName());

	public ResponseActionAdapter() {

	}

	@Override
	public void messageReceived(GameIoSession session, Agreement ag) {
		// TODO Auto-generated method stub
		try {

			Executable exec = this.getExecuteAccepter(this, ag);
			if (exec == null) {
				logger.debug("警告：发现非法链接······未查找到执行者！！！！" + ag.getCmdId());
			} else {
				logger.debug(exec.getClass().getName() + " 执行者..." + ag.getCmdId());
				Object rsObj = exec.submitExecute(ag, session);
				if (rsObj != null) {
					logger.debug("执行者。。。。返回_" + rsObj);
				}
			}
		} catch (Exception e) {
			logger.debug(e);
			e.printStackTrace();
		}
	}

	@Override
	public void messageCallbacked(GameIoSession session, Agreement ag) {
		// TODO Auto-generated method stub
		this.sendMessage(ag, session);
	}

	@Override
	public Object execute(Agreement agreement, GameIoSession session) {
		// TODO Auto-generated method stub
		logger.debug(agreement.toString());
		return null;
	}

	@Override
	public Executable getExecuteAccepter(Executable exec, Agreement agreement) {
		// TODO Auto-generated method stub
		// 该执行器 不接收命令，传递给下一个执行器
		for (Executable e : getExecutors()) {
			Executable ea = e.getExecuteAccepter(e, agreement);
			if (ea == null) {
				continue;
			} else {
				ea.setExecuteState(EXECUTOR_STATE_IN);
				return ea;
			}
		}
		// 查看自己作为分发器是否能执行命令
		boolean b = exec.accept(agreement);
		if (!b) {
			return null;
		} else {
			// 该执行器已接收命令执行完成
			exec.setExecuteState(EXECUTOR_STATE_IN);
			return exec;
		}
	}

	@Override
	public boolean accept(Agreement agreement) {
		// TODO Auto-generated method stub
		return true;
	}

}
