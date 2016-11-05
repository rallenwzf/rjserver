/*********************************************
 * Copyright (c) 2011 by rallen
 *********************************************/

/**
 * a4a-poms-analysis
 * 2011-3-2
 */
package cn.game.rjserver.utils.filtword;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;

/**
 * 组合出lucene查询语句
 * 
 * @author rallen 2011-3-2
 * @doc class explain:
 */
public class QueryUtils {

	public static String INDEX_default = "content";
	public static String INDEX_source = "source";

	public static Query parser(String content) {
		return parser(content, true);
	}

	public static Query parser(String content, boolean isqieci) {
		try {
			String contstr = "";
			if (isqieci) {

				TokenStream stream = AnalyzerFactory.getInstance().buildAnalyzer()
						.tokenStream("", new StringReader(content));
				// 获取分词结果
				TermAttribute termAtt = stream.addAttribute(TermAttribute.class);
				try {
					while (stream.incrementToken()) {
						contstr += termAtt.term() + " ";
					}
					// System.out.println("contstr：" + contstr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				contstr = content;
			}
			Query query1 = MultiFieldQueryParser.parse(Version.LUCENE_30,
					contstr.trim().equals("") ? content : contstr, new String[] { QueryUtils.INDEX_default },
					new BooleanClause.Occur[] { BooleanClause.Occur.SHOULD }, AnalyzerFactory.getInstance()
							.buildAnalyzer());
			// Query query2 = new TermQuery(new Term(QueryUtils.INDEX_default,
			// content));
			BooleanQuery mainQuery = new BooleanQuery();
			mainQuery.add(query1, BooleanClause.Occur.SHOULD);
			// mainQuery.add(query2, BooleanClause.Occur.SHOULD);
			mainQuery.add(getWordsQuery(contstr, content), BooleanClause.Occur.SHOULD);
			return mainQuery;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @doc 测试索引查询
	 * @param word
	 * @return
	 * @throws Exception
	 *             Query
	 */
	public static Query creatTestQuery(String word) {

		try {
			// SimpleAnalyzer
			Query query = MultiFieldQueryParser.parse(Version.LUCENE_30, word,
					new String[] { QueryUtils.INDEX_default },
					new BooleanClause.Occur[] { BooleanClause.Occur.SHOULD }, AnalyzerFactory.getInstance()
							.buildAnalyzer());

			return query;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 手工输入的queryString生成query
	 * 
	 * @doc class explain:
	 * @param parma
	 * @return
	 */
	public static Query creatParserStringQuery(String queryString) {
		QueryParser parser = new QueryParser(Version.LUCENE_30, QueryUtils.INDEX_default, AnalyzerFactory.getInstance()
				.buildAnalyzer());
		try {
			return parser.parse(queryString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @doc class explain:
	 * @param words
	 * @return
	 */
	public static Query getWordsQuery(String words, String content) {
		if (words != null && !"".equals(words)) {
			BooleanQuery query = new BooleanQuery();
			String keys[] = words.split("[ ,]");
			for (int i = 0; i < keys.length; i++) {
				BooleanQuery b_query1 = new BooleanQuery();
				Query tQuery2 = new TermQuery(new Term(QueryUtils.INDEX_default, keys[i]));
				b_query1.add(tQuery2, BooleanClause.Occur.SHOULD);
				query.add(b_query1, BooleanClause.Occur.SHOULD);
			}
			return query;
		}
		return null;
	}

	/**
	 * @doc class explain: containing a main method
	 * @param args
	 *            void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
