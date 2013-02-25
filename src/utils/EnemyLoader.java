package utils;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import entities.enemies.Enemy;

/**
 * A utility class for loading enemy templates from XML documents from specified {@code String} paths.<br />
 * Instantiation of this class should not occur.<br />
 * Note additionally that the enemies template storage will not be automatically emptied unless {@code clearLoadedEnemies()} is run. 
 */
public final class EnemyLoader {
	private EnemyLoader(){} //should not be instantiated.
	
	/**
	 * Clears the enemy template storage.<br />
	 * This is equivalent to {@code Enemy.clearLoadedEnemies()}.
	 */
	public static final void clearLoadedEnemies(){
		Enemy.clearLoadedEnemies();
	}
	
	/**
	 * Loads enemies into the enemy template storage from a given XML document object.
	 */
	private static final void loadEnemies(Document d){
		if(d.getDocumentElement().getNodeName().equalsIgnoreCase("enemies")){
			NodeList nList = d.getElementsByTagName("enemy");
			for (int i = nList.getLength()-1; i >= 0; --i) {
				Enemy.loadEnemy(nList.item(i));
			}
		}
	}
	
	/**
	 * Loads specific enemies into the enemy template storage from a given XML document object.
	 */
	private static final void loadEnemies(Document d, List<String> enemiesToLoad){
		if(d.getDocumentElement().getNodeName().equalsIgnoreCase("enemies")){
			NodeList nList = d.getElementsByTagName("enemy");
			for (int i = nList.getLength()-1; i >= 0; --i) {
				Node item = nList.item(i);
				if(enemiesToLoad.contains(item.getAttributes().getNamedItem("name").getNodeValue())){
					Enemy.loadEnemy(item);
				}
			}
		}
	}
	
	/**
	 * Loads enemies into the enemy template storage from a given XML file.
	 * @param path The location of the XML file containing enemy information.
	 */
	public static final void loadEnemies(String path){
		if(path != null){
			try {
				loadEnemies(XMLDocumentLoader.getXMLDocument(path));
			} catch (SAXException e) { 
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads enemies into the enemy template storage from an array of given XML files.
	 * @param paths The locations of the XML files containing enemy information.
	 */
	public static final void loadEnemies(String[] paths){
		try {
			Document[] docs = XMLDocumentLoader.getXMLDocument(paths);
			for(Document d : docs){
				if(d != null){
					loadEnemies(d);
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
	 * Loads specific enemies into the enemy template storage from a given XML file.
	 * @param path The location of the XML file containing enemy information.
	 */
	public static final void loadEnemies(String path, List<String> enemiesToLoad){
		if(path != null){
			try {
				loadEnemies(XMLDocumentLoader.getXMLDocument(path),enemiesToLoad);
			} catch (SAXException e) { 
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads specific enemies into the enemy template storage from an array of given XML files.
	 * @param paths The locations of the XML files containing enemy information.
	 */
	public static final void loadEnemies(String[] paths, List<String> enemiesToLoad){
		try {
			Document[] docs = XMLDocumentLoader.getXMLDocument(paths);
			for(Document d : docs){
				if(d != null){
					loadEnemies(d,enemiesToLoad);
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
}
