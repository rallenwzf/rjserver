/**
 * rallen
 */
package cn.game.rjserver.uc.dao.gsm;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.game.rjserver.uc.dao.BaseDao;
import cn.game.rjserver.uc.entity.TgsmGameupdate;
import cn.game.rjserver.uc.entity.TgsmServerUserroles;
import cn.game.rjserver.uc.exception.DbException;

/**
 * @author wangzhifeng(rallen)
 */
/**
 * @author wangzhifeng(rallen)
 */
public interface ServerDao extends BaseDao {

	public List<TgsmServerUserroles> getUserServerRolesList(String sql);

	public List<TgsmGameupdate> getAllServerVersionList(String sql);

	public long getUserLastServerid(String sql, int idnumber) throws DbException;

	public boolean insertUserRole(TgsmServerUserroles tserverUserroles) throws DbException;
}
