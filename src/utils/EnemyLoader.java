package utils;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
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
			for (int temp = nList.getLength()-1; temp >= 0; --temp) {
				new Enemy(nList.item(temp));
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
}
