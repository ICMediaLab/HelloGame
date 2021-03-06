package entities.objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import utils.triggers.TriggerEffect;
import utils.triggers.TriggerSource;
import entities.MovingEntity;
import entities.StaticRectEntity;
import entities.players.Player;
import game.config.Config;

public class DoorTrigger extends StaticRectEntity implements TriggerSource {
	
	private static final int DOOR_TRIGGER_DEFAULT_LAYER = -200;
	
	private transient final Animation s;
	private float filter = 1f;
	
	private final Set<TriggerEffect> triggerEffects = new HashSet<TriggerEffect>();
	
	public DoorTrigger(int x, int y){
		super(x,y,1,1);
		s = getTriggerAnimation();
	}
	
	private Animation getTriggerAnimation(){
		BufferedImage i = new BufferedImage(Config.getTileSize(), Config.getTileSize(), BufferedImage.TYPE_INT_ARGB);
		java.awt.Graphics g = i.createGraphics();
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(0, 0, Config.getTileSize(), Config.getTileSize());
		Texture t = null;
		try {
			t = BufferedImageUtil.getTexture("doorimage", i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Animation(new Image[]{ new Image(t) }, 1);
	}
	
	public void update(GameContainer gc) {
		if(filter < 1f){
			filter = Math.max(0f, filter - 0.02f);
		}
	}
	
	protected void trigger() {
		filter -= 0.02f;
		for(TriggerEffect t : triggerEffects){
			t.triggeredSource(this);
		}
	}
	
	@Override
	public void addTriggerEffect(TriggerEffect t) {
		triggerEffects.add(t);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		float filsq = filter*filter;
		filsq *= filsq;
		s.draw((getX()-1)*Config.getTileSize(), (getY()-1)*Config.getTileSize(), new Color(filsq, 1f - filsq, 0f, filter));
	}
	
	@Override
	public void collide(MovingEntity e) {
		if(e instanceof Player){
			trigger();
		}
	}

	@Override
	public int getLayer() {
		return DOOR_TRIGGER_DEFAULT_LAYER;
	}

	@Override
	public boolean isSolid() {
		return true;
	}
	
}
