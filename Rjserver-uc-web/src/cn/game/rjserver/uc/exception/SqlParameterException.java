/**
 * rallen 
 */
package cn.game.rjserver.uc.exception;

/**
 * @author wangzhifeng(rallen)
 */
public class SqlParameterException extends DbException {
	public SqlParameterException(String msg) {
		super(msg);
	}

	public SqlParameterException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "{SqlParameterException}" + super.getMessage();
	}

}
