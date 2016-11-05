/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.log.entity;

import java.util.Date;

public class HyLog {
	private String action;
	private String userAccount;
	private String isReal;
	private String channel;
	private Date startDate;
	private Date startTime;
	private String startHour;
	private Date endDate;
	private Date endTime;
	private String endHour;
	private int seconds;

	private String propsAction;

	/**
	 * @hibernate.property
	 * @hibernate.column name="action" length="100" unique-key="log_unique"
	 */
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @hibernate.property
	 * @hibernate.column name="userAccount" length="100" unique-key="log_unique"
	 */
	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	/**
	 * @hibernate.property
	 * @hibernate.column name="isReal" length="10" unique-key="log_unique"
	 */
	public String getIsReal() {
		return isReal;
	}

	public void setIsReal(String isReal) {
		this.isReal = isReal;
	}

	/**
	 * @hibernate.property
	 * @hibernate.column name="isReal" length="100"
	 */
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @hibernate.property
	 * @hibernate.column name="startDate" sql-type="date"
	 *                   unique-key="log_unique"
	 */
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @hibernate.property
	 * @hibernate.column name="startTime" sql-type="time"
	 *                   unique-key="log_unique"
	 */
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @hibernate.property
	 * @hibernate.column name="startHour" length="10"
	 */
	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	/**
	 * @hibernate.property
	 * @hibernate.column name="endDate" sql-type="date"
	 */
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @hibernate.property
	 * @hibernate.column name="endTime" sql-type="time"
	 */
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @hibernate.property
	 * @hibernate.column name="endHour" length="10"
	 */
	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	/**
	 * @hibernate.property
	 * @hibernate.column name="seconds" sql-type="int"
	 */
	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	/**
	 * @hibernate.property
	 * @hibernate.column name="propsAction" length="50"
	 */
	public String getPropsAction() {
		return propsAction;
	}

	public void setPropsAction(String propsAction) {
		this.propsAction = propsAction;
	}

}
