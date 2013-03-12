package entities.enemies;

import java.util.HashMap;
import java.util.Map;

import map.Cell;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import utils.MapLoader;
import entities.Entity;
import entities.NonPlayableEntity;
import entities.players.Player;
import game.config.Config;

public class Enemy extends NonPlayableEntity{
	
	private final Animation left, right;
	private Animation sprite;
	
	/**
	 * Map containing default representations of all enemies currently required.<br />
	 * New enemy instances can be created by cloning instances already existing here.<br />
	 * This is present in order to increase the ease with which enemies are loaded from xml.
	 */
	private static final Map<String,Enemy> enemies = new HashMap<String,Enemy>();

	private Enemy(int width,int height, int maxhealth){
		super(width,height,maxhealth);
		Image[] movementRight = null;
		Image[] movementLeft = null;
		try {
			movementRight = new Image[]{new Image("data/images/dvl1_rt1.png"), new Image("data/images/dvl1_rt2.png")};
			movementLeft = new Image[]{new Image("data/images/dvl1_lf1.png"), new Image("data/images/dvl1_lf2.png")};
		} catch (SlickException e) {
			//do shit all
		}
		int[] duration = {200,200};
		right = new Animation(movementRight, duration, false);
		left = new Animation(movementLeft, duration, false);
		sprite = right;
	}
	
	private Enemy(float x, float y, int width, int height, int maxhealth) {
		super(x,y,width,height,maxhealth);
		Image[] movementRight = null;
		Image[] movementLeft = null;
		try {
			movementRight = new Image[]{new Image("data/images/dvl1_rt1.png"), new Image("data/images/dvl1_rt2.png")};
			movementLeft = new Image[]{new Image("data/images/dvl1_lf1.png"), new Image("data/images/dvl1_lf2.png")};
		} catch (SlickException e) {
			//do shit all
		}
		int[] duration = {200,200};
		right = new Animation(movementRight, duration, false);
		left = new Animation(movementLeft, duration, false);
		sprite = right;
	}

	@Override
	public Enemy clone() {
		return new Enemy(getX(), getY(), getWidth(), getHeight(),getMaxHealth());
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
	public static Enemy getNewEnemy(Cell currentCell, String name, float x, float y){
		if(name == null){
			return null;
		}
		Enemy base = enemies.get(name.toLowerCase());
		return new Enemy(x,y, base.getWidth(), base.getHeight(),base.getMaxHealth());
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
	public static void loadEnemy(Node node) {
		NamedNodeMap attrs = node.getAttributes();
		String name = attrs.getNamedItem("name").getNodeValue();
		int health = Integer.parseInt(attrs.getNamedItem("maxhealth").getNodeValue());
		int width = 1, height = 1;
		try{
			width = Integer.parseInt(attrs.getNamedItem("width").getNodeValue());
		}catch(NullPointerException e){ }
		try{
			height = Integer.parseInt(attrs.getNamedItem("width").getNodeValue());
		}catch(NullPointerException e){ }
		loadEnemy(name, new Enemy(width,height,health));
	}
	
	@Override
	public void update(Input input) {
	    if (isDead()) {
	        MapLoader.getCurrentCell().removeEntity(this);
	        return;
	    }
		super.updateEntity();
		if(getdX() < 0){
			sprite = left;
		}else if(getdX() > 0){
			sprite = right;
		}
		Player p = MapLoader.getCurrentCell().getPlayer();
		if (intersects(p)) {
		    p.takeDamage(1);
		}
		sprite.update(DELTA);
		frameMove();
	}
	
	@Override
	public void render() {
		sprite.draw((int)((getX()-1)*Config.getTileSize()), (int)((getY()-1)*Config.getTileSize()), new Color(255,255,255));
		
		// Health bar for debugging
		new Graphics().fillRect(getX()*32 - 32, getY()*32 - 32 - 25, 32*getHealth()/100, 3);
	}
}
