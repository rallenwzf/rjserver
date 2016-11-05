/**
 * rallen wangzhifeng@hiyou.cn
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
public class GsmucServerProviderImpl implements GsmucServerProvider {

	@Override
	public List<TgsmGameupdate> getAllServerVersionList(int maxVersion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getUserLastServerid(String userAccount) throws DbException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TgsmServers> getAllOpedServerList() throws DbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TgsmServerUserroles> getServerRolesList(long uid) throws DbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TgsmServers> getAllServerList() throws DbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TgsmServers getServer(long serverId) throws DbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TgsmServers getServerBycode(String orderheadcode) throws DbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TgsmSdktype getTgsmSdktype(int sdktype) throws DbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getUserRolesNum(long uid, long serverId) throws DbException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasUserRole(long uid, long serverId) throws DbException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUserRole(long serverId, long uid, long roleId) throws DbException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addServerRole(long serverId, long uid, long roleId, int level, String rolenikename, String head) throws DbException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateServerRoleDate(long serverId, long uid) throws DbException {
		// TODO Auto-generated method stub
		return false;
	}

}
