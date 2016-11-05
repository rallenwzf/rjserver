package cn.game.rjserver.basicutils.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

// 将一个字符串按照zip方式压缩和解压缩
public class ZipUtil {
	// 压缩
	public static String compressGzip(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		System.out.println("out.toByteArray()=" + out.toByteArray().length);
		return new BASE64Encoder().encodeBuffer(out.toByteArray());
		// return out.toString();
	}

	// 解压缩
	public static String uncompressGzip(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(
				new BASE64Decoder().decodeBuffer(str));
		// ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toString();
	}

	// 测试方法
	public static void main(String[] args) throws IOException {
		String str = "中国China是东方客户身份是东方客户撒地方是东方客";
		System.out.println(str.getBytes().length);
		System.out.println(ZipUtil.uncompressGzip(ZipUtil.compressGzip(str)));
	}

}
