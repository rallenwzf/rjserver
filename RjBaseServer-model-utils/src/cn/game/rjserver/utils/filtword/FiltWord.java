/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.utils.filtword;

import java.io.File;
import java.util.Set;

import org.apache.log4j.Logger;
import org.wltea.analyzer.dic.Dictionary;

import cn.game.rjserver.utils.ModelUtilsConfig;

/**
 * @author Administrator
 */
public class FiltWord {
	static Logger logger = Logger.getLogger(FiltWord.class.getName());
	private Indexer indexer;
	private Search search;
	private static FiltWord filtWord;

	private FiltWord() {
	}

	public FiltWord(Indexer indexer, Search search) {
		this.indexer = indexer;
		this.search = search;
	}

	public FiltWord(Indexer indexer) {
		this.indexer = indexer;
	}

	public FiltWord(Search search) {
		this.search = search;
	}

	public Indexer getIndexer() {
		return indexer;
	}

	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public void loadExtendsWord() {
		AnalyzerFactory.getInstance();
	}

	public static FiltWord getFiltWord() {
		if (filtWord == null) {
			filtWord = new FiltWord();
		}
		return filtWord;
	}

	/**
	 * 将过滤词作为doc建立索引
	 */
	public void creatIndex() {
		String path = ModelUtilsConfig.getConfig("filtWordsPath");
		try {
			if (!new File(indexer.getIndexDir()).exists()) {
				logger.info("建立索引...");
				// extendword
				if (path != null) {
					Set<String> extendsWords = AnalyzerFactory.loadWords(new File(path), false);
					for (String w : extendsWords) {
						if (w != null && !w.trim().equals("")) {
							indexer.creatIndex(indexer.creatDocument(w));
						}
					}
					extendsWords.clear();
					extendsWords = null;
				}
				logger.info("索引完成");
			}

		} finally {
			indexer.close();
		}
	}

	/**
	 * 替换需过滤的词
	 * 
	 * @param content
	 * @param replacement
	 * @return
	 */
	public String searchFilt(String content, String replacement) {
		String tcontent = AnalyzerFactory.formatNumber(content);
		// System.out.println(content + "___" + tcontent);
		Set<String> set = search.find(QueryUtils.parser(tcontent));
		if (set != null && set.size() > 0) {
			for (String s : set) {
				content = content.replaceAll(s, replacement);
			}
			return content;
		} else {
			return content;
		}
	}

	/**
	 * 是否存在过滤词
	 * 
	 * @param content
	 * @return
	 */
	public boolean searchFilt(String content, boolean isqieci) {
		String tcontent = AnalyzerFactory.formatNumber(content);
		// System.out.println(content + "___" + tcontent);
		int num = search.findCount(QueryUtils.parser(tcontent, isqieci));
		// System.out.println(content + "___" + tcontent + "___num=" + num);
		if (num > 0) {
			return true;
		} else {
			return false;
		}
	}

}
