/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @author Administrator
 */
public class Constants {
	/**
	 * 数据协议类型：自定义字节
	 */
	public static int PROTOCOL_TYPE_OWNBYTE = 1;
	/**
	 * 数据协议类型：JSON
	 */
	public static int PROTOCOL_TYPE_JSON = 2;
	/**
	 * 数据协议类型：google protocolbuffer
	 */
	public static int PROTOCOL_TYPE_PB = 3;
	/**
	 * 客户端发送至服务器消息 包头长度
	 */
	public static int CLIENTREQUEST_HEAD_LENGTH = 7;
	/**
	 * 服务器发送至客户端消息 包头长度
	 */
	public static int SERVERREPONSE_HEAD_LENGTH = 4;

	/**
	 * 客户端发送至服务器消息（消息体中除去数据体，头部体长度）
	 */
	public static int CLIENTREQUEST_CONTENT_HEADER_LENGTH = 2;

	/**
	 * 服务器发送至客户端消息（消息体中除去数据体，头部体长度）
	 */
	// public static int SERVERREQUEST_CONTENT_HEADER_LENGTH = 6;
	public static int SERVERREQUEST_CONTENT_HEADER_LENGTH = 2;// 取消多个消息体

	//
	public static CharsetDecoder DECODER = Charset.forName("UTF-8").newDecoder();
	public static CharsetEncoder ENCODER = Charset.forName("UTF-8").newEncoder();
	//
	public static final int DATATYPE_BYTE = 1;
	public static final int DATATYPE_SHORT = 2;
	public static final int DATATYPE_INT = 3;
	public static final int DATATYPE_STRING = 4;
	public static final int DATATYPE_LONG = 5;

}
