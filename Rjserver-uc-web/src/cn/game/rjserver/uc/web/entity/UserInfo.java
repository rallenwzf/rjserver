/**
 * rallen
 */
package cn.game.rjserver.uc.web.entity;

/**
 * @author wangzhifeng(rallen)
 */
public class UserInfo {
	private long id;
	private String userid;
	private String username;
	private String petname;
	private String mobile;
	private String email;
	private String identifycard;
	private String invitationcode;

	public boolean isNew;

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getIdentifycard() {
		return identifycard;
	}

	public void setIdentifycard(String identifycard) {
		this.identifycard = identifycard;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	//
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPetname() {
		return petname;
	}

	public void setPetname(String petname) {
		this.petname = petname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUsercoin() {
		return usercoin;
	}

	public void setUsercoin(Long usercoin) {
		this.usercoin = usercoin;
	}

	public String getInvitationcode() {
		return invitationcode;
	}

	public void setInvitationcode(String invitationcode) {
		this.invitationcode = invitationcode;
	}

	private Long usercoin;
}
