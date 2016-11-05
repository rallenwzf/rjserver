/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.db.support.hibernate;

import java.util.List;
import java.util.Vector;

import cn.game.rjserver.db.support.hibernate.entity.EntityImpl;

/**
 * @author wangzhifeng(rallen)
 */
public class DBUpdateThread extends Thread {
	public Vector<EntityImpl> entities = new Vector<EntityImpl>();
	private static DBUpdateThread dBUpdateThread;

	private DBUpdateThread() {
	}

	public static DBUpdateThread getInstace() {
		if (dBUpdateThread == null) {
			dBUpdateThread = new DBUpdateThread();
		}
		return dBUpdateThread;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			for (int i = 0; i < 10; i++) {
				if (entities.size() > 0) {
					EntityImpl entity = entities.remove(0);
					try {
						BaseTblProvider.getInstance().saveOrUpdateEntityImp(entity);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					break;
				}
			}
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void add(EntityImpl entity) {
		entity.setNeedUpdate(false);
		entities.add(entity);
	}

	public void putTop(List entitis) {
		entities.addAll(0, entitis);
	}
	// public Vector<EntityImpl> getEntities() {
	// return entities;
	// }
	//
	// public void setEntities(Vector<EntityImpl> entities) {
	// this.entities = entities;
	// }

}
