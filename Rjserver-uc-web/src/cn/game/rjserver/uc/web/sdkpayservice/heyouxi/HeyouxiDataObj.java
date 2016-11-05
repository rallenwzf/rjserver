package cn.game.rjserver.uc.web.sdkpayservice.heyouxi;

/**
 * @author wzf
 * 
 */
public class HeyouxiDataObj {
	private String userId;// userId 用户伪码 String HTTP 游戏业务平台 userId
	private String contentId;// M String 12 计费代码
	private String consumeCode;// M String 12 道具计费代码
	private String cpId;// M String 6 合作代码
	private String hRet;// M String 4 平台计费结果（状态码外码）0-成功 其他-失败
	private String status;// 内码
	private String versionId;// M String 6 版本号2_0_0, 统一填写2_0_0
	private String cpparam;// O String 16 CP透传参数
	private String packageID;// O String 12 套餐包ID(非局数据ID)

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getConsumeCode() {
		return consumeCode;
	}

	public void setConsumeCode(String consumeCode) {
		this.consumeCode = consumeCode;
	}

	public String getCpId() {
		return cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String gethRet() {
		return hRet;
	}

	public void sethRet(String hRet) {
		this.hRet = hRet;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getCpparam() {
		return cpparam;
	}

	public void setCpparam(String cpparam) {
		this.cpparam = cpparam;
	}

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}