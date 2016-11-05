import cn.game.rjserver.uc.api.entity.CollectedInfo;
import cn.game.rjserver.uc.api.user.UserApiImpl;

/**
 * 
 */

/**
 * @author meishu
 * 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CollectedInfo info = new CollectedInfo();
//		new UserApiImpl().reg("123456", "123456", info);
		new UserApiImpl().login("123456", "123456", info);
	}

}
