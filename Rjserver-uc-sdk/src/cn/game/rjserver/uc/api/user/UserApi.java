/**
 * rallen
 */
package cn.game.rjserver.uc.api.user;

import cn.game.rjserver.uc.api.entity.CollectedInfo;
import cn.game.rjserver.uc.api.entity.TsdkOrder;
import cn.game.rjserver.uc.api.entity.UserInfo;
import cn.game.rjserver.uc.sdk.ApiResult;

/**
 * @author wangzhifeng(rallen)
 */
public interface UserApi {

	public ApiResult<UserInfo> creatNewUser(CollectedInfo info);

	// 手动注册
	public ApiResult<UserInfo> reg(String userAccount, String password, CollectedInfo info);

	public ApiResult<UserInfo> login(String userAccount, String password, CollectedInfo info);

	public ApiResult<Boolean> isPresence(String userAccount);

	public ApiResult<String> getLastSuccCode(String useraccount);

	public ApiResult<UserInfo> getUser(String userAccount);

	public ApiResult<Boolean> logout(long uid);

	public ApiResult<Boolean> updateEmail(long uid, String email);

}
