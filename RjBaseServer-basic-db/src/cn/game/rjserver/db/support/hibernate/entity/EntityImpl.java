/*********************************************
    Copyright (c) 2011 by rallen
 *********************************************/

package cn.game.rjserver.db.support.hibernate.entity;

public class EntityImpl implements Entity, java.io.Serializable {
	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;

	protected Long id;
	protected boolean needUpdate;

	public EntityImpl() {
	}

	public boolean isNew() {
		return id == null;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long objId) {
		// TODO Auto-generated method stub
		this.id = objId;
	}

	public boolean isNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}

}
