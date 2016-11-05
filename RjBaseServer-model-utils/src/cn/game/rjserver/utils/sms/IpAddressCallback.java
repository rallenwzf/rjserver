/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.utils.sms;

/**
 * @author Administrator
 * 
 */
public interface IpAddressCallback {
	/**
	 * 检测此ip的位置地址是否需要获取
	 * @param ip
	 * @return true 需要
	 */
	public boolean checkIp(String ip);

	/**
	 * 得到ip位置地址相关的文本
	 * @param ip
	 * @param addressText
	 */
	public void parserIpAddress(String ip, String addressText);
}
