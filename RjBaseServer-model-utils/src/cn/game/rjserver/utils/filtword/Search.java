package cn.game.rjserver.utils.filtword;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.FSDirectory;

public abstract class Search {
	protected String[] indexDirs;
	protected Searcher searcher;
	protected boolean order;
	protected boolean autoClose = false;
	boolean isClosed = false;

	static Logger logger = Logger.getLogger(Searcher.class.getName());

	public Search() {

	}

	public Search(Searcher searcher) {
		this.searcher = searcher;
	}

	public String[] getIndexDirs() {
		if (indexDirs == null) {
			indexDirs = new String[] { AnalyzerFactory.INDEX_DIR };
		}
		return indexDirs;
	}

	public void addIndexDir(String indexDir) {
		this.getIndexDirs()[getIndexDirs().length] = indexDir;
	}

	public void setIndexDir(String[] indexDirs) {
		this.indexDirs = indexDirs;
		searcher = null;
	}

	public Searcher getSearcher() {
		if (searcher == null || isClosed) {
			try {
				List<IndexSearcher> searchers = new ArrayList<IndexSearcher>();
				for (int i = 0; i < getIndexDirs().length; i++) {
					// System.out.println("getIndexDirs()[i]ddddddddd=" +
					// getIndexDirs()[i]);
					if (new File(getIndexDirs()[i]).exists()) {
						// System.out.println("getIndexDirs()[i]=" +
						// getIndexDirs()[i]);
						searchers.add(new IndexSearcher(FSDirectory.open(new File(getIndexDirs()[i])), true));
					}
				}
				MultiSearcher multisearcher = new MultiSearcher(searchers.toArray(new IndexSearcher[0]));
				searcher = multisearcher;
				isClosed = false;
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return searcher;
	}

	public void setSearcher(Searcher searcher) {
		this.searcher = searcher;
	}

	public boolean isOrder() {
		return order;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

	public void close() {
		if (searcher != null) {
			try {
				searcher.close();
				isClosed = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}

	public void dispose() {
		close();
		searcher = null;
	}

	public boolean isAutoClose() {
		return autoClose;
	}

	public void setAutoClose(boolean autoClose) {
		this.autoClose = autoClose;
	}

	// /**
	// * @doc 执行搜索记录
	// * @param pageQuery
	// * @return SeoPageQuery
	// */
	// public abstract SeoPageQuery find(SeoPageQuery pageQuery);

	/**
	 * @doc 搜索到的总条数
	 * @param query
	 * @return int
	 */
	public abstract int findCount(Query query);

	/**
	 * 搜索到得doc词
	 * 
	 * @param query
	 * @return
	 */
	public abstract Set<String> find(Query query);

	// /**
	// * @doc 搜索的分组结果
	// * @param query
	// * @return int
	// */
	// public abstract BrowseResult findBrowseResult(SeoPageQuery pageQuery,
	// String[] groupBys);

	// public abstract BrowseResult findBrowseResult(Query query, String[]
	// groupBys,
	// boolean reloadHandlerList);
	// public abstract List<BrowseFacet> findBrowseResult(BrowseRequest
	// request,BoboIndexReader boboReader);

}
