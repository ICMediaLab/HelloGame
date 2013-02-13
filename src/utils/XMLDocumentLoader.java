package utils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * A utility class for loading XML documents as {@code Document} objects from specified {@code String} paths.<br />
 * Instantiation of this class should not occur.
 */
public final class XMLDocumentLoader {
	private XMLDocumentLoader() {}
	
	private static DocumentBuilder docBuilder;
	
	/**
	 * Ensures there is only ever one DocumentBuilder object in existence in order to reduce memory usage.
	 */
	private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		if(docBuilder == null) {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}else {
			docBuilder.reset();
		}
		return docBuilder;
	}
	
	/**
	 * Destroys the current documentBuilder object if present to free memory.
	 */
	private static void doneParsing() {
		docBuilder = null;
	}
	
	/**
	 * Gets the XML Document specified by the parameter given.
	 * @param path The path of this XML Document.
	 * @return The XML document located at {@code path} if it exists, else an exception may be thrown.
	 * @throws SAXException If any parsing errors occur. The document is likely structured incorrectly.
	 * @throws IOException If any IO errors occur.
	 * @throws ParserConfigurationException Something has gone horribly wrong.
	 */
	public static Document getXMLDocument(String path) throws SAXException, IOException, ParserConfigurationException {
		if(path != null){
			Document d = getDocumentBuilder().parse(new File(path));
			d.getDocumentElement().normalize();
			doneParsing();
			return d;
		}
		return null;
	}
	
	/**
	 * Gets an array of XML Document objects in order of the paths specified.<br />
	 * Null pointers are skipped and will return null documents.
	 * @param paths An array of paths to check for XML Documents.
	 * @return An array of XML Documents located at each of the paths specified. 
	 * @throws SAXException If any parsing errors occur. The document is likely structured incorrectly.
	 * @throws IOException If any IO errors occur.
	 * @throws ParserConfigurationException Something has gone horribly wrong.
	 */
	public static Document[] getXMLDocument(String[] paths) throws SAXException, IOException, ParserConfigurationException {
		Document[] docs = new Document[paths.length];
		for(int i=0;i<paths.length;i++) {
			if(paths[i] != null){
				docs[i] = getDocumentBuilder().parse(new File(paths[i]));
			}
		}
		doneParsing();
		return docs;
	}
	
	
}