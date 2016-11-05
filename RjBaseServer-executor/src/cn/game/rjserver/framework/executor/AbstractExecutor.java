/**
 * rallen wangzhifeng
 * 2011-7-14
 */
package cn.game.rjserver.framework.executor;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import cn.game.rjserver.framework.UserManager;
import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Message;
import cn.game.rjserver.framework.protocol.MessageRequestQueue;
import cn.game.rjserver.framework.protocol.MessageResponseQueue;

/**
 * @author rallen(wangzhifeng) 2011-7-14
 *         <p>
 *         提交执行器执行
 *         <p>
 *         调用submitExecute
 */
public abstract class AbstractExecutor implements Executable {
	public static Logger logger = Logger.getLogger(Executable.class.getName());
	private String sign;
	private int state = EXECUTOR_STATE_SUCCS;// 初始假定处理成功，防止异步处理时产生事件传递混乱
	private Vector<Executable> execs = new Vector<Executable>();
	private boolean boDelay;

	public AbstractExecutor() {

	}

	public AbstractExecutor(Executable exec) {
		this.addExecutor(exec);
	}

	public AbstractExecutor(Set<Executable> execs) {
		this.execs.addAll(execs);
	}

	@Override
	public Object submitExecute(Agreement agreement, GameIoSession session) {
		// TODO Auto-generated method stub
		Object obj = null;
		Executable ec = this.getExecuteAccepter(this, agreement);
		if (ec != null) {
			obj = ec.execute(agreement, session);
			// 状态遗传給父级
			this.setExecuteState(ec.getExecuteState());
		} else {
			// 未找到合适的执行器，设置未接收
			this.setExecuteState(EXECUTOR_STATE_UNDO);
		}
		return obj;
	}

	/**
	 * @doc 获取合适的执行器
	 * @param exec
	 * @param agreement
	 * @return
	 */
	public Executable getExecuteAccepter(Executable exec, Agreement agreement) {
		boolean b = exec.accept(agreement);
		if (!b) {
			// 该执行器 不接收命令，传递给下一个执行器
			for (Executable e : execs) {
				Executable ea = e.getExecuteAccepter(e, agreement);
				if (ea == null) {
					continue;
				} else {
					ea.setExecuteState(EXECUTOR_STATE_IN);
					return ea;
				}
			}
			return null;
		} else {
			// 该执行器已接收命令执行完成
			exec.setExecuteState(EXECUTOR_STATE_IN);
			return exec;
		}
	}

	public void addQueueRequest(Message msg) {
		// TODO Auto-generated method stub
		MessageRequestQueue.getInstance().add(msg);
	}

	public void addQueueResponse(Message msg) {
		// TODO Auto-generated method stub
		MessageResponseQueue.getInstance().add(msg);
	}

	/**
	 * @doc 解析命令，判断是否可以接收命令
	 *      <p>
	 *      子类实现具体的处理逻辑
	 */
	public abstract boolean accept(Agreement agreement);

	/**
	 * @doc 执行器执行
	 *      <p>
	 *      子类实现具体的处理逻辑
	 */
	public abstract Object execute(Agreement agreement, GameIoSession session);

	@Override
	public void addExecutor(Executable exec) {
		// TODO Auto-generated method stub
		this.execs.add(exec);
	}

	@Override
	public Vector<Executable> getExecutors() {
		// TODO Auto-generated method stub
		return this.execs;
	}

	@Override
	public String getExecutorSign() {
		// TODO Auto-generated method stub
		if (sign == null) {
			sign = new Random().nextLong() + "";
		}
		return (sign);
	}

	@Override
	public void removeExecutor(Executable exec) {
		// TODO Auto-generated method stub
		this.execs.remove(exec);
	}

	@Override
	public void setExecutorSign(String sign) {
		// TODO Auto-generated method stub
		this.sign = sign;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		for (Executable e : execs) {
			e.dispose();
		}
		execs.clear();
	}

	@Override
	public int getExecuteState() {
		// TODO Auto-generated method stub
		return this.state;
	}

	@Override
	public void setExecuteState(int state) {
		// TODO Auto-generated method stub
		this.state = state;
	}

	@Override
	public void setNoDelay(boolean b) {
		// TODO Auto-generated method stub
		this.boDelay = b;
	}

	/**
	 * 发送消息
	 */
	@Override
	public void sendMessage(Agreement ag, GameIoSession session) {
		// TODO Auto-generated method stub
		if (session != null && ag != null) {
			try {
				// logger.debug("{sessionID=" + session.getId()
				// + "} session.write:" + ag.getCmdID() + " message.size="
				// + MessageResponseQueue.getInstance().size());
				session.write(ag);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
	}

	/**
	 * 发送消息
	 */
	public void sendMessage(Agreement ag, int uid) {
		// TODO Auto-generated method stub
		GameIoSession session = UserManager.getInstance().getUserSession(uid + "");
		sendMessage(ag, session);
	}

	/**
	 * 发送消息
	 */
	public void sendMessage(Message msg) {
		// TODO Auto-generated method stub
		MessageResponseQueue.getInstance().add(msg);
	}
}
