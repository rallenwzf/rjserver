package cn.game.rjserver.uc.api.entity;


// Generated 2015-1-27 11:30:06 by Hibernate Tools 3.4.0.CR1

/**
 * TandroidOrder generated by hbm2java
 */
public class TsdkOrder  implements java.io.Serializable {

	private Long id;
    private String sdkcode;
	private String ordercode;
    private String transactid;
    private String fee;
	private String sign;
	private String status;
//	private Long createtime;

	public TsdkOrder() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSdkcode() {
		return sdkcode;
	}

	public void setSdkcode(String sdkcode) {
		this.sdkcode = sdkcode;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getTransactid() {
		return transactid;
	}

	public void setTransactid(String transactid) {
		this.transactid = transactid;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

//	public Long getCreatetime() {
//		return createtime;
//	}
//
//	public void setCreatetime(Long createtime) {
//		this.createtime = createtime;
//	}


}