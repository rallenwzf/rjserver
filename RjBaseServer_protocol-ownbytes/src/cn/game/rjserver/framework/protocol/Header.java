/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol;

/**
 * @author Administrator
 * 
 */
public class Header {
	public byte protocolVersion;
	public byte packgeHeaderLength;
	public short dataLength;
	public short appID;// client
	public byte modeID;// client
	private boolean client = false;

	public Header() {
		protocolVersion = 0;
		packgeHeaderLength = 0;
		dataLength = 0;
		appID = 0;
		modeID = 0;
	}

	public byte getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(byte protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public byte getPackgeHeaderLength() {
		return packgeHeaderLength;
	}

	public void setPackgeHeaderLength(byte packgeHeaderLength) {
		this.packgeHeaderLength = packgeHeaderLength;
	}

	public short getDataLength() {
		return dataLength;
	}

	public void setDataLength(short dataLength) {
		this.dataLength = dataLength;
	}

	public short getAppID() {
		return appID;
	}

	public void setAppID(short appID) {
		client = true;
		this.appID = appID;
	}

	public byte getModeID() {
		return modeID;
	}

	public void setModeID(byte modeID) {
		this.modeID = modeID;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "protocolVersion=[" + protocolVersion + "],"
				+ "packgeHeaderLength=[" + packgeHeaderLength + "],"
				+ "dataLength=[" + dataLength + "],";
		if (client) {
			//客户端发给服务器的，存在appID,modeID
			str += "appID=[" + appID + "]," + "modeID=[" + modeID + "],";
		}
		return str;
	}
}
