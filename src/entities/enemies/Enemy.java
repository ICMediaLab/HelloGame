package entities.enemies;

import java.util.HashMap;
import java.util.Map;

import map.MapLoader;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import utils.ani.AnimationContainer;
import utils.ani.AnimationManager;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import entities.DestructibleEntity;
import entities.MovingEntity;
import entities.NonPlayableEntity;
import entities.StaticEntity;
import entities.aistates.decisiontree.AIDecisionTree;
import entities.players.Player;

public class Enemy extends NonPlayableEntity{
	
	private static final int ENEMY_DEFAULT_LAYER = -20;
	
	/**
	 * Map containing default representations of all enemies currently required.<br />
	 * New enemy instances can be created by cloning instances already existing here.<br />
	 * This is present in order to increase the ease with which enemies are loaded from xml.
	 */
	private static final Map<String,Enemy> enemies = new HashMap<String,Enemy>();

	private Enemy(float width,float height, int maxhealth, Element elemNode) throws SlickException{
		super(0,0,width,height,maxhealth,elemNode);
	}
	
	private Enemy(float x, float y, float width,float height, int maxhealth, AnimationManager ani, AIDecisionTree aiDecisionTree){
		super(x,y,width,height,maxhealth,ani,aiDecisionTree);
	}
	
	/**
	 * Copy constructor
	 */
	private Enemy(Enemy base) {
		super(base);
	}

	@Override
	public Enemy clone() {
		return new Enemy(this);
	}

	/**
	 * Gets a new instance of the object.<br />
	 * Implementations should not be case sensitive and not allow common references between entities.
	 * @param name The string representation of the entity to be created.
	 * @return A new Enemy object.
	 */
	public static Enemy getNew(String name){
		if(name == null){
			return null;
		}
		return enemies.get(name.toLowerCase()).clone();
	}
	
	/**
	 * Gets a new instance of the object.<br />
	 * Implementations should not be case sensitive and not allow common references between entities.
	 * @param name The string representation of the entity to be created.
	 * @param x The x coordinate of the newly created enemy.
	 * @param y The y coordinate of the newly created enemy.
	 * @return A new INonPlayableEntity object.
	 */
	public static Enemy getNew(String name, float x, float y){
		if(name == null){
			return null;
		}
		Enemy base = enemies.get(name.toLowerCase());
		Enemy ret = new Enemy(base);
		ret.setPosition(x, y);
		return ret;
	}
	
	/**
	 * Resets the entity storage such that (A)x.getNewEnemy(x) = null;
	 */
	public static void clearLoaded(){
		enemies.clear();
	}
	
	/**
	 * Adds an enemy to the enemy copy storage with the specified name.<br />
	 * Names are not treated as case-sensitive.
	 */
	public static void load(String name, Enemy e){
		if(name != null){
			enemies.put(name.toLowerCase(), e);
		}
	}
	
	/**
	 * Creates a new enemy from an XML node. This should not be used except when loading enemies into the enemy template storage.
	 * @throws ParseException 
	 * @throws SlickException 
	 * @throws DOMException 
	 */
	public static void load(Node node) throws ParseException, DOMException, SlickException {
		NamedNodeMap attrs = node.getAttributes();
		
		String name = attrs.getNamedItem("name").getNodeValue();
		int health = Integer.parseInt(attrs.getNamedItem("maxhealth").getNodeValue());
		
		//set up hitbox
		float width = 1, height = 1;
		try{
			width = Float.parseFloat(attrs.getNamedItem("width").getNodeValue());
		}catch(NullPointerException e){ }
		try{
			height = Float.parseFloat(attrs.getNamedItem("height").getNodeValue());
		}catch(NullPointerException e){ }
		
		Element elemNode = (Element) node;
		
		Enemy e = new Enemy(width,height,health, elemNode);
		load(name, e);
	}
	
	@Override
	public void update(GameContainer gc) {
		super.update(gc);
	    if (checkMapChanged()) {
	        die();
	        return;
	    }
		Player p = MapLoader.getCurrentCell().getPlayer();
		if (intersects(p)) {
		    p.takeDamage(1);
		}
		frameMove();
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		AnimationContainer sprite = getCurrentAnimationContainer();
		renderSprite(sprite);
		renderHealthBar((int) (sprite.getOffset().getY()/2f));
	}

	@Override
	public void collide(MovingEntity e) {
	}
	
	@Override
	public void collide(StaticEntity<?> e) {
	}

	@Override
	public int getLayer() {
		return ENEMY_DEFAULT_LAYER;
	}

	@Override
	public void collide(DestructibleEntity d) {
	}

}
