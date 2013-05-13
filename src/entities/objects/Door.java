package entities.objects;

import entities.MovingEntity;
import game.config.Config;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import map.Cell;
import map.tileproperties.TileProperty;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import utils.triggers.Trigger;
import utils.triggers.Triggerable;

public class Door extends StaticBlockingEntity implements Triggerable {
	
	private static final int DOOR_DEFAULT_LAYER = 100;
	
	private transient final Animation openSprite, closedSprite;
	
	private final Set<Trigger> triggered = new HashSet<Trigger>(), untriggered = new HashSet<Trigger>();
	
	public Door(Cell cell, int x, int y){
		super(cell,x,y,1,1);
		closeDoor();
		closedSprite = getClosedAnimation();
		openSprite = getOpenAnimation();
	}
	
	/**
	 * Serialisation loading method for {@link Door}
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		in.defaultReadObject();
		Field open = getClass().getDeclaredField("openSprite");
		Field closed = getClass().getDeclaredField("closedSprite");
		open.setAccessible(true);
		closed.setAccessible(true);
		open.set(this, getOpenAnimation());
		closed.set(this, getClosedAnimation());
	}
	
	private Animation getClosedAnimation(){
		BufferedImage i = new BufferedImage(Config.getTileSize(), Config.getTileSize(), BufferedImage.TYPE_INT_ARGB);
		java.awt.Graphics g = i.createGraphics();
		g.setColor(java.awt.Color.BLACK);
		g.fillRect(0, 0, Config.getTileSize(), Config.getTileSize());
		Texture t = null;
		try {
			t = BufferedImageUtil.getTexture("doorimage", i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Animation(new Image[]{ new Image(t) }, 1);
	}
	
	private Animation getOpenAnimation(){
		BufferedImage i = new BufferedImage(Config.getTileSize(), Config.getTileSize(), BufferedImage.TYPE_INT_ARGB);
		java.awt.Graphics g = i.createGraphics();
		g.setColor(java.awt.Color.GREEN);
		g.fillRect(0, 0, Config.getTileSize(), Config.getTileSize());
		Texture t = null;
		try {
			t = BufferedImageUtil.getTexture("doorimage", i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Animation(new Image[]{ new Image(t) }, 1);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		if(!parent.getTile((int) getCentreX(),(int) getCentreY()).lookup(TileProperty.BLOCKED)){
			openSprite.draw((getX()-1)*Config.getTileSize(), (getY()-1)*Config.getTileSize());
		}else{
			closedSprite.draw((getX()-1)*Config.getTileSize(), (getY()-1)*Config.getTileSize());
		}
	}
	
	@Override
	public void update(GameContainer gc) {
		if(untriggered.isEmpty()){
			openDoor();
		}
	}
	
	private void openDoor(){
		setBlocked(false);
	}
	
	private void closeDoor(){
		setBlocked(true);
	}
	
	@Override
	public void collide(MovingEntity e) {
		//nothing to do here
	}
	
	@Override
	public int getLayer() {
		return DOOR_DEFAULT_LAYER;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public void addTrigger(Trigger t) {
		untriggered.add(t);
	}
	
	@Override
	public void removeTrigger(Trigger t) {
		triggered.remove(t);
		untriggered.remove(t);
	}
	
	@Override
	public void clearTriggers() {
		triggered.clear();
		untriggered.clear();
	}
	
	@Override
	public void triggered(Trigger t) {
		if(untriggered.remove(t)){
			triggered.add(t);
		}
	}
	
	@Override
	public void untriggered(Trigger t) {
		//do nothing as door only requires one triggering of each trigger to open
		/*if(triggered.remove(t)){
			untriggered.add(t);
		}*/
	}
}
