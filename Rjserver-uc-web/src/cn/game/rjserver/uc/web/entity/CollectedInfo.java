/**
 * rallen
 */
package cn.game.rjserver.uc.web.entity;

/**
 * @author wangzhifeng(rallen) 收集到的信息
 */
public class CollectedInfo {
	private String imei;
	private String imsi;
	private Long screenwidth;
	private Long screenheight;
	private Long gameversion;
	private String gamecode;
	private String systemsdk;
	private String systemversion;
	private String channel;
	private String gangway;
	private String phonemode;
	private String userip;
	private String Invitationcode;
	private String phoneNumber;
	private String identifycard;

	public String getIdentifycard() {
		return identifycard;
	}

	public void setIdentifycard(String identifycard) {
		this.identifycard = identifycard;
	}

	public String getInvitationcode() {
		return Invitationcode;
	}

	public void setInvitationcode(String invitationcode) {
		Invitationcode = invitationcode;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Long getScreenwidth() {
		return screenwidth;
	}

	public void setScreenwidth(Long screenwidth) {
		this.screenwidth = screenwidth;
	}

	public Long getScreenheight() {
		return screenheight;
	}

	public void setScreenheight(Long screenheight) {
		this.screenheight = screenheight;
	}

	public Long getGameversion() {
		return gameversion;
	}

	public void setGameversion(Long gameversion) {
		this.gameversion = gameversion;
	}

	public String getSystemsdk() {
		return systemsdk;
	}

	public void setSystemsdk(String systemsdk) {
		this.systemsdk = systemsdk;
	}

	public String getSystemversion() {
		return systemversion;
	}

	public void setSystemversion(String systemversion) {
		this.systemversion = systemversion;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPhonemode() {
		return phonemode;
	}

	public void setPhonemode(String phonemode) {
		this.phonemode = phonemode;
	}

	public String getUserip() {
		return userip;
	}

	public void setUserip(String userip) {
		this.userip = userip;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
