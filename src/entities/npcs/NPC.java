package entities.npcs;

import java.util.HashMap;
import java.util.Map;

import map.Cell;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import utils.AnimationContainer;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import entities.DestructibleEntity;
import entities.MovingEntity;
import entities.NonPlayableEntity;
import entities.StaticEntity;
import entities.aistates.decisiontree.AIDecisionTree;
import entities.objects.TextField;

public class NPC extends NonPlayableEntity{
	
	private static final int NPC_DEFAULT_LAYER = -10;
	
	private final AnimationContainer left, right;
	private AnimationContainer sprite;

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
	private NPC(float width, float height, int maxhealth, AnimationContainer left, AnimationContainer right, String AIStr) {
		this(0,0,width,height,maxhealth,left,right,AIStr);
	}
	
	private NPC(float x, float y, float width, float height, int maxhealth, AnimationContainer left, AnimationContainer right, String AIStr) {
		super(x,y,width,height,maxhealth,AIStr);
		this.left = left;
		this.right = right;
		sprite = right;
	}
	
	private NPC(float x, float y, float width, float height, int maxHealth, AnimationContainer left, AnimationContainer right, AIDecisionTree aiDecisionTree) {
		super(x,y,width,height,maxHealth,aiDecisionTree);
		this.left = left;
		this.right = right;
		sprite = right;
	}
	
	private NPC(NPC base){
		super(base);
		this.left = base.left;
		this.right = base.right;
		this.sprite = base.sprite;
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
	public static NPC getNew(Cell currentCell, String name, float x, float y){
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void collide(StaticEntity<?> e) {
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
		
		//set up animation
		AnimationContainer leftAni,rightAni;
		{
			Node leftImagesNode = elemNode.getElementsByTagName("leftimages").item(0);
			Node rightImagesNode = elemNode.getElementsByTagName("rightimages").item(0);
			if(leftImagesNode == null){
				if(rightImagesNode == null){
					throw new ParseException("Must have at least either 'leftimages' or 'rightimages' tag defined.");
				}else{
					AnimationContainer cont = new AnimationContainer(rightImagesNode);
					rightAni = cont;
					leftAni  = cont.flippedCopy(true,false);
				}
			}else {
				if(rightImagesNode == null){
					AnimationContainer cont = new AnimationContainer(leftImagesNode);
					leftAni  = cont;
					rightAni = cont.flippedCopy(true, false);
				}else{
					leftAni  = new AnimationContainer(leftImagesNode);
					rightAni = new AnimationContainer(rightImagesNode);
				}
			}
		}
		
		//parse AI
		String AIText = DEFAULT_AI_STRING;
		{
			Node AINode = elemNode.getElementsByTagName("ai").item(0);
			if(AINode == null){
				AINode = elemNode.getElementsByTagName("AI").item(0);
			}
			if(AINode != null){
				AIText = AINode.getTextContent();
			}
		}

		NPC e = new NPC(width,height,health, leftAni, rightAni,AIText);
		load(name, e);
	}
	
	@Override
	public int getLayer() {
		return NPC_DEFAULT_LAYER;
	}

	@Override
	public void collide(DestructibleEntity d) {
		// TODO Auto-generated method stub
		
	}

	public void setTextField(TextField<?> tf) {
		this.tf  = tf;
	}

	public TextField<?> getTextField() {
		return tf;
	}
	
}
