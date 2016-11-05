/**
 * rallen
 */
package cn.game.rjserver.uc.provider;

/**
 * @author wangzhifeng(rallen)
 */
public class UcProviderManager {
	private static UcProviderManager manager;

	public static UcProviderManager getInstance() {
		if (manager == null) {
			manager = new UcProviderManager();
		}
		return manager;
	}

	private GsmucUserProvider userProvider;
	private GsmucServerProvider serverProvider;

	public GsmucUserProvider getUserProvider() {
		return userProvider;
	}

	public void setUserProvider(GsmucUserProvider userProvider) {
		this.userProvider = userProvider;
	}

	public GsmucServerProvider getServerProvider() {
		return serverProvider;
	}

	public void setServerProvider(GsmucServerProvider serverProvider) {
		this.serverProvider = serverProvider;
	}

}
