/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

/**
 * @author Administrator <li>客户端与服务器端传输内容的自定义协议体</li>
 */
public class Agreement extends AgStack implements Cloneable {
	public static boolean SHOW_HEADER_LOG = false;
	private Vector<Agreement> agreements;// 元素
	private Header header;

	public Agreement() {
		init();
	}

	public Agreement(short cmdid) {
		this.cmdId = cmdid;
		init();
	}

	@Override
	public Agreement clone() {
		// TODO Auto-generated method stub
		try {
			return (Agreement) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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

	public Vector<Agreement> getAgreements() {
		if (agreements == null) {
			agreements = new Vector<Agreement>();
		}
		return agreements;
	}

	public void setAgreements(Vector<Agreement> agreements) {
		this.agreements = agreements;
	}

	public void addAgreement(Agreement ag) {
		if (agreements == null) {
			agreements = new Vector<Agreement>();
		}
		agreements.add(ag);
	}

	public int getAgreementCount() {
		int num = 1;
		if (agreements != null) {
			return getAgreementCount(this);
		}
		return num;
	}

	private int getAgreementCount(Agreement ag) {
		if (ag == null) {
			return 0;
		}
		int num = 1;
		if (ag.agreements != null) {
			for (int i = 0; i < ag.agreements.size(); i++) {
				Agreement agr = ag.agreements.get(i);
				num += getAgreementCount(agr);
			}
			return num;
		} else {
			return num;
		}
	}

	public int getGzipLength() {
		return gzipLength;
	}

	public void setGzipLength(int gzipLength) {
		this.gzipLength = gzipLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Agreement addAttr(Property p) {
		if (attrsList == null) {
			attrsList = new Vector<Property>();
		}
		dataLength = -1;
		attrsList.add(p);
		return this;
	}

	/**
	 * @return 获取数据体的数据长度
	 */
	public int getDataLength(boolean reload) {

		if (dataLength == -1 || reload) {
			int length = 0;
			if (this.getAttrsList() != null) {
				for (int i = 0; i < this.getAttrsList().size(); i++) {
					Property p = this.getAttrsList().get(i);
					if (p.getDataType() == Constants.DATATYPE_BYTE) {
						length += 1;
					} else if (p.getDataType() == Constants.DATATYPE_INT) {
						// length += 4;
						if (p.getValue() == null)
							p.setValue("");
						length += p.getValue().toString().getBytes(Constants.ENCODER.charset()).length + 2;
					} else if (p.getDataType() == Constants.DATATYPE_SHORT) {
						length += 2;
					} else if (p.getDataType() == Constants.DATATYPE_STRING) {
						length += p.getValue().toString().getBytes(Constants.ENCODER.charset()).length + 2;
					} else if (p.getDataType() == Constants.DATATYPE_LONG) {
						// length += 8;
						if (p.getValue() == null)
							p.setValue("");
						length += p.getValue().toString().getBytes(Constants.ENCODER.charset()).length + 2;
					}
				}
			}
			dataLength = length;
		}
		return dataLength;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		try {
			String str = "gzipLength=[" + this.getGzipLength() + "] ";
			if (SHOW_HEADER_LOG) {
				str += "cmdid=[" + cmdId + "]" + "	serialNumber=[" + this.getSerialNumber() + "];";
				if (header != null) {
					str += "header={" + this.getHeader().toString() + "};";
				}
				if (server) {
					// 服务器发给客户端的，存在dataNum,cmdType,dataLength=
					str += "dataNum=[" + dataNum + "]," + "cmdType=[" + cmdType + "]," + "dataLength=[" + (getDataLength(false) + 2) + "],";
				}
			}
			str += "cmdid=[" + cmdId + "],";
			if (this.attrsList != null) {
				str += "data={";
				for (int i = 0; i < this.getAttrsList().size(); i++) {
					Property p = this.getAttrsList().get(i);
					str += p.getDataType() + "," + (p.getValue() != null ? p.getValue().toString() : "") + ";";
				}
				str += "}";
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return "error：" + e.getMessage();
		}
	}

	public static void main(String[] args) {
		Agreement ag = new Agreement((short) 1);
		ag.addAgreement(new Agreement((short) 2));
		ag.addAgreement(new Agreement((short) 3));
		Agreement ag2 = new Agreement((short) 4);
		ag.addAgreement(ag2);
		ag2.addAgreement(new Agreement((short) 101));

		System.out.println(ag.getAgreementCount());
	}
}
