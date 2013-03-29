package utils.npeloader;

import java.util.Collection;

import org.w3c.dom.Document;

import entities.enemies.Enemy;

/**
 * A utility class for loading enemy templates from XML documents from specified {@code String} paths.<br />
 * Note additionally that the enemies template storage will not be automatically emptied unless {@code clearLoaded()} is run. 
 */
public final class EnemyLoader extends Loader {
	
	private static final String root="enemies",node="enemy",lookup="name";
	
	public EnemyLoader(){
		super(Enemy.class, "loadEnemy", "clearLoadedEnemies");
	}
	
	/**
	 * Loads specific enemies into the enemy template storage from a given XML document object.
	 */
	@Override
	protected final void load(Document d, Collection<String> enemiesToLoad){
		try {
			loadOnly(d, root, node, lookup, enemiesToLoad);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
