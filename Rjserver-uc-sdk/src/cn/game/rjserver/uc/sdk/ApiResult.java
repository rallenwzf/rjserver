/**
 * rallen 
 */
package cn.game.rjserver.uc.sdk;

/**
 * @author wangzhifeng(rallen)
 * @param <T>
 */
public class ApiResult<T> {
	private T result;
	private ApiStatus apiStatus;

	public ApiResult(T result, ApiStatus apiStatus) {
		this.result = result;
		this.apiStatus = apiStatus;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public ApiStatus getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(ApiStatus apiStatus) {
		this.apiStatus = apiStatus;
	}

}
