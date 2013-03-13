package entities.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;

import entities.Entity;
import entities.NonPlayableEntity;
import game.config.Config;

public class Door extends NonPlayableEntity {
	
	private final Animation s;
	
	public Door(int x, int y){
		super((float) x, (float) y);
		
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
		s = new Animation(new Image[]{ new Image(t) }, 1);
	}

	@Override
	public void render() {
		s.draw(getX(), getY());
	}

	@Override
	public Entity clone() {
		return this;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg,
			org.newdawn.slick.Graphics g) {
		render();
		
	}

}
