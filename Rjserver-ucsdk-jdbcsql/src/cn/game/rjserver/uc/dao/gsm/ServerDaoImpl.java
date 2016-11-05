/**
 * rallen
 */
package cn.game.rjserver.uc.dao.gsm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.game.rjserver.basicutils.lang.StringFormat;
import cn.game.rjserver.db.support.jdbc.JdbOp;
import cn.game.rjserver.uc.InitDcsConfig;
import cn.game.rjserver.uc.dao.BaseDaoImpl;
import cn.game.rjserver.uc.dao.HBM;
import cn.game.rjserver.uc.entity.TgsmGameupdate;
import cn.game.rjserver.uc.entity.TgsmServerUserroles;
import cn.game.rjserver.uc.entity.TgsmServers;
import cn.game.rjserver.uc.exception.DbException;

/**
 * @author wangzhifeng(rallen)
 */
public class ServerDaoImpl extends BaseDaoImpl implements ServerDao {

	@Override
	public boolean insertUserRole(TgsmServerUserroles tserverUserroles) throws DbException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("insert into tgsm_server_userroles values(NULL,");
		sqlBuf.append(tserverUserroles.getTgsmServers().getServerid() + ",");
		sqlBuf.append(tserverUserroles.getUid() + ",");
		sqlBuf.append(tserverUserroles.getUroleid() + ",");
		sqlBuf.append(tserverUserroles.getRolelevel() + ",");
		sqlBuf.append("'" + tserverUserroles.getRolenikename() + "',");
		sqlBuf.append("'" + tserverUserroles.getRolehead() + "',");
		sqlBuf.append("'" + StringFormat.getYYMMDDHHMMSS(new Date()) + "'");
		sqlBuf.append(")");
		return update(sqlBuf.toString());
	}

	@Override
	public List<TgsmServerUserroles> getUserServerRolesList(String sql) {
		// TODO Auto-generated method stub
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			List<TgsmServerUserroles> list = new ArrayList<TgsmServerUserroles>();
			jdbOp.createConnect();
			ResultSet rs = jdbOp.getData(sql);
			while (rs.next()) {
				TgsmServerUserroles info = new TgsmServerUserroles();
				HBM.fillEntity(info, rs);
				info.setTgsmServers((TgsmServers) HBM.fillEntity(TgsmServers.class.newInstance(),
						rs));
				list.add(info);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
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
	public List<TgsmGameupdate> getAllServerVersionList(String sql) {
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			List<TgsmGameupdate> list = new ArrayList<TgsmGameupdate>();
			jdbOp.createConnect();
			ResultSet rs = jdbOp.getData(sql);
			while (rs.next()) {
				TgsmGameupdate info = new TgsmGameupdate();
				HBM.fillEntity(info, rs);
				list.add(info);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
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
	public long getUserLastServerid(String sql, int idNumber) throws DbException {
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

}
