/**
 * rallen wangzhifeng
 * 2011-7-14
 */
package cn.game.rjserver.framework.executor;

import java.util.Set;
import java.util.Vector;


import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Message;


/**
 * @author rallen(wangzhifeng) 2011-7-14
 */
public interface Executable {
	public final static int EXECUTOR_STATE_FAILD = -1;
	public final static int EXECUTOR_STATE_UNDO = 0;
	public final static int EXECUTOR_STATE_IN = 1;// 已接收
	public final static int EXECUTOR_STATE_DOING = 2;// 异步处理中
	public final static int EXECUTOR_STATE_SUCCS = 3;

	//
	public final static String EXECUTOR_KEY_STATE = "exec_state";
	public final static String EXECUTOR_KEY_RESULT = "exec_rs";

	/**
	 * @doc 命令执行者的启动
	 *      <p>
	 *      运行Executable中的所有Executable，子类可不重写，由父类实现
	 *      <p>
	 *      当某一个Executable处理状态为EXECUTOR_STATE_UNDO时，继续往下执行其他Executable，否者不再往下执行
	 *      <p>
	 *      当某个Executable为异步处理时，应该在处理前假定处理已经成功
	 * @param agreement
	 * @return
	 */
	public Object submitExecute(Agreement agreement, GameIoSession session);

	/**
	 * @doc 解析命令，判断是否该执行命令
	 * @param agreement
	 * @return
	 */
	public abstract boolean accept(Agreement agreement);

	/**
	 * @doc 执行命令
	 * @param agreement
	 * @param session
	 *            只用mina时，session为IoSession对象
	 * @return
	 */
	public abstract Object execute(Agreement agreement, GameIoSession session);

	/**
	 * @doc 获取执行者
	 * @param exec
	 * @param agreement
	 * @return
	 */
	public Executable getExecuteAccepter(Executable exec, Agreement agreement);

	/**
	 * @doc 发送消息
	 * @param msg
	 * @param session
	 */
	public void sendMessage(Agreement ag, GameIoSession session);

	public void setNoDelay(boolean b);

	public void addExecutor(Executable exec);

	public void removeExecutor(Executable exec);

	public Vector<Executable> getExecutors();

	public String getExecutorSign();

	public void setExecutorSign(String sign);

	public void dispose();

	public int getExecuteState();

	public void setExecuteState(int state);
}
