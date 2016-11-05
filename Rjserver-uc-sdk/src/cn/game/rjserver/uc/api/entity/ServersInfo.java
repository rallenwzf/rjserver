/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.api.entity;

/**
 * @author rallen
 * 
 */
public class ServersInfo {
	//
	private long serverId;
	private String serverName;
	private String serverHosting;
	private int status;// 0优、1良、2满、3忙
	private int commend;// 1推荐、2新服、0无
	private long code;
	private int sort;

	public long getServerId() {
		return serverId;
	}

	public void setServerId(long serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerHosting() {
		return serverHosting;
	}

	public void setServerHosting(String serverHosting) {
		this.serverHosting = serverHosting;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCommend() {
		return commend;
	}

	public void setCommend(int commend) {
		this.commend = commend;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
