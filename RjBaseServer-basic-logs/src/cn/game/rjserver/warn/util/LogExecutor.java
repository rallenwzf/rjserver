/**
 * 
 */
package cn.game.rjserver.warn.util;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.lang.Runnable;

/**
 * @author Administrator
 *         <p>
 *         Executor线程池
 */
public class LogExecutor {
	private Executor executor;

	/**
	 * 设置执行器
	 * 
	 * @param executor
	 */
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public Executor getExecutor() {
		if (executor == null) {
			executor = Executors.newCachedThreadPool();
		}
		return executor;
	}

	public ExecutorService getExecutorService() {
		return (ExecutorService) getExecutor();
	}

	/**
	 * 添加任务执行
	 * 
	 * @param command
	 */
	public void addTask(Runnable command) {
		this.getExecutor().execute(command);
	}

}
