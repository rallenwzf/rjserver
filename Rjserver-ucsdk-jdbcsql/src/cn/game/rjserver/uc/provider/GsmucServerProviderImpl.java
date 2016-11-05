/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.uc.provider;

import java.util.Date;
import java.util.List;

import cn.game.rjserver.basicutils.lang.StringFormat;
import cn.game.rjserver.uc.dao.gsm.ServerDao;
import cn.game.rjserver.uc.dao.gsm.ServerDaoImpl;
import cn.game.rjserver.uc.entity.TgsmGameupdate;
import cn.game.rjserver.uc.entity.TgsmSdktype;
import cn.game.rjserver.uc.entity.TgsmServerUserroles;
import cn.game.rjserver.uc.entity.TgsmServers;
import cn.game.rjserver.uc.exception.DbException;

/**
 * @author wangzhifeng(rallen) 表数据提供
 */
public class GsmucServerProviderImpl implements GsmucServerProvider {
	ServerDao serverDao = new ServerDaoImpl();

	public ServerDao getServerDao() {
		return serverDao;
	}

	public void setServerDao(ServerDao serverDao) {
		this.serverDao = serverDao;
	}

	/**
	 * 
	 * 可以更新的版本
	 */
	@Override
	public List<TgsmGameupdate> getAllServerVersionList(int maxVersion) {
		String sql = "select * from tgsm_gameupdate where  version >'" + maxVersion + "' order by version";
		return serverDao.getAllServerVersionList(sql);
	}

	public List<TgsmServerUserroles> getServerRolesList(long uid) throws DbException {
		String sql = "SELECT * FROM tgsm_server_userroles t inner join tgsm_servers t3 ON t.serverid=t3.serverid and t3.`status`>-1  WHERE t.uid=" + uid + " ORDER BY t.createdate DESC";
		List<TgsmServerUserroles> list = serverDao.selectEntities(TgsmServerUserroles.class, sql);
		return list;
	}

	public List<TgsmServers> getAllOpedServerList() throws DbException {
		String sql = "select * from tgsm_servers t where t.status>-1 order by t.sort asc";
		List<TgsmServers> list = serverDao.selectEntities(TgsmServers.class, sql);
		return list;
	}

	/**
	 * 所有服务器
	 * 
	 * @return
	 * @throws DbException
	 */
	public List<TgsmServers> getAllServerList() throws DbException {
		String sql = "select * from tgsm_servers t order by t.sort asc";
		List<TgsmServers> list = serverDao.selectEntities(TgsmServers.class, sql);
		return list;
	}

	public TgsmServers getServer(long serverId) throws DbException {
		String sql = "select * from tgsm_servers t where t.serverid=" + serverId;
		TgsmServers t = (TgsmServers) serverDao.selectSingleEntity(TgsmServers.class, sql);
		return t;
	}

	public TgsmSdktype getTgsmSdktype(int sdktype) throws DbException {
		String sql = "select * from tgsm_sdktype t where t.sdktype=" + sdktype;
		TgsmSdktype t = (TgsmSdktype) serverDao.selectSingleEntity(TgsmSdktype.class, sql);
		return t;
	}

	public int getUserRolesNum(long uid, long serverId) throws DbException {
		if (serverId > 0) {
			String sql = "select count(*) from tgsm_server_userroles where uid=" + uid + " and serverid=" + serverId;
			return this.serverDao.selectCount(sql);
		} else {
			String sql = "select count(*) from tgsm_server_userroles where uid=" + uid;
			return this.serverDao.selectCount(sql);

		}
	}

	// 最後一次登錄的服務器
	public long getUserLastServerid(String userAccount) throws DbException {

		String sql = "select t.serverid from tgsm_server_userroles t inner join tgsm_servers t3 ON t.serverid=t3.serverid and t3.`status`>-1 "
				+ "where t.uid=(select t2.uid from tuc_users t2 where t2.useraccount='" + userAccount + "' limit 0,1) order by createdate desc limit 0,1";
		return this.serverDao.getUserLastServerid(sql, 1);

	}

	public boolean hasUserRole(long uid, long serverId) throws DbException {
		String sql = "select * from tgsm_server_userroles  t where t.uid=" + uid + " and t.serverid=" + serverId + " and uroleid>0";
		int count = this.serverDao.selectCount(sql);
		if (count < 1) {
			return false;
		} else {
			return true;
		}
	}

	public boolean deleteUserRole(long serverId, long uid, long roleId) throws DbException {
		String sql = "delete from tgsm_server_userroles where serverid=" + serverId + " and uid=" + uid + " and uroleid=" + roleId;
		return serverDao.update(sql);
	}

	@Override
	public boolean updateServerRoleDate(long serverId, long uid) throws DbException {
		// TODO Auto-generated method stub
		String sql = "update tgsm_server_userroles t set t.createdate='" + StringFormat.getYYMMDDHHMMSS(new Date()) + "'" + " where t.serverid=" + serverId + " and t.uid=" + uid;
		boolean b = serverDao.update(sql);
		return b;
	}

	@Override
	public boolean addServerRole(long serverId, long uid, long roleId, int level, String rolenikename, String head) throws DbException {
		// TODO Auto-generated method stub
		String sql = "update tgsm_server_userroles t set t.rolelevel=" + level + " where t.serverid=" + serverId + " and t.uid=" + uid + " and t.uroleid=" + roleId;
		boolean b = serverDao.update(sql);
		if (!b) {
			TgsmServerUserroles t = new TgsmServerUserroles();
			t.setCreatedate(new Date());
			t.setRolelevel(level);
			t.setRolenikename(rolenikename);
			t.setUid(uid);
			t.setUroleid(roleId);
			t.setRolehead(head);
			TgsmServers tgsmServers = new TgsmServers();
			tgsmServers.setServerid(serverId);
			t.setTgsmServers(tgsmServers);
			return serverDao.insertUserRole(t);
		}
		return true;
	}

	public TgsmServers getServerBycode(String code) throws DbException {
		String sql = "select * from tgsm_servers t where t.code='" + code + "'";
		TgsmServers t = (TgsmServers) serverDao.selectSingleEntity(TgsmServers.class, sql);
		return t;
	}
}
