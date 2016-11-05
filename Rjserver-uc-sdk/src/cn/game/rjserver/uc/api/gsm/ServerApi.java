/**
 * rallen
 */
package cn.game.rjserver.uc.api.gsm;

import java.util.List;

import cn.game.rjserver.uc.api.entity.ServerRolesInfo;
import cn.game.rjserver.uc.api.entity.ServersInfo;
import cn.game.rjserver.uc.api.entity.UpgradeInfo;
import cn.game.rjserver.uc.sdk.ApiResult;

/**
 * @author wangzhifeng(rallen)
 */
public interface ServerApi {
	/**
	 * @param gameCode
	 * @param userId
	 * @param joinRole
	 * @return
	 */
	public ApiResult<List<ServersInfo>> getAllOpedServerList(String channel, boolean needDefault);

	/**
	 * @param gameCode
	 * @param userId
	 * @param joinRole
	 * @return
	 */
	public ApiResult<ServersInfo> getDefultServer(String channel, String userAccount);

	/**
	 * @param gameCode
	 * @param maxVersion
	 * @return
	 */
	public ApiResult<List<UpgradeInfo>> getAllUpgradeList(int maxVersion);

	public ApiResult<List<ServerRolesInfo>> getAllServerRolesList(long uid);

	/**
	 * @param gameCode
	 * @param userId
	 * @return
	 */
	public ApiResult<Long> getUserLastServerid(String userAccount);

	public ApiResult<Boolean> sendRole(long serverid, ServerRolesInfo info);

	public ApiResult<Boolean> inoutServer(long serverid, long uid, boolean into);

}
