/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.warn.cmd;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public class WarnInfo implements Cloneable {
	private int warnLevel;
	private int warnId;
	private String content;
	private String name;
	private int mode;
	private boolean needAnalysis;
	
	@Override
	protected WarnInfo clone() {
		// TODO Auto-generated method stub
		try {
			return (WarnInfo) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int getWarnLevel() {
		return warnLevel;
	}

	public void setWarnLevel(int warnLevel) {
		this.warnLevel = warnLevel;
	}

	public int getWarnId() {
		return warnId;
	}

	public void setWarnId(int warnId) {
		this.warnId = warnId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public boolean isNeedAnalysis() {
		return needAnalysis;
	}

	public void setNeedAnalysis(boolean needAnalysis) {
		this.needAnalysis = needAnalysis;
	}

}
