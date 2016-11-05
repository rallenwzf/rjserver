/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol;

import cn.game.rjserver.framework.executor.GameIoSession;

/**
 * @author Administrator
 * 
 */
public class Message implements Cloneable {

	private String sessionKey;
	private BaseAgreement content;
	private GameIoSession session;

	public Message() {
	}

	public Message(String sessionKey, BaseAgreement content) {
		this.sessionKey = sessionKey;
		this.content = content;
	}

	public Message(BaseAgreement content, GameIoSession session) {
		this.content = content;
		this.session = session;
	}

	@Override
	protected Message clone() {
		// TODO Auto-generated method stub
		try {
			return (Message) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "{sessionKey}=" + sessionKey + " {content}=" + content;
		return str;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public BaseAgreement getContent() {
		return content;
	}

	public void setContent(BaseAgreement content) {
		this.content = content;
	}

	public GameIoSession getSession() {
		return session;
	}

	public void setSession(GameIoSession session) {
		this.session = session;
	}

}
