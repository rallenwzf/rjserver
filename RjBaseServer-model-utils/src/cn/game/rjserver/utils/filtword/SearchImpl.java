/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.utils.filtword;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;

/**
 * @author Administrator
 */
public class SearchImpl extends Search {

	@Override
	public int findCount(Query query) {
		// TODO Auto-generated method stub
		try {
			TopScoreDocCollector results = TopScoreDocCollector.create(1, false);
			this.getSearcher().search(query, results);
			// System.out.println("queryString=" + query.toString() + " 共检索到 " +
			// results.getTotalHits()
			// + " 条记录 ");
			logger.debug("queryString=" + query.toString() + " 共检索到 " + results.getTotalHits() + " 条记录 ");
			return results.getTotalHits();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (this.autoClose) {
			this.close();
		}
		return -1;
	}

	@Override
	public Set<String> find(Query query) {
		// TODO Auto-generated method stub
		Set<String> words = new HashSet<String>();
		try {
			// Sort sort = new Sort();
			TopScoreDocCollector results = TopScoreDocCollector.create(this.getSearcher().maxDoc(), false);
			this.getSearcher().search(query, results);
			// System.out.println("queryString=" + query.toString() + " 共检索到 " +
			// results.getTotalHits() + " 条记录 ");
			TopDocs tds = results.topDocs(0);
			ScoreDoc[] hits = tds.scoreDocs;
			// System.out.println("当前页记录数： " + hits.length);
			// List scoreList = new ArrayList();
			for (int i = 0; i < hits.length; i++) {

				Document doc = this.getSearcher().doc(hits[i].doc);
				// System.out.println("docstr=" +
				// doc.get(QueryUtils.INDEX_default) + " |source="
				// + doc.get(QueryUtils.INDEX_source));
				words.add(doc.get(QueryUtils.INDEX_source));
				// scoreList.add(hits[i].score);
				// System.out.println("hits[i].score=" + hits[i].score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return words;
	}

}
