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
 * @author Administrator <li>客户端与服务器端传输内容的文本格式的协议体(json or xml)</li>
 */
public class Agreement extends BaseAgreement implements Cloneable {
	public static boolean SHOW_HEADER_LOG = false;
	private Vector<Agreement> agreements;// 元素

	private Map datas = null;
	private String jsonstr = null;

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

	public Map getDatas() {
		return datas;
	}

	public void setDatas(Map datas) {
		this.datas = datas;
	}

	public String getJsonstr() {
		return jsonstr;
	}

	public void setJsonstr(String jsonstr) {
		this.jsonstr = jsonstr;
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
