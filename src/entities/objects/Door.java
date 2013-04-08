package entities.objects;

import java.awt.image.BufferedImage;
import java.io.IOException;

import map.Cell;
import map.tileproperties.BooleanTilePropertyValue;
import map.tileproperties.TileProperty;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import entities.MovingEntity;
import entities.StaticRectEntity;
import game.config.Config;

public class Door extends StaticRectEntity {
	
	private static final int DOOR_DEFAULT_LAYER = 100;
	
	private final Cell cell;
	private final Animation openSprite, closedSprite;
	private DoorTrigger trigger;
	
	public Door(Cell cell, DoorTrigger trigger, int x, int y){
		super(x,y,1f,1f);
		this.cell = cell;
		closeDoor();
		this.trigger = trigger;
		if(trigger != null){
			trigger.setDoor(this);
		}
		{
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
			closedSprite = new Animation(new Image[]{ new Image(t) }, 1);
		}
		{
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
			openSprite = new Animation(new Image[]{ new Image(t) }, 1);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		if(!cell.getTile((int) getCentreX(),(int) getCentreY()).lookup(TileProperty.BLOCKED).getBoolean()){
			openSprite.draw((getX()-1)*Config.getTileSize(), (getY()-1)*Config.getTileSize());
		}else{
			closedSprite.draw((getX()-1)*Config.getTileSize(), (getY()-1)*Config.getTileSize());
		}
	}

	@Override
	public void update(GameContainer gc) {
		if(trigger == null){
			closeDoor();
		}
	}
	
	private void openDoor(){
		cell.getTile((int) getCentreX(),(int) getCentreY()).put(TileProperty.BLOCKED, new BooleanTilePropertyValue(false));
	}
	
	private void closeDoor(){
		cell.getTile((int) getCentreX(),(int) getCentreY()).put(TileProperty.BLOCKED, new BooleanTilePropertyValue(true));
	}

	public void assignTrigger(DoorTrigger trigger) {
		this.trigger = trigger;
	}

	public void setTriggered() {
		openDoor();
	}

	@Override
	public void collide(MovingEntity e) {
		if(trigger == null){
			openDoor();
		}
	}

	@Override
	public int getLayer() {
		return DOOR_DEFAULT_LAYER;
	}
}
