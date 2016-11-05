/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.utils.filtword;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.LockObtainFailedException;

/**
 * @author Administrator
 * 
 */
public interface Indexer {
	public boolean isAppend();

	public void setAppend(boolean append);

	public String getIndexDir();

	public void setIndexDir(String indexDir);

	/**
	 * @doc 格式化文章
	 * @param doc
	 * @return Document
	 */
	public Document creatDocument(Object o);

	/**
	 * @doc class explain: containing a creatDocument method
	 * @param title
	 * @param content
	 * @return Document
	 */
	public Document creatDocument(String title, String content);

	/**
	 * @doc 创建索引
	 * @param docmentList
	 *            void
	 */
	public void creatIndex(List<Document> docmentList);

	/**
	 * @doc 创建索引
	 * @param doc
	 *            void
	 */
	public void creatIndex(Document doc);

	/**
	 * @doc class explain: containing a close method void
	 */
	public void close();

	public void creatIndexWriter() throws IOException;

	public IndexWriter getIndexWriter() throws CorruptIndexException,
			LockObtainFailedException, IOException;

	public void setIndexWriter(IndexWriter indexWriter);
}
