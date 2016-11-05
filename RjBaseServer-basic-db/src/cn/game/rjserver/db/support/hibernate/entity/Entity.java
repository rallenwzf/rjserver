/*********************************************
    Copyright (c) 2011 by rallen
 *********************************************/
package cn.game.rjserver.db.support.hibernate.entity;

public interface Entity {

	public abstract boolean isNew();

	public abstract Long getId();

	public abstract void setId(Long integer);

	public boolean isNeedUpdate();

	public void setNeedUpdate(boolean needUpdate);
}
