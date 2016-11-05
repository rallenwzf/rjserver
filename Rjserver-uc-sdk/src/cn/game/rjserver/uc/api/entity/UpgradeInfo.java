/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.api.entity;

/**
 * @author rallen
 * 
 */
public class UpgradeInfo {
	private long id;
	private long version;
	private String versionName;
	private String gameName;
	private String gameCode;
	private String versionSerialcode;
	private String src;
	private int mustUpdate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getVersionSerialcode() {
		return versionSerialcode;
	}

	public void setVersionSerialcode(String versionSerialcode) {
		this.versionSerialcode = versionSerialcode;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public int getMustUpdate() {
		return mustUpdate;
	}

	public void setMustUpdate(int mustUpdate) {
		this.mustUpdate = mustUpdate;
	}

}
