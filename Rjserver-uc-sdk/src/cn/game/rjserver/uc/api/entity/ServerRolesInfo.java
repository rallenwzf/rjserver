/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.api.entity;

/**
 * @author rallen
 * 
 */
public class ServerRolesInfo {
	private long uid;
	private long roleId;
	private int roleLevel;
	private String roleNikename;
	private String roleHead;
	//
	private long serverId;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getRoleNikename() {
		return roleNikename;
	}

	public void setRoleNikename(String roleNikename) {
		this.roleNikename = roleNikename;
	}

	public long getServerId() {
		return serverId;
	}

	public void setServerId(long serverId) {
		this.serverId = serverId;
	}

	public String getRoleHead() {
		return roleHead;
	}

	public void setRoleHead(String roleHead) {
		this.roleHead = roleHead;
	}

}
