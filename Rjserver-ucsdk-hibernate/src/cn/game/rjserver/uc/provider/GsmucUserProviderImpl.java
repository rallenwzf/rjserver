/**
 * rallen
 */
package cn.game.rjserver.uc.provider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.game.rjserver.db.support.hibernate.BaseTblProvider;
import cn.game.rjserver.uc.entity.TucUserinfo;
import cn.game.rjserver.uc.entity.TucUsers;
import cn.game.rjserver.uc.exception.DbException;


/**
 * @author wangzhifeng(rallen) 数据提供 只提供用户信息相关表的数据操作
 */
public class GsmucUserProviderImpl extends AbsProvider implements GsmucUserProvider {

	@Override
	public boolean isHasUser(String userAccount) throws DbException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TucUserinfo getUser(String userAccount) throws DbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TucUserinfo vilidate(String userAccount, String password) throws DbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TucUserinfo createUser(TucUsers defaultUser, TucUserinfo defaultUserinfo, boolean auto) throws DbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateEmail(long uid, String email) throws DbException {
		// TODO Auto-generated method stub
		return false;
	}

}
