package utils.npeloader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utils.XMLDocumentLoader;

public abstract class Loader {

	private final Method loadMethod;

	protected Loader(Method loadMethod){
		this.loadMethod = loadMethod;
	}
	
	public abstract void clearLoaded();
	
	private void loadItem(Node item){
		try {
			loadMethod.invoke(null,item);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	protected void load(Document d, String root, String node){
		loadOnly(d, root, node, null, null, loadMethod);
	}
	
	protected void loadOnly(Document d, String root, String node, String lookup, Collection<String> only, Method loadMethod){
		if(d.getDocumentElement().getNodeName().equalsIgnoreCase(root)){
			NodeList nList = d.getElementsByTagName(node);
			for (int i = nList.getLength()-1; i >= 0; --i) {
				Node item = nList.item(i);
				if(lookup == null || only == null || only.contains(item.getAttributes().getNamedItem(lookup).getNodeValue())){
					loadItem(item);
				}
			}
		}else{
			System.out.println("Invalid root node found in " + d);
		}
	}
	
	public void load(String... paths){
		load(null,paths);
	}

	private void load(Collection<String> only, String... paths) {
		try {
			Document[] docs = XMLDocumentLoader.getXMLDocument(paths);
			for(Document d : docs){
				if(d != null){
					load(d,only);
				}
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	protected abstract void load(Document d, Collection<String> only);
	
}
