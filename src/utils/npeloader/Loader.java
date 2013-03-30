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

/**
 * An abstract Entity loader for loading generic entities from xml files.
 * @param <T> The class of entity to be loaded and the class containing the load and clear methods.<br />
 * Must satisfy {@code <T extends Entity>}.
 */
public abstract class Loader<T extends Entity> {

	private static final String DEFAULT_LOAD = "load";
	private static final String DEFAULT_CLEAR = "clearLoaded";
	private final Method loadMethod;
	private final Method clearMethod;

	/**
	 * Create a new Loader object of specified loading and clearing methods.<br />
	 * Note both methods must be public and static.
	 * @param loadMethod
	 * @param clearMethod
	 */
	protected Loader(Method loadMethod, Method clearMethod){
		this.loadMethod = loadMethod;
		this.clearMethod = clearMethod;
	}
	
	/**
	 * Create a new Loader object with methods found in the class specified of the names specified.
	 * @param clazz The class containing the following two method members. Note both methods must be public and static.
	 * @param loadMethodName A case-sensitive string representation of the method name to be invoked when loading an entity of class T.
	 * @param clearMethodName A case-sensitive string representation of the method name to be invoked when clearing the set of loaded entities of class T.
	 */
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
	
	/**
	 * Creates a new Loader object based on the class specified and the default loading and clearing methods (i.e. {@code load} and {@code clearLoaded})
	 * @param clazz The class containing the two method members. Note both methods must be public and static.
	 */
	public Loader(Class<? extends T> clazz) {
		this(clazz,DEFAULT_LOAD,DEFAULT_CLEAR);
	}

	/**
	 * Attempts to invoke a static method with no context, printing the stack trace if the method does not exist, cannot be accessed, or has incorrect arguments.
	 * @param method The method to be invoked.
	 * @param args The arguments with which to invoke the method. It is recommended that arrays are explicitly cast as (Object) when passed.
	 */
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

	/**
	 * Clears all entities currently loaded in the set.
	 */
	public void clearLoaded(){
		printStackTraceInvoke(clearMethod);
	}
	
	/**
	 * Loads an entity to the set from the node specified.<br />
	 * Note this method will only attempt to load a single entity.
	 */
	private void loadItem(Node item){
		printStackTraceInvoke(loadMethod, item);
	}
	
	/**
	 * Loads a document asserting that the root node and it's child nodes have the specified name.
	 * @param d The document from which to load entities.
	 * @param root The root node value to be tested.
	 * @param node The child node value to be tested.
	 */
	protected final void load(Document d, String root, String node){
		loadOnly(d, root, node, null, null);
	}
	
	/**
	 * Loads a document asserting that the root node and it's child nodes have the specified name.<br />
	 * Additionally, it will check the attribute {@code lookup} and only load the node if it is contained by {@code only}.
	 * @param d The document from which to load entities.
	 * @param root The root node value to be tested.
	 * @param node The child node value to be tested.
	 * @param lookup The attribute to be queried.
	 * @param only The set of {@code lookup} values that should be loaded.
	 */
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
	
	/**
	 * Attempts to load all entities from xml documents corresponding to the paths specified.
	 */
	public final void load(String... paths){
		load(null,paths);
	}
	
	/**
	 * Attempts to load all entities from xml documents corresponding to the paths specified.<br />
	 * Will only load entities of lookup value contained by {@code only}. 
	 */
	public final void load(Collection<String> only, String... paths) {
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

	/**
	 * Attempts to load entities from an xml document specified. Will only load those with lookup values contained by the set {@code only}.<br />
	 * The details of the lookup attribute are left to the implementation.
	 */
	protected abstract void load(Document d, Collection<String> only);
	
}
