/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.basicutils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author Administrator
 */
public class XmlBaseParser implements Serializable {

	private String xmlFilePath;
	private SAXReader saxReader;
	private Document document;
	private String encode;

	public XmlBaseParser() {
	}

	/**
	 * 更新属性值 containing a (XmlControl)'s upDateAttributeValue method
	 * 
	 * @param xpath
	 * @param attrName
	 * @param attrValue
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws DocumentException
	 *             void
	 */
	public void upDateAttributeValue(String xpath, String attrName, String attrValue)
			throws DocumentException, UnsupportedEncodingException, FileNotFoundException {
		List<Element> elements = getDocument().selectNodes(xpath);
		if (elements != null && elements.size() > 0) {
			Element em = elements.get(0);
			em.attribute(attrName).setValue(attrValue);
		}
	}

	/**
	 * 更新节点值 containing a (XmlControl)'s upDateText method
	 * 
	 * @param xpath
	 * @param value
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws DocumentException
	 *             void
	 */
	public void upDateText(String xpath, int num, String value) throws DocumentException,
			UnsupportedEncodingException, FileNotFoundException {
		List<Element> elements = getDocument().selectNodes(xpath);
		if (elements != null && elements.size() > 0) {
			Element em = elements.get(0);
			em.setText(value);
		}
	}

	/**
	 * containing a (AbstractXmlControler)'s getElementsAttrValue method
	 * 
	 * @param attName
	 * @param xpath
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 *             Set
	 */
	public Set getElementsAttrValue(String attName, String xpath) throws UnsupportedEncodingException,
			FileNotFoundException, DocumentException {
		Set set = new HashSet();
		List<Element> elements = getDocument().selectNodes(xpath);
		if (elements != null) {
			for (int i = 0; i < elements.size(); i++) {
				Attribute t = elements.get(i).attribute(attName);
				if (t != null) {
					set.add(t.getValue());
				}
			}
		}
		return set;
	}

	/**
	 * containing a (XmlControl)'s addElement method
	 * 
	 * @param em
	 *            void
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void addElement(Element em) throws UnsupportedEncodingException, FileNotFoundException,
			DocumentException {
		getRootElement().add(em);
	}

	/**
	 * 添加 元素 containing a (AbstractXmlControler)'s addElementAndAttr method
	 * 
	 * @param elementName
	 * @param attrs
	 * @param parentXpath
	 * @throws DocumentException
	 * @throws IOException
	 *             void
	 */
	public void addElement(String elementName, Map attrs, String parentXpath) throws DocumentException,
			IOException {
		if (elementName == null)
			return;

		Element rootElement = null;
		if (parentXpath == null) {
			rootElement = this.getRootElement();
		} else {
			try {
				rootElement = (Element) getDocument().selectNodes(parentXpath).get(0);
			} catch (Exception ex) {
			}
		}
		if (rootElement != null) {
			Element em = rootElement.addElement(elementName);
			if (attrs != null) {
				Set e = attrs.keySet();
				for (Iterator iter = e.iterator(); iter.hasNext();) {
					String str = (String) iter.next();
					em.addAttribute(str, attrs.get(str).toString());
				}
			}
		}
	}

	/**
	 * containing a (XmlControl)'s remove method
	 * 
	 * @param xpath
	 * @throws DocumentException
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 *             void
	 */
	public void remove(String xpath, String parentXpath) throws DocumentException,
			UnsupportedEncodingException, FileNotFoundException {
		List<Element> elements = getDocument().selectNodes(xpath);
		if (elements != null && elements.size() > 0) {
			Element rootElement = null;
			if (parentXpath == null) {
				rootElement = this.getRootElement();
			} else {
				try {
					rootElement = (Element) getDocument().selectNodes(parentXpath).get(0);
				} catch (Exception e) {
				}
			}
			if (rootElement != null) {
				for (int i = 0; i < elements.size(); i++) {
					Element em = elements.get(i);
					rootElement.remove(em);
				}
			}
		}
	}

	/**
	 * containing a (XmlControl)'s slectElement method
	 * 
	 * @param xpath
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 *             Element
	 */
	public Element slectElement(String xpath) throws UnsupportedEncodingException, FileNotFoundException,
			DocumentException {
		List<Element> elements = getDocument().selectNodes(xpath);
		if (elements != null && elements.size() > 0) {
			return elements.get(0);
		}
		return null;
	}

	/**
	 * containing a (XmlControl)'s getRootElement method
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 *             Element
	 */
	public Element getRootElement() throws UnsupportedEncodingException, FileNotFoundException,
			DocumentException {
		return this.getDocument().getRootElement();
	}

	/**
	 * 保存xml containing a (XmlControl)'s save method void
	 * 
	 * @throws DocumentException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void save() throws UnsupportedEncodingException, FileNotFoundException, IOException,
			DocumentException {
		try {
			if (document != null) {
				OutputFormat of = new OutputFormat();
				of.setIndent(true);
				of.setNewlines(true);
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(
						this.getXmlFilePath(), false), getEncode()), of);
				writer.write(document);
				writer.flush();
				writer.close();
			}
		} finally {
			close();
		}
	}

	/**
	 * containing a (AbstractXmlControler)'s removeFile method void
	 */
	public void removeFile() {
		File f = new File(this.getXmlFilePath());
		if (f.exists()) {
			f.delete();
		}
		close();
	}

	/**
	 * containing a (AbstractXmlControler)'s isHasFile method
	 * 
	 * @return boolean
	 */
	public boolean isHasFile() {
		// System.out.println("-----------sdfsdf--------------"+this.getXmlFilePath());
		File f = new File(this.getXmlFilePath());
		return f.exists();
	}

	/**
	 * containing a (XmlControl)'s getSaxReader method
	 * 
	 * @return SAXReader
	 */
	public SAXReader getSaxReader() {
		if (saxReader == null)
			saxReader = new SAXReader();
		return saxReader;
	}

	public void setSaxReader(SAXReader saxReader) {
		this.saxReader = saxReader;
	}

	/**
	 * containing a (XmlControl)'s getDocument method
	 * 
	 * @return
	 * @throws DocumentException
	 *             Document
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public Document getDocument() throws DocumentException, UnsupportedEncodingException,
			FileNotFoundException {
		if (document == null)
			document = this.getSaxReader().read(
					new InputStreamReader(new FileInputStream(new File(getXmlFilePath())), getEncode()));
		return document;
	}

	/**
	 * rootName 创建xml containing a (XmlControl)'s createDoc method
	 * 
	 * @param rootName
	 *            void
	 * @throws DocumentException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void createDoc(String rootName) throws UnsupportedEncodingException, FileNotFoundException,
			IOException, DocumentException {
		document = DocumentHelper.createDocument();
		Element root = document.addElement(rootName);
		this.save();
	}

	/**
	 * containing a (XmlControl)'s reset method void
	 */
	public void close() {
		if (document != null) {
			document.clearContent();
			document = null;
		}

	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getXmlFilePath() {
		return xmlFilePath;
	}

	public void setXmlFilePath(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
	}

	/**
	 * @return the XmlControl's encode
	 */
	public String getEncode() {
		if (encode == null) {
			encode = "utf-8";
		}
		return encode;
	}

	/**
	 * @param encode
	 *            the XmlControl's encode to set
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}

	// // 地址清除运行记录
	public static void clearSeConf(String path) {
		try {
			// File f=new File(path);
			// f.mkdirs();
			new File(path).delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
