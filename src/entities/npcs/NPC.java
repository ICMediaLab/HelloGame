package entities.npcs;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import utils.ani.AnimationContainer;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import entities.DestructibleEntity;
import entities.MovingEntity;
import entities.NonPlayableEntity;
import entities.StaticEntity;
import entities.objects.TextField;

public class NPC extends NonPlayableEntity{
	
	private static final int NPC_DEFAULT_LAYER = -10;
	
	private TextField<?> tf = null;
	
	/**
	 * Map containing default representations of all npcs currently required.<br />
	 * New npc instances can be created by cloning instances already existing here.<br />
	 * This is present in order to increase the ease with which enemies are loaded from xml.
	 */
	private static final Map<String,NPC> npcs = new HashMap<String,NPC>();
	
	/**
	 * Should only be used when initialising npcs list
	 */
	private NPC(float width, float height, int maxhealth, Element elemNode) throws SlickException {
		this(0,0,width,height,maxhealth,elemNode);
	}
	
	private NPC(float x, float y, float width, float height, int maxhealth, Element elemNode) throws SlickException {
		super(x,y,width,height,maxhealth,elemNode);
	}
	
	private NPC(NPC base){
		super(base);
		this.tf = base.tf;
	}
	
	@Override
	public NPC clone() {
		return new NPC(this);
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
	public static NPC getNew(String name, float x, float y){
		if(name == null){
			return null;
		}
		NPC base = npcs.get(name.toLowerCase());
		NPC ret = new NPC(base);
		ret.setPosition(x, y);
		return ret;
	}
	
	/**
	 * Resets the entity storage such that (A)x.getNewNPC(x) = null;
	 */
	public static void clearLoaded(){
		npcs.clear();
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		AnimationContainer sprite = getCurrentAnimationContainer();
		renderSprite(sprite);
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
	public void update(GameContainer gc) {
		super.update(gc);
		/*if(tf != null){
			tf.setText(this.toString() + " says hello :)");
		}*/
	}
	
	@Override
	public void collide(MovingEntity e) {
	}
	
	@Override
	public void collide(StaticEntity<?> e) {
	}
	
	/**
	 * Creates a new npc from an XML node. This should not be used except when loading enemies into the npc template storage.
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
		
		NPC e = new NPC(width,height,health, elemNode);
		load(name, e);
	}
	
	@Override
	public int getLayer() {
		return NPC_DEFAULT_LAYER;
	}

	@Override
	public void collide(DestructibleEntity d) {
	}

	public void setTextField(TextField<?> tf) {
		this.tf  = tf;
	}

	public TextField<?> getTextField() {
		return tf;
	}
	
}
