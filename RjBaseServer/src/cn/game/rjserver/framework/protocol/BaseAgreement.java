/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

/**
 * @author wangzhifeng(rallen)
 */
public abstract class BaseAgreement {
	public static boolean SHOW_HEADER_LOG = false;
	protected short cmdId;// 指令的编号
	protected byte cmdType;// 指令的类型
	protected byte dataNum;
	protected int dataLength;
	protected String serialNumber;// 指令流水号
	protected boolean agent;
	//
	protected boolean server = true;

	protected int gzipLength = -1;
	private boolean succ = true;
	private long sid;

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public int getDataLength() {
		return this.toString().length();
	}

	public BaseAgreement() {
		init();
	}

	public BaseAgreement(short cmdid) {
		this.cmdId = cmdid;
		init();
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void init() {
		agent = false;
		cmdType = 0;
		dataNum = 1;
		dataLength = -1;
		serialNumber = new Date().getTime() + "" + new Random().nextInt(100000);
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public short getCmdId() {
		return cmdId;
	}

	public void setCmdId(short cmdId) {
		this.cmdId = cmdId;
	}

	public byte getCmdType() {
		return cmdType;
	}

	public void setCmdType(byte cmdType) {
		this.cmdType = cmdType;
	}

	public byte getDataNum() {
		return dataNum;
	}

	public void setDataNum(byte dataNum) {
		server = true;
		this.dataNum = dataNum;
	}

	public boolean isServer() {
		return server;
	}

	public void setServer(boolean server) {
		this.server = server;
	}

	public boolean isAgent() {
		return agent;
	}

	public void setAgent(boolean agent) {
		this.agent = agent;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getGzipLength() {
		return gzipLength;
	}

	public void setGzipLength(int gzipLength) {
		this.gzipLength = gzipLength;
	}

}
