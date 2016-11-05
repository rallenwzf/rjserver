package cn.game.rjserver.log.api;

public interface WriteDatasLog {

	public abstract void writeMessage(String funcName, int type, String contentStr);

	public abstract void writeMessage(String funcName, int type, Object[] contentStr);

}