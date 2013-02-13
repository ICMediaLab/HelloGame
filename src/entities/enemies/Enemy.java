package entities.enemies;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.geom.Rectangle;
import org.w3c.dom.Node;

import entities.NonPlayableEntity;

public class Enemy extends NonPlayableEntity{
	
	/**
	 * Map containing default representations of all enemies currently required.<br />
	 * New enemy instances can be created by cloning instances already existing here.<br />
	 * This is present in order to increase the ease with which enemies are loaded from xml.
	 */
	private static final Map<String,Enemy> enemies = new HashMap<String,Enemy>();

	private Enemy(Rectangle hitbox, int maxhealth) {
		super(hitbox, maxhealth);
	}
	
	@Override
	protected Object clone() {
		return new Enemy(new Rectangle(getX(), getY(), getWidth(), getHeight()),getMaxHealth());
	}

	/**
	 * Gets a new instance of the object.<br />
	 * Implementations should not be case sensitive and not allow common references between entities.
	 * @param name The string representation of the entity to be created.
	 * @return A new INonPlayableEntity object.
	 */
	public static Enemy getNewEnemy(String name){
		if(name == null){
			return null;
		}
		return (Enemy) enemies.get(name.toLowerCase()).clone();
	}
	
	/**
	 * Gets a new instance of the object.<br />
	 * Implementations should not be case sensitive and not allow common references between entities.
	 * @param name The string representation of the entity to be created.
	 * @param x The x coordinate of the newly created enemy.
	 * @param y The y coordinate of the newly created enemy.
	 * @return A new INonPlayableEntity object.
	 */
	public static Enemy getNewEnemy(String name, int x, int y){
		if(name == null){
			return null;
		}
		Enemy base = enemies.get(name.toLowerCase());
		return new Enemy(new Rectangle(x,y, base.getWidth(), base.getHeight()),base.getMaxHealth());
	}
	
	/**
	 * Resets the entity storage such that (A)x.getNewEnemy(x) = null;
	 */
	public static void clearLoadedEnemies(){
		enemies.clear();
	}
	
	/**
	 * Adds an enemy to the enemy copy storage with the specified name.<br />
	 * Names are not treated as case-sensitive.
	 */
	public static void loadEnemy(String name, Enemy e){
		if(name != null){
			enemies.put(name.toLowerCase(), e);
		}
	}
	
	/**
	 * Creates a new enemy from an XML node. This should not be used except when loading enemies into the enemy template storage.
	 */
	public Enemy(Node node) {
		super(null,0);
		//TODO: implement
	}
}
