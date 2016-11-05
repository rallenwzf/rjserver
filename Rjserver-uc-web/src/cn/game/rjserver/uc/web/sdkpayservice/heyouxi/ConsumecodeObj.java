package cn.game.rjserver.uc.web.sdkpayservice.heyouxi;

public class ConsumecodeObj {
	private int propsId;
	private String consumeCode;
	private int currencyNum;
	private int moneyNum;
	private String propsName;

	// 附属物
	private String attrType;
	private String attrCode;
	private String attrNum;

	public String getConsumeCode() {
		return consumeCode;
	}

	public void setConsumeCode(String consumeCode) {
		this.consumeCode = consumeCode;
	}

	public int getCurrencyNum() {
		return currencyNum;
	}

	public void setCurrencyNum(int currencyNum) {
		this.currencyNum = currencyNum;
	}

	public int getMoneyNum() {
		return moneyNum;
	}

	public void setMoneyNum(int moneyNum) {
		this.moneyNum = moneyNum;
	}

	public int getPropsId() {
		return propsId;
	}

	public void setPropsId(int propsId) {
		this.propsId = propsId;
	}

	public String getPropsName() {
		return propsName;
	}

	public void setPropsName(String propsName) {
		this.propsName = propsName;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public String getAttrNum() {
		return attrNum;
	}

	public void setAttrNum(String attrNum) {
		this.attrNum = attrNum;
	}

}