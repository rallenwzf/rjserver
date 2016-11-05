/**
 * rallen
 */
package cn.game.rjserver.uc.provider;

import java.util.List;

import cn.game.rjserver.uc.entity.TsdkOrder;
import cn.game.rjserver.uc.entity.TucOthersdkUser;
import cn.game.rjserver.uc.entity.TucUserinfo;
import cn.game.rjserver.uc.entity.TucUsers;
import cn.game.rjserver.uc.exception.DbException;

/**
 * @author wangzhifeng(rallen) 数据提供 只提供用户信息相关表的数据操作
 */
public interface GsmucUserProvider extends Provider {

	public boolean isHasUser(String userAccount) throws DbException;

	public TucUserinfo getUser(String userAccount) throws DbException;

	public TucUserinfo vilidate(String userAccount, String password) throws DbException;

	public TucUserinfo createUser(TucUsers defaultUser, TucUserinfo defaultUserinfo, boolean auto)
			throws DbException;

	public boolean updateEmail(long uid, String email) throws DbException;

}
