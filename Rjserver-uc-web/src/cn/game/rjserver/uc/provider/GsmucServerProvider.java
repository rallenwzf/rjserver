/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.uc.provider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.game.rjserver.uc.entity.TgsmGameupdate;
import cn.game.rjserver.uc.entity.TgsmSdktype;
import cn.game.rjserver.uc.entity.TgsmServerUserroles;
import cn.game.rjserver.uc.entity.TgsmServers;
import cn.game.rjserver.uc.exception.DbException;

/**
 * @author wangzhifeng(rallen) 表数据提供
 */
public interface GsmucServerProvider extends Provider {
	/**
	 * 
	 * 获取最新的版本
	 */
	public List<TgsmGameupdate> getAllServerVersionList(int maxVersion);

	public long getUserLastServerid(String userAccount) throws DbException;

	public List<TgsmServers> getAllOpedServerList() throws DbException;

	public List<TgsmServerUserroles> getServerRolesList(long uid) throws DbException;

	/**
	 * 所有服务器
	 * 
	 * @return
	 * @throws DbException
	 */
	public List<TgsmServers> getAllServerList() throws DbException;

	public TgsmServers getServer(long serverId) throws DbException;

	public TgsmServers getServerBycode(String orderheadcode) throws DbException;

	public TgsmSdktype getTgsmSdktype(int sdktype) throws DbException;

	public int getUserRolesNum(long uid, long serverId) throws DbException;

	public boolean hasUserRole(long uid, long serverId) throws DbException;

	public boolean deleteUserRole(long serverId, long uid, long roleId) throws DbException;

	public boolean addServerRole(long serverId, long uid, long roleId, int level, String rolenikename, String head) throws DbException;

	public boolean updateServerRoleDate(long serverId, long uid) throws DbException;
}
