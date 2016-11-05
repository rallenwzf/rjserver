/**
 * rallen 
 */
package cn.game.rjserver.uc.provider;

import cn.game.rjserver.uc.exception.SqlParameterException;

/**
 * @author wangzhifeng(rallen)
 */
public class AbsProvider implements Provider {
	/**
	 * @param param
	 *            判断参数安全性,主要防sql注入
	 * @return
	 * @throws SqlParameterException
	 */
	public boolean checkSqlParameter(Object param) throws SqlParameterException {
		boolean result = false;
		if (param == null) {
			result = false;
		} else {

			//

			result = true;
		}
		if (!result) {
			throw new SqlParameterException("防sql注入，参数有误");
		}
		return result;
	}
}
