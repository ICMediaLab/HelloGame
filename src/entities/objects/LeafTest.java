package entities.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;

import entities.Entity;
import entities.NonPlayableEntity;
import game.config.Config;

public class LeafTest extends NonPlayableEntity {
	
	private final Animation stopped, moving;
	private Animation sprite;
	
	public LeafTest(int x, int y){
		super(x,y,1f,1f);

		//initialise graphics
		Image staticRaw = null;
		Image movingRaw = null;
		SpriteSheet staticSheet = null;
		SpriteSheet movingSheet = null;
		
		try {
			staticRaw = new Image("data/images/leafTest01.png");
			movingRaw = new Image("data/images/leafTest01.png");
			staticSheet = new SpriteSheet(staticRaw, 131, 88);
			movingSheet = new SpriteSheet(movingRaw, 131, 88);
			
		} catch (SlickException e) {
			//do shit all
		}

		moving = new Animation(movingSheet, 150);
		//moving.setAutoUpdate(false);
		stopped = new Animation(staticSheet, 0, 0, 0, 0, true, 1000, false);
		//sprite = stopped;
		moving.stop();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, org.newdawn.slick.Graphics g) {
		moving.draw(((getX()-1)*Config.getTileSize()-5), ((getY()-1)*Config.getTileSize())-54);
	}

	@Override
	public Entity clone() {
		return this;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if (!moving.isStopped()){
			if (moving.getFrame() == 0) {
				moving.stop();
			}
		}
	}

	@Override
	public void collide(Entity e) {
		moving.start();
	}
}