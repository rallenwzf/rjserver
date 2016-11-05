/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol;

/**
 * @author Administrator
 */
public class Property {
	private int dataType;
	private String name;
	private Object value;

	public Property(int dataType, Object value) {
		this.dataType = dataType;
		this.value = value;
	}

	public Property(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public int getDataType() {
		return dataType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public Object getValue() {
		if (value == null) {
			if (value instanceof String || dataType == Constants.DATATYPE_STRING) {
				value = "";
			}
			if (value instanceof Boolean && dataType == Constants.DATATYPE_BYTE) {
				if (value.toString().equalsIgnoreCase("true")) {
					value = 1;
				} else {
					value = 0;
				}
			}
		}
		// if (value.equals("")) {
		// value = " ";
		// }
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
