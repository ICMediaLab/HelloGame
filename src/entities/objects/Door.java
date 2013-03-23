package entities.objects;

import entities.Entity;
import entities.StaticEntity;
import game.config.Config;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import map.Cell;
import map.tileproperties.BooleanTilePropertyValue;
import map.tileproperties.TileProperty;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;

public class Door extends StaticEntity {
	
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
			Graphics g = i.createGraphics();
			g.setColor(Color.BLACK);
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
			Graphics g = i.createGraphics();
			g.setColor(Color.GREEN);
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
	public void render(GameContainer gc, StateBasedGame sbg,
		org.newdawn.slick.Graphics g) {
		if(!cell.getTile((int) getX(),(int) getY()).lookupProperty(TileProperty.BLOCKED).getBoolean()){
			openSprite.draw((getX()-1)*Config.getTileSize(), (getY()-1)*Config.getTileSize());
		}else{
			closedSprite.draw((getX()-1)*Config.getTileSize(), (getY()-1)*Config.getTileSize());
		}
	}

	@Override
	public Entity clone() {
		return this;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if(trigger == null){
			closeDoor();
		}
	}
	
	private void openDoor(){
		cell.getTile((int) getX(),(int) getY()).addProperty(TileProperty.BLOCKED, new BooleanTilePropertyValue(false));
	}
	
	private void closeDoor(){
		cell.getTile((int) getX(),(int) getY()).addProperty(TileProperty.BLOCKED, new BooleanTilePropertyValue(true));
	}

	public void assignTrigger(DoorTrigger trigger) {
		this.trigger = trigger;
	}

	public void setTriggered() {
		openDoor();
	}

	@Override
	public void collide(Entity e) {
		if(trigger == null){
			openDoor();
		}
	}

	@Override
	public int getLayer() {
		return DOOR_DEFAULT_LAYER;
	}
}
