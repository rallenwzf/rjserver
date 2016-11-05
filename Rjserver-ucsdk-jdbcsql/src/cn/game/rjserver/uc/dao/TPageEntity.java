/**
 * rallen 
 */
package cn.game.rjserver.uc.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public class TPageEntity<T> {
	private List<T> entityList;

	public List<T> getEntityList() {
		if (entityList == null) {
			entityList = new ArrayList<T>();
		}
		return entityList;
	}

	public void setEntityList(List<T> entityList) {
		this.entityList = entityList;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	private int pageCount;
}
