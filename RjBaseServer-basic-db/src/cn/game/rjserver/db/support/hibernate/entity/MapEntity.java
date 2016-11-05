/*********************************************
    Copyright (c) 2011 by rallen
 *********************************************/

package cn.game.rjserver.db.support.hibernate.entity;

/**
 * @author rallen 2011-4-27
 */
public class MapEntity {

	private Object key;
	private Object value;

	public MapEntity() {

	}

	public MapEntity(Object key, Object value) {
		this.key = key;
		this.value = value;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
