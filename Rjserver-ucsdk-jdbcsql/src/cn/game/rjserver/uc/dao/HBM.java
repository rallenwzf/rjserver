/**
 * rallen
 */
package cn.game.rjserver.uc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.game.rjserver.uc.entity.TgsmGameupdate;
import cn.game.rjserver.uc.entity.TgsmSdktype;
import cn.game.rjserver.uc.entity.TgsmServerUserroles;
import cn.game.rjserver.uc.entity.TgsmServers;
import cn.game.rjserver.uc.entity.TsdkOrder;
import cn.game.rjserver.uc.entity.TucOthersdkUser;
import cn.game.rjserver.uc.entity.TucUserinfo;
import cn.game.rjserver.uc.entity.TucUsers;

/**
 * @author wangzhifeng(rallen)
 *         <p>
 *         ResultSet to Object (ORM)
 */
public class HBM {
	public static Object fillEntity(Object entity, ResultSet rs) {
		Object obj = null;
		// user role 用户、角色
		obj = fillUsersEntity(entity, rs);
		if (obj != null) {
			return obj;
		}
		obj = fillServerEntity(entity, rs);
		if (obj != null) {
			return obj;
		}
		return null;
	}

	/**
	 * @param tur
	 * @param rs
	 * @return
	 */
	private static Object fillUsersEntity(Object entity, ResultSet rs) {
		try {
			if (entity instanceof TucUserinfo) {
				TucUserinfo t = (TucUserinfo) entity;
				t.setUserinfoid(rs.getLong("userinfoid"));
				t.setMobile(rs.getString("mobile"));
				t.setEmail(rs.getString("email"));
				t.setUsercoin(rs.getLong("usercoin"));
				t.setRemark(rs.getString("remark"));
				t.setIdentifycard(rs.getString("identifycard"));
				t.setSmsverifycode(rs.getString("smsverifycode"));

				return t;
			} else if (entity instanceof TucUsers) {
				TucUsers t = (TucUsers) entity;
				t.setUid(rs.getLong("uid"));
				t.setUseraccount(rs.getString("useraccount"));
				t.setUname(rs.getString("uname"));
				t.setUserpwd(rs.getString("userpwd"));
				t.setCreatedate(rs.getTimestamp("createdate"));
				t.setDeleteflag(rs.getString("deleteflag"));
				t.setRemark(rs.getString("remark"));

				return t;
			} else if (entity instanceof TucOthersdkUser) {
				TucOthersdkUser t = (TucOthersdkUser) entity;
				t.setSdksid(rs.getString("sdksid"));
				t.setSdktype(rs.getString("sdktype"));
				t.setSdkuid(rs.getString("sdkuid"));
				t.setSdkuname(rs.getString("sdkuname"));
				t.setTotherid(rs.getLong("totherid"));
				TucUsers tusers = new TucUsers();
				tusers.setUid(rs.getLong("uid"));
				t.setTucUsers(tusers);

				return t;
			} else if (entity instanceof TsdkOrder) {
				TsdkOrder t = (TsdkOrder) entity;
				t.setCreatetime(rs.getLong("createtime"));
				t.setFee(rs.getString("fee"));
				t.setId(rs.getLong("id"));
				t.setOrdercode(rs.getString("ordercode"));
				t.setSdkcode(rs.getString("sdkcode"));
				t.setSign(rs.getString("sign"));
				t.setStatus(rs.getString("status"));
				t.setTransactid(rs.getString("transactid"));

				return t;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * fillServerEntity
	 * 
	 * @param tur
	 * @param rs
	 * @return
	 */
	private static Object fillServerEntity(Object entity, ResultSet rs) {
		try {
			if (entity instanceof TgsmServers) {
				TgsmServers t = (TgsmServers) entity;
				t.setServerid(rs.getLong("serverid"));
				t.setServername(rs.getString("servername"));
				t.setServerhosting(rs.getString("serverhosting"));
				t.setStatus(rs.getInt("status"));
				t.setCommendval(rs.getInt("commendval"));
				t.setChannel(rs.getInt("channel"));
				t.setRolenum(rs.getLong("rolenum"));
				t.setCode(rs.getString("code"));
				t.setSort(rs.getInt("sort"));
				t.setRemark(rs.getString("remark"));

				return t;
			} else if (entity instanceof TgsmServerUserroles) {
				TgsmServerUserroles t = (TgsmServerUserroles) entity;
				t.setCreatedate(rs.getTimestamp("createdate"));
				TgsmServers tgsmServers = new TgsmServers();
				tgsmServers.setServerid(rs.getLong("serverid"));
				t.setTgsmServers(tgsmServers);
				t.setSurolesid(rs.getLong("surolesid"));
				t.setUroleid(rs.getLong("uroleid"));
				t.setUid(rs.getLong("uid"));
				t.setRolelevel(rs.getInt("rolelevel"));
				t.setRolenikename(rs.getString("rolenikename"));
				t.setRolehead(rs.getString("rolehead"));
				t.setCreatedate(rs.getTimestamp("createdate"));

				return t;
			} else if (entity instanceof TgsmGameupdate) {
				TgsmGameupdate t = (TgsmGameupdate) entity;
				t.setResid(rs.getLong("resid"));
				t.setVersion(rs.getLong("version"));
				t.setVersionname(rs.getString("versionname"));
				t.setGamename(rs.getString("gamename"));
				t.setGamecode(rs.getString("gamecode"));
				t.setVersionserialcode(rs.getString("versionserialcode"));
				t.setSrc(rs.getString("src"));
				t.setMustupdate(rs.getString("mustupdate"));
				t.setCreatedate(rs.getTimestamp("createdate"));
				return t;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getInsertSql(Object entity, String keyVal) {
		// if (entity instanceof TrelecesOriginal) {
		// StringBuffer mysqlBuf = new StringBuffer();
		// TrelecesOriginal t = (TrelecesOriginal) entity;
		// mysqlBuf.append("insert into Treleces_original values(" +
		// t.getRelicid() + ",");
		// mysqlBuf.append("'" + t.getReliccode() + "',");
		// mysqlBuf.append("'" + t.getRelicname() + "',");
		// mysqlBuf.append(t.getStrengthlevel() + ",");
		// mysqlBuf.append(t.getAdditionattr() + ",");
		// mysqlBuf.append("'" + t.getAttrvalue() + "',");
		// mysqlBuf.append("'" + t.getIconurl() + "',");
		// mysqlBuf.append(t.getEnergyvalue());
		// mysqlBuf.append(")");
		// return mysqlBuf.toString();
		// }
		return null;
	}

	/**
	 * 得到表名，默认是类名
	 * 
	 * @param entity
	 * @return
	 */
	public static String getTableName(Object entity) {
		// if (entity instanceof Tpublicnotice) {
		// return "Tpublicnotice";
		// }
		return entity.getClass().getSimpleName();
	}
}
