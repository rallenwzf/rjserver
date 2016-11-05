/**
 * rallen 
 */
package cn.game.rjserver.uc.api.pay;

import cn.game.rjserver.uc.sdk.ApiResult;
import cn.game.rjserver.uc.sdk.ApiStatus;

/**
 * @author wangzhifeng(rallen)
 */
public class UsercoinApiImpl implements UsercoinApi {

	@Override
	public ApiResult<Integer> getUserUsercoin(String userId) {
		// TODO Auto-generated method stub
		try {
			int usercoin = 0;
			return new ApiResult<Integer>(usercoin, ApiStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<Integer>(0, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<Boolean> subUsercoin(String userId, int subNum) {
		try {
			boolean b = false;
			if (b) {
				return new ApiResult<Boolean>(b, ApiStatus.OK);
			} else {
				return new ApiResult<Boolean>(b, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<Boolean>(false, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<Boolean> addUsercoin(String userId, int subNum) {
		try {
			boolean b = false;
			if (b) {
				return new ApiResult<Boolean>(b, ApiStatus.OK);
			} else {
				return new ApiResult<Boolean>(b, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<Boolean>(false, ApiStatus.EXCEPTION.setException(e));
		}
	}
}
