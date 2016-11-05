/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.executor;

/**
 * @author wangzhifeng(rallen)
 */
public interface ReceiveListener {
	public int receivedContentLength(int length);

	public void startRead();

	public void reading(int receivedLength);

	public void readFinished(byte[] bytes);
	
	public boolean isCansole();
}
