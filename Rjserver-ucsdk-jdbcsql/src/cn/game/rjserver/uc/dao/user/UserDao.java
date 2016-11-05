/**
 * rallen
 */
package cn.game.rjserver.uc.dao.user;

import cn.game.rjserver.uc.dao.BaseDao;
import cn.game.rjserver.uc.entity.TucUserinfo;
import cn.game.rjserver.uc.entity.TucUsers;
import cn.game.rjserver.uc.exception.DbException;

/**
 * @author wangzhifeng(rallen)
 */
/**
 * @author wangzhifeng(rallen)
 */
public interface UserDao extends BaseDao {

	public boolean addTuser(TucUsers defaultUser) throws DbException;

	public boolean addTuserinfo(TucUserinfo defaultUserinfo) throws DbException;

	/**
	 * 查找用户信息
	 * <p>
	 * 包含Tuserinfo、Tusers的关联信息
	 * 
	 * @param sql
	 * @return
	 */
	public TucUserinfo getUserInfo(String sql) throws DbException;

	public boolean updateEmail(long uid, String email) throws DbException;

	public boolean updateUser(TucUsers user) throws DbException;

	public long getLong(String sql, int idNumber) throws DbException;

	public String getString(String sql) throws DbException;

}
