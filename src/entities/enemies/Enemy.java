package entities.enemies;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.geom.Rectangle;

import entities.NonPlayableEntity;

public class Enemy extends NonPlayableEntity{
	
	/**
	 * Map containing default representations of all enemies currently required.<br />
	 * New enemy instances can be created by cloning instances already existing here.<br />
	 * This is present in order to increase the ease with which enemies are loaded from xml.
	 */
	private Map<String,Enemy> enemies = new HashMap<String,Enemy>();

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
	public Enemy getNewEnemy(String name){
		if(name == null){
			return null;
		}
		return (Enemy) enemies.get(name.toLowerCase()).clone();
	}
	
	/**
	 * Resets the entity storage such that (A)x.getNewEnemy(x) = null;
	 */
	public void clearLoadedEnemies(){
		enemies.clear();
	}
	
	/**
	 * Adds an enemy to the enemy copy storage with the specified name.<br />
	 * Names are not treated as case-sensitive.
	 */
	public void loadEnemy(String name, Enemy e){
		if(name != null){
			enemies.put(name.toLowerCase(), e);
		}
	}
}
