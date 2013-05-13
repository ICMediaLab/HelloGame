package entities.objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import utils.triggers.Trigger;
import utils.triggers.Triggerable;
import entities.MovingEntity;
import entities.StaticRectEntity;
import entities.players.Player;
import game.config.Config;

public class DoorTrigger extends StaticRectEntity implements Trigger {
	
	private static final int DOOR_TRIGGER_DEFAULT_LAYER = -200;
	
	private final Set<Triggerable> triggerables = new HashSet<Triggerable>();
	
	private transient final Animation s;
	
	public DoorTrigger(int x, int y){
		super(x,y,1,1);
		s = getTriggerAnimation();
	}
	
	/**
	 * Serialisation loading method for {@link DoorTrigger}
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		in.defaultReadObject();
		Field s = getClass().getDeclaredField("s");
		s.setAccessible(true);
		s.set(this, getTriggerAnimation());
	}
	
	private Animation getTriggerAnimation(){
		BufferedImage i = new BufferedImage(Config.getTileSize(), Config.getTileSize(), BufferedImage.TYPE_INT_ARGB);
		java.awt.Graphics g = i.createGraphics();
		g.setColor(java.awt.Color.RED);
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
	public void update(GameContainer gc) {
		untriggered();
	}
	
	@Override
	public void addTriggerable(Triggerable t) {
		triggerables.add(t);
	}
	
	@Override
	public void removeTriggerable(Triggerable t) {
		triggerables.remove(t);
	}
	
	@Override
	public void clearTriggerables() {
		triggerables.clear();
	}
	
	@Override
	public void triggered() {
		for(Triggerable t : triggerables){
			t.triggered(this);
		}
	}
	
	@Override
	public void untriggered() {
		for(Triggerable t : triggerables){
			t.untriggered(this);
		}
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		s.draw((getX()-1)*Config.getTileSize(), (getY()-1)*Config.getTileSize());
	}
	
	@Override
	public void collide(MovingEntity e) {
		if(e instanceof Player){
			triggered();
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
