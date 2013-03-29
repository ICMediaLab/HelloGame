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
import entities.Entity;

public abstract class Loader<T extends Entity> {

	private static final String DEFAULT_LOAD = "load";
	private static final String DEFAULT_CLEAR = "clearLoaded";
	private final Method loadMethod;
	private final Method clearMethod;

	protected Loader(Method loadMethod, Method clearMethod){
		this.loadMethod = loadMethod;
		this.clearMethod = clearMethod;
	}
	
	public Loader(Class<? extends T> clazz, String loadMethodName, String clearMethodName) {
		Method load = null, clear = null;
		try {
			load  = clazz.getMethod(loadMethodName, Node.class);
			clear = clazz.getMethod(clearMethodName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		loadMethod = load;
		clearMethod = clear;
	}
	
	public Loader(Class<? extends T> clazz) {
		this(clazz,DEFAULT_LOAD,DEFAULT_CLEAR);
	}

	private void printStackTraceInvoke(Method method, Object... args){
		try {
			method.invoke(null, args);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void clearLoaded(){
		printStackTraceInvoke(clearMethod);
	}
	
	private void loadItem(Node item){
		printStackTraceInvoke(loadMethod, item);
	}
	
	protected final void load(Document d, String root, String node){
		loadOnly(d, root, node, null, null);
	}
	
	protected final void loadOnly(Document d, String root, String node, String lookup, Collection<String> only){
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
	
	public final void load(String... paths){
		load(null,paths);
	}

	private final void load(Collection<String> only, String... paths) {
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
