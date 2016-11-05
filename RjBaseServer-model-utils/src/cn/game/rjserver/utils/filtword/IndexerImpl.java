/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.utils.filtword;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

/**
 * @author rallen
 */
public class IndexerImpl implements Indexer {

	private String indexDir;
	private IndexWriter indexWriter;
	private FSDirectory fsDir;
	private boolean append = true;

	public IndexerImpl() {

	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

	public String getIndexDir() {
		if (indexDir == null) {
			indexDir = AnalyzerFactory.INDEX_DIR;
		}
		return indexDir;
	}

	public void setIndexDir(String indexDir) {
		this.indexDir = indexDir;
	}

	@Override
	public Document creatDocument(Object o) {
		// TODO Auto-generated method stub
		String content = o.toString();
		Document doc = new Document();
		// 将过滤词加入到索引文件中
		String tcontent = AnalyzerFactory.formatNumber(content.trim());
		Field field = new Field(QueryUtils.INDEX_default, tcontent, Field.Store.NO, Field.Index.NOT_ANALYZED,
				Field.TermVector.WITH_POSITIONS_OFFSETS);

		// 将过滤词加入到索引文件中
		Field field2 = new Field(QueryUtils.INDEX_source, content.trim(), Field.Store.YES,
				Field.Index.NOT_ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS);

		doc.add(field);
		doc.add(field2);
		return doc;
	}

	/**
	 * @doc class explain: containing a creatDocument method
	 * @param title
	 * @param content
	 * @return Document
	 */
	public Document creatDocument(String title, String content) {
		return null;
	}

	/**
	 * @doc 创建索引
	 * @param docmentList
	 *            void
	 */
	public void creatIndex(List<Document> docmentList) {
		try {
			indexWriter = this.getIndexWriter();
			for (Document doc : docmentList) {
				indexWriter.addDocument(doc);
			}
			indexWriter.optimize();
			indexWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @doc 创建索引
	 * @param doc
	 *            void
	 */
	public void creatIndex(Document doc) {
		try {
			indexWriter = this.getIndexWriter();
			indexWriter.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @doc class explain: containing a close method void
	 */
	public void close() {
		try {
			System.out.println("numDocs=" + indexWriter.numDocs());
			indexWriter.optimize();
			indexWriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	public void creatIndexWriter() throws IOException {
		if (fsDir == null) {
			fsDir = FSDirectory.open(new File(this.getIndexDir()));
		}
		if (indexWriter == null) {
			Analyzer analyzer = AnalyzerFactory.getInstance().buildAnalyzer();
			boolean creat = true;
			if (new File(this.getIndexDir()).exists() && this.isAppend()) {
				creat = false;
			}
			indexWriter = new IndexWriter(fsDir, analyzer, creat, new IndexWriter.MaxFieldLength(1000000));
		}
	}

	public IndexWriter getIndexWriter() throws CorruptIndexException, LockObtainFailedException, IOException {
		if (indexWriter == null) {
			creatIndexWriter();
		}
		return indexWriter;
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		// 获取索引存放的路径
		try {
			fsDir = FSDirectory.open(new File(this.getIndexDir()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.indexWriter = indexWriter;
	}

	/**
	 * @doc class explain: containing a main method
	 * @param args
	 *            void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Analyzer analyzer = AnalyzerFactory.getInstance().buildAnalyzer();
		IndexerImpl si = new IndexerImpl();
		List<Document> docmentList = new ArrayList<Document>();

		FiltWord fw = new FiltWord(si);
		fw.creatIndex();
	}

}
