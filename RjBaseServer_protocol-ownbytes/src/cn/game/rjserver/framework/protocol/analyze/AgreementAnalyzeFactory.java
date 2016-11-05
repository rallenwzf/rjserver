/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol.analyze;

/**
 * @author Administrator
 * 
 */
public class AgreementAnalyzeFactory {
	private static AgreementAnalyzeFactory factory;

	private AgreementAnalyze analyze;

	private AgreementAnalyze clientAnalyze;

	public static AgreementAnalyzeFactory getInstance() {
		if (factory == null) {
			factory = new AgreementAnalyzeFactory();
		}
		return factory;
	}

	public AgreementAnalyze getAnalyze() {
		return analyze;
	}

	public void setAnalyze(AgreementAnalyze analyze) {
		this.analyze = analyze;
	}

	public AgreementAnalyze getClientAnalyze() {
		return clientAnalyze;
	}

	public void setClientAnalyze(AgreementAnalyze clientAnalyze) {
		this.clientAnalyze = clientAnalyze;
	}

}
