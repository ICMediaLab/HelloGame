package utils.npeloader;

import java.lang.reflect.Method;
import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import entities.enemies.Enemy;

/**
 * A utility class for loading enemy templates from XML documents from specified {@code String} paths.<br />
 * Note additionally that the enemies template storage will not be automatically emptied unless {@code clearLoadedEnemies()} is run. 
 */
public final class EnemyLoader extends Loader {
	
	private static final Method loadMethod;
	
	static {
		Method load = null;
		try {
			load = Enemy.class.getMethod("loadEnemyFromNode", Node.class);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		loadMethod = load;
	}
	
	public EnemyLoader(){
		super(loadMethod);
	}
	
	/**
	 * Clears the enemy template storage.<br />
	 * This is equivalent to {@code Enemy.clearLoadedEnemies()}.
	 */
	public final void clearLoaded(){
		Enemy.clearLoadedEnemies();
	}
	
	/**
	 * Loads specific enemies into the enemy template storage from a given XML document object.
	 */
	protected final void load(Document d, Collection<String> enemiesToLoad){
		try {
			loadOnly(d, "enemies", "enemy", "name", enemiesToLoad, loadMethod);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
