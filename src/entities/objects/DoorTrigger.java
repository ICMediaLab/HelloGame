package entities.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;

import utils.MapLoader;

import entities.Entity;
import entities.NonPlayableEntity;
import game.config.Config;

public class DoorTrigger extends NonPlayableEntity {
	
	private final Animation s;
	private Door trigger;
	
	public DoorTrigger(Door trigger, int x, int y){
		super(x,y,1,1);
		this.trigger = trigger;
		if(trigger != null){
			trigger.assignTrigger(this);
		}
		{
			BufferedImage i = new BufferedImage(Config.getTileSize(), Config.getTileSize(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = i.createGraphics();
			g.setColor(Color.RED);
			g.fillRect(0, 0, Config.getTileSize(), Config.getTileSize());
			Texture t = null;
			try {
				t = BufferedImageUtil.getTexture("doorimage", i);
			} catch (IOException e) {
				e.printStackTrace();
			}
			s = new Animation(new Image[]{ new Image(t) }, 1);
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
	}
	
	@Override
	public Entity clone() {
		return this;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg,
			org.newdawn.slick.Graphics g) {
		s.draw((getX()-1)*Config.getTileSize(), (getY()-1)*Config.getTileSize());
	}

	public void setDoor(Door trigger) {
		this.trigger = trigger;
	}

	@Override
	public void collide(Entity e) {
		if(e == MapLoader.getCurrentCell().getPlayer()){
			trigger.setTriggered();
		}
	}
	
}
