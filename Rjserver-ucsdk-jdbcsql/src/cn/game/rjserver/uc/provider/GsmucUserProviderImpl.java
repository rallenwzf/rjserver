/**
 * rallen
 */
package cn.game.rjserver.uc.provider;

import java.util.Date;
import java.util.List;

import cn.game.rjserver.basicutils.lang.RandomUtils;
import cn.game.rjserver.basicutils.lang.StringFormat;
import cn.game.rjserver.uc.dao.user.UserDao;
import cn.game.rjserver.uc.dao.user.UserDaoImpl;
import cn.game.rjserver.uc.entity.TsdkOrder;
import cn.game.rjserver.uc.entity.TucOthersdkUser;
import cn.game.rjserver.uc.entity.TucUserinfo;
import cn.game.rjserver.uc.entity.TucUsers;
import cn.game.rjserver.uc.exception.DbException;
import cn.game.rjserver.uc.utils.IdcardUtils;
import cn.game.rjserver.uc.utils.UserIdFormat;
import cn.game.rjserver.uc.web.entity.CollectedInfo;

/**
 * @author wangzhifeng(rallen) 数据提供 只提供用户信息相关表的数据操作
 */
public class GsmucUserProviderImpl extends AbsProvider implements GsmucUserProvider {
	UserDao roleDao = new UserDaoImpl();

	public UserDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(UserDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public TucUserinfo vilidate(String userAccount, String password) throws DbException {
		if (this.checkSqlParameter(userAccount) && this.checkSqlParameter(password)) {
			String sql = "select a.*,b.* FROM tuc_users a, tuc_userinfo b where a.useraccount='"
					+ userAccount + "' and a.userpwd='" + password + "' and a.uid=b.uid";
			return roleDao.getUserInfo(sql);
		}
		return null;
	}

	@Override
	public boolean isHasUser(String userAccount) throws DbException {
		// TODO Auto-generated method stub
		if (this.checkSqlParameter(userAccount)) {
			String sql = "select count(*) FROM tuc_users a, tuc_userinfo b where a.useraccount='"
					+ userAccount + "' and a.uid=b.uid";
			int count = roleDao.selectCount(sql);
			if (count > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TucUserinfo getUser(String userAccount) throws DbException {
		// TODO Auto-generated method stub
		if (this.checkSqlParameter(userAccount)) {
			String sql = "select a.*,b.* FROM tuc_users a, tuc_userinfo b where a.useraccount='"
					+ userAccount + "' and a.uid=b.uid";
			return roleDao.getUserInfo(sql);
		}
		return null;
	}

	@Override
	public TucUserinfo createUser(TucUsers defaultUser, TucUserinfo defaultUserinfo, boolean auto)
			throws DbException {
		// TODO Auto-generated method stub
		if (!auto) {
			// 手动创建用户
			if (this.getUser(defaultUser.getUseraccount()) == null) {
				// 账户不存在，可创建
				boolean b = roleDao.addTuser(defaultUser);
				String sql = "select * from tuc_users t where t.useraccount='"
						+ defaultUser.getUseraccount() + "'";
				defaultUser = (TucUsers) roleDao.selectSingleEntity(TucUsers.class, sql);
				if (b && defaultUser != null) {
					defaultUserinfo.setTucUsers(defaultUser);
					b = roleDao.addTuserinfo(defaultUserinfo);
				}
				if (b) {
					return getUser(defaultUser.getUseraccount());
				}
			}
			return null;
		} else {
			String userpwdString = UserIdFormat.getUserpwdString();
			defaultUser.setUserpwd(userpwdString);
			// 自动设置账户密码
			if (defaultUser.getUseraccount() == null || defaultUser.getUseraccount().equals("")) {
				boolean flag = false;
				while (!flag) {
					String sql = "select * from tuc_users t where t.useraccount='"
							+ defaultUser.getUserpwd() + "'";
					TucUsers newdefaultUser = (TucUsers) roleDao.selectSingleEntity(TucUsers.class,
							sql);
					if (newdefaultUser == null) {
						flag = true;
					} else {
						defaultUser.setUserpwd(UserIdFormat.getUserpwdString());
					}
				}
			}
			// defaultUser.setUseraccount(new Date().getTime() +
			// RandomUtils.getRandom(10));
			defaultUser.setUseraccount(defaultUser.getUserpwd());
			roleDao.addTuser(defaultUser);
			String sql = "select * from tuc_users t where t.useraccount='"
					+ defaultUser.getUseraccount() + "'";
			defaultUser = (TucUsers) roleDao.selectSingleEntity(TucUsers.class, sql);
			if (defaultUser != null) {
				String userAccount = UserIdFormat.getClientUserId(defaultUser.getUid());
				// defaultUser.setUseraccount(userAccount);
				if (defaultUser.getUname() == null) {
					defaultUser.setUname("玩家" + userAccount);
				}
				String sql2 = "update tuc_users set useraccount='" + defaultUser.getUseraccount()
						+ "',uname='" + defaultUser.getUname() + "' where uid="
						+ defaultUser.getUid();

				boolean b = roleDao.update(sql2);
				if (b) {
					defaultUserinfo.setTucUsers(defaultUser);
					b = roleDao.addTuserinfo(defaultUserinfo);
				}
				if (b) {
					return getUser(defaultUser.getUseraccount());
				}
			}
		}
		return null;
	}

	@Override
	public boolean updateEmail(long uid, String email) throws DbException {
		// TODO Auto-generated method stub
		return roleDao.updateEmail(uid, email);
	}

}
