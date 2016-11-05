/**
 * rallen
 */
package cn.game.rjserver.framework.mina.analyze.impl;

import java.nio.charset.CharacterCodingException;

import org.apache.mina.core.buffer.IoBuffer;

import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Constants;
import cn.game.rjserver.framework.protocol.analyze.CommandFormatter;

/**
 * @author wangzhifeng(rallen)
 */
public class CommandFormatterAdapter implements CommandFormatter {

	@Override
	public Agreement decodeBody(Object bufObj, Agreement ag) {
		// TODO Auto-generated method stub

		return ag;
	}

	@Override
	public boolean accept(short cmdID) {
		// TODO Auto-generated method stub

		return false;
	}

	protected byte get(Object bufObj) {
		IoBuffer buf = (IoBuffer) bufObj;
		return buf.get();
	}

	protected short getShort(Object bufObj) {
		IoBuffer buf = (IoBuffer) bufObj;
		return buf.getShort();
	}

	protected int getInt(Object bufObj) {
		IoBuffer buf = (IoBuffer) bufObj;
		return buf.getInt();
	}

	protected long getLong(Object bufObj) {
		// IoBuffer buf = (IoBuffer) bufObj;
		// return buf.getLong();
		IoBuffer buf = (IoBuffer) bufObj;
		try {
			String str = buf.getString(buf.getUnsignedShort(), Constants.DECODER);
			return Long.parseLong(str);
		} catch (CharacterCodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	protected String getString(Object bufObj) {
		IoBuffer buf = (IoBuffer) bufObj;
		try {
			return buf.getString(buf.getUnsignedShort(), Constants.DECODER);
		} catch (CharacterCodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
