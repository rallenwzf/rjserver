/**
 * rallen 
 */
package cn.game.rjserver.uc.exception;

/**
 * @author wangzhifeng(rallen)
 */
public class DbException extends Exception {
	public DbException(Throwable cause) {
		super(cause);
	}

	public DbException(String msg) {
		super(msg);
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "DbException:" + super.getMessage();
	}
}
