package entities.npcs;

import java.util.HashMap;
import java.util.Map;

import map.Cell;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import entities.Entity;
import entities.NonPlayableEntity;
import entities.aistates.decisiontree.AIDecisionTree;

public class NPC extends NonPlayableEntity{
	
	private static final int NPC_DEFAULT_LAYER = -10;
	
	private final Animation left, right;
	private Animation sprite;
	
	/**
	 * Map containing default representations of all npcs currently required.<br />
	 * New npc instances can be created by cloning instances already existing here.<br />
	 * This is present in order to increase the ease with which enemies are loaded from xml.
	 */
	private static final Map<String,NPC> npcs = new HashMap<String,NPC>();
	
	private NPC(float x, float y, float width, float height, int maxhealth, Animation left, Animation right, String AIStr) {
		super(x,y,width,height,maxhealth,AIStr);
		this.left = left;
		this.right = right;
		sprite = right;
	}
	
	private NPC(float x, float y, float width, float height, int maxHealth, Animation left, Animation right, AIDecisionTree aiDecisionTree) {
		super(x,y,width,height,maxHealth,aiDecisionTree);
		this.left = left;
		this.right = right;
		sprite = right;
	}

	@Override
	public NPC clone() {
		return new NPC(getX(), getY(), getWidth(), getHeight(),getMaxHealth(), left, right,getAIDecisionTree());
	}
	
	/**
	 * Gets a new instance of the object.<br />
	 * Implementations should not be case sensitive and not allow common references between entities.
	 * @param name The string representation of the entity to be created.
	 * @return A new NPC object.
	 */
	public static NPC getNew(String name){
		if(name == null){
			return null;
		}
		return npcs.get(name.toLowerCase()).clone();
	}
	
	/**
	 * Gets a new instance of the object.<br />
	 * Implementations should not be case sensitive and not allow common references between entities.
	 * @param name The string representation of the entity to be created.
	 * @param x The x coordinate of the newly created npc.
	 * @param y The y coordinate of the newly created npc.
	 * @return A new NPC object.
	 */
	public static NPC getNew(Cell currentCell, String name, float x, float y){
		if(name == null){
			return null;
		}
		NPC base = npcs.get(name.toLowerCase());
		return new NPC(x,y, base.getWidth(), base.getHeight(),base.getMaxHealth(),base.left,base.right,base.getAIDecisionTree());
	}
	
	/**
	 * Resets the entity storage such that (A)x.getNewNPC(x) = null;
	 */
	public static void clearLoaded(){
		npcs.clear();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		renderSprite(sprite, 0, 0);
		renderHealthBar(-5);
	}
	
	/**
	 * Adds an npc to the npc copy storage with the specified name.<br />
	 * Names are not treated as case-sensitive.
	 */
	public static void load(String name, NPC e){
		if(name != null){
			npcs.put(name.toLowerCase(), e);
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void collide(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Creates a new npc from an XML node. This should not be used except when loading enemies into the npc template storage.
	 * @throws ParseException 
	 * @throws SlickException 
	 * @throws DOMException 
	 */
	public static void load(Node node) throws ParseException, DOMException, SlickException {
		NamedNodeMap attrs = node.getAttributes();
	}
	
	@Override
	public int getLayer() {
		return NPC_DEFAULT_LAYER;
	}
	
}
