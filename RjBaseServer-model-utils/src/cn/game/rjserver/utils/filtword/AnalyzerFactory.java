/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.utils.filtword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.game.rjserver.utils.ModelUtilsConfig;

/**
 * @author
 */
public class AnalyzerFactory {
	static Logger logger = Logger.getLogger(AnalyzerFactory.class.getName());
	private static Analyzer singletion = null;

	public static String INDEX_DIR = null;

	private static AnalyzerFactory factory = null;

	public static synchronized AnalyzerFactory getInstance() {
		if (factory == null) {
			factory = new AnalyzerFactory();
			INDEX_DIR = ModelUtilsConfig.getConfig("indexDir");
			if (INDEX_DIR == null) {
				INDEX_DIR = "luceneIndex";
			}
		}
		return factory;
	}

	public AnalyzerFactory() {
		buildAnalyzer();
	}

	/**
	 * 分词器IK_CAnalyzer的创建
	 * 
	 * @throws Exception
	 */
	public synchronized Analyzer buildAnalyzer() {
		if (singletion == null) {
			rebuild();
		}
		return singletion;
	}

	/**
	 * @doc class explain: 默认只加载文件中的词库
	 */
	public synchronized void rebuild() {
		try {
			singletion = new IKAnalyzer();
			long start = new Date().getTime();
			int sum = 0;
			String path = ModelUtilsConfig.getConfig("filtWordsPath");
			logger.info("加载词库...path=" + path);
			// extendword
			if (path != null) {
				Set extendsWords = loadWords(new File(path), true);
				Dictionary.loadExtendWords(extendsWords);
				sum = extendsWords.size();
				extendsWords.clear();
				extendsWords = null;
			}
			long end = new Date().getTime();
			logger.info("加载文件存储词库用时：" + (end - start) / 1000 + "s" + "  词库大小为：" + sum);

		} catch (Exception e) {
		}
	}

	public static Set<String> loadWords(File keydir, boolean needFormatNumber) {
		Set<String> t = new HashSet<String>();
		if (keydir.isDirectory()) {
			File[] fs = keydir.listFiles();
			for (int i = 0; i < fs.length; i++) {
				try {
					File f = fs[i];
					if (f.isDirectory()) {
						t.addAll(loadWords(f, needFormatNumber));
					} else {
						t.addAll(readFile(f.getPath(), t, null, needFormatNumber));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			t.addAll(readFile(keydir.getPath(), t, null, needFormatNumber));
		}
		return t;
	}

	public static String getTokenString(String termString) {
		// IKAnalyzer切词会统一使用小写，
		return termString.toLowerCase();
	}

	/**
	 * @doc class explain: containing a readFile method
	 * @param filePath
	 * @param col
	 * @return Collection
	 */
	public static Collection readFile(String filePath, Collection col, String code, boolean needFormatNumber) {
		try {
			if (col == null)
				col = new HashSet<String>();
			if (code == null)
				code = "UTF-8";
			File f = new File(filePath);
			if (f.exists()) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(filePath)), code));
				String line;
				// 循环读取内容
				while ((line = reader.readLine()) != null) {
					if (line != null && !line.trim().equals("")) {
						if (needFormatNumber) {
							col.add(formatNumber(line));
						} else {
							col.add(line);
						}
					}
				}
				return col;
			}
			return col;
		} catch (Exception e) {
			e.printStackTrace();
			return col;
		}
	}

	/**
	 * @doc 加载自定义词库
	 * @param keyset
	 *            void
	 */
	public synchronized void loadKeyword(Set keyset) {
		Dictionary.loadExtendWords(keyset);
	}

	public static String formatNumber(String words) {
		if (words.matches(".*\\d+.*")) {
			String str = "";
			for (int i = 0; i < words.length(); i++) {
				if ((words.charAt(i) + "").matches("\\d+")) {
					String tt = words.charAt(i) + "";
					switch (words.charAt(i)) {
					case '0':
						tt = "零";
						break;
					case '1':
						tt = "一";
						break;
					case '2':
						tt = "二";
						break;
					case '3':
						tt = "三";
						break;
					case '4':
						tt = "四";
						break;
					case '5':
						tt = "五";
						break;
					case '6':
						tt = "六";
						break;
					case '7':
						tt = "七";
						break;
					case '8':
						tt = "八";
						break;
					case '9':
						tt = "九";
						break;
					default:
						break;
					}
					str += tt;
				} else {
					str += words.charAt(i);
				}
			}
			return str;
		}
		return words;
	}

}
