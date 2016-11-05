/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol;

import java.util.List;
import java.util.Vector;

/**
 * @author wangzhifeng(rallen)
 */
public class AgStack extends BaseAgreement {
	protected int popId = 0;
	protected List<Property> attrsList;// 只按顺序存储值域

	public AgStack() {
		attrsList = new Vector<Property>();
	}

	/**
	 * 获得发送过来的第几个参数
	 * 
	 * @param popId
	 *            从0开始计数
	 * @return
	 */
	public long nextOneLong(int popId) {
		long l = 0;
		try {
			if (attrsList.size() > popId) {
				Property p = attrsList.get(popId);
				if (p != null) {
					l = Long.parseLong(p.getValue().toString());
				}
			}
		} catch (Exception e) {
		}
		return l;
	}

	public String nextOneString(int popId) {
		String s = null;
		try {
			if (attrsList.size() > popId) {
				Property p = attrsList.get(popId);
				if (p != null) {
					s = p.getValue().toString();
				}
			}
		} catch (Exception e) {
		}
		return s;
	}

	public byte nextOneByte(int popId) {
		byte b = 1;
		try {
			if (attrsList.size() > popId) {
				Property p = attrsList.get(popId);
				if (p != null) {
					b = Byte.parseByte(p.getValue().toString());
				}
			}
		} catch (Exception e) {
		}
		return b;
	}

	public byte nextByte() {
		byte b = -1;
		if (attrsList.size() > popId) {
			Property p = attrsList.get(this.popId);
			if (p != null) {
				b = Byte.parseByte(p.getValue().toString());
			}
		}
		popNext();
		return b;
	}

	public int nextInt() {
		int b = -1;
		if (attrsList.size() > popId) {
			Property p = attrsList.get(this.popId);
			if (p != null) {
				b = Integer.parseInt(p.getValue().toString());
			}
		}
		popNext();
		return b;
	}

	public short nextShort() {
		short b = -1;
		if (attrsList.size() > popId) {
			Property p = attrsList.get(this.popId);
			if (p != null) {
				b = Short.parseShort(p.getValue().toString());
			}
		}
		popNext();
		return b;
	}

	public long nextLong() {
		long l = 0;
		if (attrsList.size() > popId) {
			Property p = attrsList.get(this.popId);
			if (p != null) {
				l = Long.parseLong(p.getValue().toString());
			}
		}
		popNext();
		return l;
	}

	public String nextString() {
		String b = "";
		if (attrsList.size() > popId) {
			Property p = attrsList.get(this.popId);
			if (p != null) {
				b = p.getValue().toString();
			}
		}
		popNext();
		return b;
	}

	public Object nextObject() {
		Object b = "";
		if (attrsList.size() > popId) {
			Property p = attrsList.get(this.popId);
			if (p != null) {
				b = p.getValue();
			}
		}
		popNext();
		return b;
	}

	public List<Property> getAttrsList() {
		return attrsList;
	}

	public void setAttrsList(List<Property> attrsList) {
		this.attrsList = attrsList;
	}

	/**
	 * 下标移动
	 * 
	 * @return
	 */
	private int popNext() {
		return ++popId;
	}

	public int getPopId() {
		return popId;
	}

	public void setPopId(int popId) {
		this.popId = popId;
	}

}
