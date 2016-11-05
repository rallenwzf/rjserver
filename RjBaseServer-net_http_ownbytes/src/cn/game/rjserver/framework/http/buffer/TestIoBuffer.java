/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.http.buffer;

import java.nio.ByteBuffer;


/**
 * @author wangzhifeng(rallen)
 *
 */
public abstract class TestIoBuffer implements Comparable<TestIoBuffer> {
	public abstract ByteBuffer buf();
	
}
