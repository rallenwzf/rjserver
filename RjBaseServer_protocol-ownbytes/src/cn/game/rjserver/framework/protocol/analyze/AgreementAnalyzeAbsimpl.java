/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol.analyze;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Constants;
import cn.game.rjserver.framework.protocol.Header;
import cn.game.rjserver.framework.protocol.Message;
import cn.game.rjserver.framework.protocol.Property;

/**
 * @author Administrator
 */
public abstract class AgreementAnalyzeAbsimpl implements AgreementAnalyze {
	public static Logger logger = Logger.getLogger(AgreementAnalyzeAbsimpl.class.getName());

	public List<CommandFormatter> commandFormatters = new Vector<CommandFormatter>();

	// @Override
	// public Agreement requestTransform(String req) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public String responseTransform(Agreement agreement) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public int getEncodeAgreementLength(Agreement ag) {
		Header h = ag.getHeader();
		if (h == null) {
			h = ServerToCommand.getInstance().getDefaultHeader();
			ag.setHeader(h);
		}
		int s = h.getPackgeHeaderLength() + Constants.SERVERREQUEST_CONTENT_HEADER_LENGTH + ag.getDataLength(true);
		return s;
	}

	public void remove(CommandFormatter ct) {
		commandFormatters.remove(ct);
	}

	public void regist(CommandFormatter ct) {
		if (isRegist(ct))
			return;
		commandFormatters.add(ct);
	}

	public boolean isRegist(CommandFormatter ct) {
		return commandFormatters.contains(ct);
	}

	public List<CommandFormatter> getCommandFormatters() {
		return commandFormatters;
	}

}
