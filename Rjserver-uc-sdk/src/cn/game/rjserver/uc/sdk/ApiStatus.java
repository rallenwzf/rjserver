/**
 * rallen 
 */
package cn.game.rjserver.uc.sdk;

/**
 * @author wangzhifeng(rallen)
 */
public class ApiStatus {

	/*
	 * 
	 */
	public static final ApiStatus OK = new ApiStatus(1, "成功");
	public static final ApiStatus FAILED = new ApiStatus(2, "失败");
	public static final ApiStatus EXCEPTION = new ApiStatus(3, "接口异常");
	/*
	 * 
	 */
	private final int type;
	private int errorType;
	private String status;
	private Exception exception;

	private ApiStatus(int type, String status) {
		this.type = type;
		this.status = status;
	}

	private ApiStatus(int type, int errorType, String status) {
		this.type = type;
		this.errorType = errorType;
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public int getErrorType() {
		return errorType;
	}

	public ApiStatus setErrorType(int errorType) {
		this.errorType = errorType;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public ApiStatus setStatus(String status) {
		this.status = status;
		return this;
	}

	public Exception getException() {
		return exception;
	}

	public ApiStatus setException(Exception exception) {
		this.exception = exception;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof ApiStatus) {
			ApiStatus s = (ApiStatus) obj;
			if (s.getType() == this.getType()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}
}
