/**
 * rallen 
 */
package cn.game.rjserver.uc.api.pay;

import cn.game.rjserver.uc.sdk.ApiResult;

/**
 * @author wangzhifeng(rallen)
 */
public interface UsercoinApi {
	/**
	 * @param userId
	 * @return
	 */
	public ApiResult<Integer> getUserUsercoin(String userId);

	/**
	 * @param userId
	 * @param subNum
	 * @return
	 */
	public ApiResult<Boolean> subUsercoin(String userId, int subNum);

	/**
	 * @param userId
	 * @param subNum
	 * @return
	 */
	public ApiResult<Boolean> addUsercoin(String userId, int subNum);
}
