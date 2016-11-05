/*********************************************
    Copyright (c) 2011 by rallen
 *********************************************/

package cn.game.rjserver.utils.sms.htmlparser;

/**
 * @author rallen 2011-4-7
 */
public class HttpConfiguration {
	private boolean followRedirects;
	private String defaultCode;

	public String getDefaultCode() {
		return defaultCode;
	}

	public void setDefaultCode(String defaultCode) {
		this.defaultCode = defaultCode;
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}
}
