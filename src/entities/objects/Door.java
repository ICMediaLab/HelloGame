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

public class Door extends NonPlayableEntity {
	
	private final Animation openSprite, closedSprite;
	private boolean open = false;
	private DoorTrigger trigger;
	
	public Door(DoorTrigger trigger, int x, int y){
		super(x,y,1f,1f);
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
		System.out.println("Hai");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg,
		org.newdawn.slick.Graphics g) {
		if(open){
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
			boolean any = false;
			for(Entity e : MapLoader.getCurrentCell().getEntities()){
				if(this != e && intersects(e)){
					any = true;
					break;
				}
			}
			if(any){
				open = true;
			}else{
				open = false;
			}
		}
	}

	public void assignTrigger(DoorTrigger trigger) {
		this.trigger = trigger;
	}

	public void setTriggered() {
		open = true;
	}
}
