/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.datas;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.game.rjserver.basicutils.lang.DateUtils;
import cn.game.rjserver.uc.web.entity.CollectedInfo;

/**
 * @author rallen
 * 
 */
public class DatasManager {

	private Map<String, String> userCodeMap = new HashMap<String, String>();
	private Map<String, CollectedInfo> collectedInfoMap = new HashMap<String, CollectedInfo>();
	private static DatasManager manager = null;

	private DatasManager() {

	}

	public synchronized static DatasManager getDatasManager() {
		if (manager == null) {
			manager = new DatasManager();
		}
		return manager;
	}

	public void addCode(String key, String code) {
		userCodeMap.put(key.toLowerCase(), code);
		// datesMap.put(DateUtils.timestampToShortStr(new Date()), key);
	}

	public void addCollectedInfo(String useraccount, CollectedInfo info) {
		collectedInfoMap.put(useraccount, info);
	}

	public CollectedInfo getCollectedInfo(String useraccount) {
		if (collectedInfoMap.containsKey(useraccount)) {
			return collectedInfoMap.get(useraccount);
		}
		return null;
	}

	public Map<String, String> getUserCodeMap() {
		return userCodeMap;
	}

	public void setUserCodeMap(Map<String, String> userCodeMap) {
		this.userCodeMap = userCodeMap;
	}

	public Map<String, CollectedInfo> getCollectedInfoMap() {
		return collectedInfoMap;
	}

}
