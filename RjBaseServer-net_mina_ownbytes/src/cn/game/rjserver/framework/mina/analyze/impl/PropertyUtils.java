/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.mina.analyze.impl;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;

import cn.game.rjserver.framework.protocol.Constants;
import cn.game.rjserver.framework.protocol.Property;

/**
 * @author Administrator
 * 
 */
public class PropertyUtils {

	/**
	 * @param p
	 * @param buf
	 */
	public static int encodeProperty(Property p, IoBuffer buf) {
		switch (p.getDataType()) {
		case Constants.DATATYPE_BYTE: {
			buf.put(Byte.parseByte(p.getValue() == null ? 0 + "" : p.getValue()
					.toString()));
			return 1;
		}
		case Constants.DATATYPE_SHORT: {
			buf.putShort(Short.parseShort(p.getValue() == null ? 0 + "" : p
					.getValue().toString()));
			return 2;
		}
		case Constants.DATATYPE_INT: {
//			buf.putInt(Integer.parseInt(p.getValue() == null ? 0 + "" : p
//					.getValue().toString()));
//			return 4;
			String str = p.getValue() == null ? "" : p.getValue().toString();
			byte b[] = str.getBytes(Constants.ENCODER.charset());
			buf.putShort((short) b.length);
			buf.put(b);
			return b.length + 2;
		}
		case Constants.DATATYPE_STRING: {
			String str = p.getValue() == null ? "" : p.getValue().toString();
			byte b[] = str.getBytes(Constants.ENCODER.charset());
			// try {
			// buf.putString(str, b.length, ENCODER);
			// buf.putString(str, b.length + 2, ENCODER);
			buf.putShort((short) b.length);
			buf.put(b);
			// } catch (CharacterCodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			return b.length + 2;
		}
		case Constants.DATATYPE_LONG: {
//			buf.putLong(Long.parseLong(p.getValue() == null ? 0 + "" : p
//					.getValue().toString()));
//			return 8;
			String str = p.getValue() == null ? "" : p.getValue().toString();
			byte b[] = str.getBytes(Constants.ENCODER.charset());
			// try {
			// buf.putString(str, b.length, ENCODER);
			// buf.putString(str, b.length + 2, ENCODER);
			buf.putShort((short) b.length);
			buf.put(b);
			// } catch (CharacterCodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			return b.length + 2;
		}
		default:
			break;
		}
		return 0;
	}
}
