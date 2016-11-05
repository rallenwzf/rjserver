/**
 * rallen
 */
package cn.game.rjserver.uc.dao.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import cn.game.rjserver.basicutils.lang.StringFormat;
import cn.game.rjserver.db.support.jdbc.JdbOp;
import cn.game.rjserver.db.support.jdbc.connsource.ConnectionSourceFactory;
import cn.game.rjserver.uc.InitDcsConfig;
import cn.game.rjserver.uc.dao.BaseDaoImpl;
import cn.game.rjserver.uc.dao.HBM;
import cn.game.rjserver.uc.entity.TucUserinfo;
import cn.game.rjserver.uc.entity.TucUsers;
import cn.game.rjserver.uc.exception.DbException;

/**
 * @author wangzhifeng(rallen)
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public boolean addTuser(TucUsers defaultUser) throws DbException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("insert into tuc_users values(NULL,'");
		sqlBuf.append(defaultUser.getUseraccount());
		sqlBuf.append("','");
		sqlBuf.append(defaultUser.getUserpwd());
		sqlBuf.append("','");
		sqlBuf.append(defaultUser.getUname());
		sqlBuf.append("','");
		sqlBuf.append(StringFormat.getYYMMDDHHMMSS(new Date()));
		sqlBuf.append("',");
		sqlBuf.append("'0',NULL)");
		String mySql = sqlBuf.toString();
		return this.update(mySql);
	}

	@Override
	public boolean addTuserinfo(TucUserinfo userinfo) throws DbException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("insert into tuc_userinfo values(NULL,");
		sqlBuf.append(userinfo.getTucUsers().getUid());
		sqlBuf.append(",");
		sqlBuf.append("'" + userinfo.getEmail() + "',");
		sqlBuf.append("'" + userinfo.getMobile() + "',");
		sqlBuf.append("0,NULL,");
		sqlBuf.append("'" + userinfo.getImei() + "',");
		sqlBuf.append("'" + userinfo.getImsi() + "',");
		sqlBuf.append(userinfo.getScreenwidth() + ",");
		sqlBuf.append(userinfo.getScreenheight() + ",");
		sqlBuf.append(userinfo.getGameversion() + ",");
		sqlBuf.append("'" + userinfo.getSystemsdk() + "',");
		sqlBuf.append("'" + userinfo.getSystemversion() + "',");
		sqlBuf.append("'" + userinfo.getChannel() + "',");
		sqlBuf.append("'" + userinfo.getPhonemode() + "',");
		sqlBuf.append("'" + userinfo.getUserip() + "',");
		sqlBuf.append("'" + userinfo.getSmsverifycode() + "',");
		sqlBuf.append("'" + userinfo.getIdentifycard() + "'");
		sqlBuf.append(")");
		String sql = sqlBuf.toString();
		return this.update(sql);
	}

	@Override
	public TucUserinfo getUserInfo(String sql) throws DbException {
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			jdbOp.createConnect();
			ResultSet rs = jdbOp.getData(sql);
			if (rs != null && rs.next()) {
				TucUserinfo info = new TucUserinfo();
				HBM.fillEntity(info, rs);
				info.setTucUsers((TucUsers) HBM.fillEntity(TucUsers.class.newInstance(), rs));
				return info;
			}
		} catch (Exception e) {
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public long getLong(String sql, int idNumber) throws DbException {
		// TODO Auto-generated method stub
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			jdbOp.createConnect();
			logger.d("db_select：" + sql);
			ResultSet rs = jdbOp.getData(sql);
			if (rs != null && rs.next()) {
				return rs.getLong(idNumber);
			}
		} catch (Exception e) {
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public boolean updateUser(TucUsers user) throws DbException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("update tuc_users set ");
		sqlBuf.append(" updatedate='" + StringFormat.getYYMMDDHHMMSS(new Date()) + "'");
		sqlBuf.append(" where uid=" + user.getUid());
		String sql = sqlBuf.toString();
		return this.update(sql);
	}

	@Override
	public String getString(String sql) throws DbException {
		// TODO Auto-generated method stub
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			jdbOp.createConnect();
			logger.d("db_select：" + sql);
			ResultSet rs = jdbOp.getData(sql);
			if (rs != null && rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean updateEmail(long uid, String email) throws DbException {
		String sql = "update tuc_userinfo set email='" + email + "' where uid=" + uid;
		return this.update(sql);
	}

}
