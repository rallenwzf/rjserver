/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.apache.log4j.Logger;

import cn.game.rjserver.basicutils.lang.DateUtils;




/**
 * 系统定时任务
 * 
 * @author Administrator
 * 
 */
public class SystemTask {
	static Logger logger = Logger.getLogger(SystemTask.class.getName());
	private Timer timer;
	private TimerTask timerTask;
	private boolean run;

	/**
	 * 每天几点执行
	 * 
	 * @param hour
	 * @param now
	 *            是否立即启动
	 */
	public void taskRun(int hour, boolean now) {
		if (timer == null) {
			timer = new Timer();
		}
		if (timerTask == null) {
			timerTask = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					logger.debug("定时任务开始执行...");
				}
			};
		}
		timer.schedule(timerTask, DateUtils.getTaskSartDate(hour, now),
				24 * 60 * 60 * 1000);
		this.run = true;
	}

	/**
	 * 整点开始执行，
	 * 
	 * @param period
	 *            间隔时间
	 */
	public void taskRun(long period, boolean isFirstRun) {
		if (timer == null) {
			timer = new Timer();
		}
		if (timerTask == null) {
			timerTask = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					logger.debug("定时任务开始执行...");
				}
			};
		}
		Date date = new Date();
		int currentHour = new Date().getHours();
		// System.out.println(currentHour);
		Calendar currentTime = Calendar.getInstance();
		currentTime.setTime(date);
		currentTime.set(Calendar.HOUR_OF_DAY, currentHour);
		currentTime.set(Calendar.MINUTE, 0);
		currentTime.set(Calendar.SECOND, 0);
		// currentTime.set(Calendar.MILLISECOND, 0);
		// Date nextHour = currentTime.getTime();
		// System.out.println(nextHour.toLocaleString());
		// if (isFirstRun) {
		// timerTask.run();
		// }
		timer.scheduleAtFixedRate(timerTask, currentTime.getTime(), period);
		this.run = true;
	}

	/**
	 * 每天循环执行几次
	 * 
	 * @param dayTimes
	 */
	public void taskRun(int dayTimes) {
		if (timer == null) {
			timer = new Timer();
		}
		if (timerTask == null) {
			timerTask = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					logger.debug("循环间隔任务开始执行...");
				}
			};
		}
		timer.schedule(timerTask, 10 * 1000, (24 * 60 * 60 * 1000) / dayTimes);
		this.run = true;
	}

	/**
	 * 间隔时间执行
	 * 
	 * @param dayTimes
	 */
	public void taskRun(long period) {
		if (timer == null) {
			timer = new Timer();
		}
		if (timerTask == null) {
			timerTask = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					logger.debug("循环间隔任务开始执行...");
				}
			};
		}
		timer.schedule(timerTask, period);
		this.run = true;
	}

	public boolean isRun() {
		return this.run;
	}

	public void stop() {
		// TODO Auto-generated method stub
		if (timerTask != null) {
			timerTask.cancel();
		}
		if (this.timer != null) {
			this.timer.cancel();
		}
		this.run = false;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public TimerTask getTimerTask() {
		return timerTask;
	}

	public void setTimerTask(TimerTask timerTask) {
		this.timerTask = timerTask;
	}
}
