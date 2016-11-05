/**
 * rallen
 */
package cn.game.rjserver.uc.api.entity;

/**
 * @author wangzhifeng(rallen)
 */
public class UserInfo {
	private long uid;
	private String useraccount;
	private String petname;
	private String mobile;
	private String email;
	//
	private String password;

	public boolean isNew;
	private Long usercoin;

	// +

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUseraccount() {
		return useraccount;
	}

	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
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

}
