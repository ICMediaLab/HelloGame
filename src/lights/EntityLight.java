package lights;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import entities.Entity;
import game.config.Config;

public class EntityLight extends AbstractPointLight {
	private final Entity e;
	
	public EntityLight(Entity e) {
		super();
		this.e = e;
	}
	
	public EntityLight(Entity e, float scale) {
		super(scale);
		this.e = e;
	}
	
	public EntityLight(Entity e, float scale, Color tint) {
		super(scale,tint);
		this.e = e;
	}
	
	@Override
	public float getX() {
		return (e.getCentreX() - 1)*Config.getTileSize();
	}

	@Override
	public float getY() {
		return (e.getCentreY() - 1)*Config.getTileSize();
	}
	
	public Entity getEntity(){
		return e;
	}
	
	@Override
	public int hashCode() {
		return e.hashCode();
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		super.render(gc, g);
	}
	
	public boolean equals(Object obj) {
		return obj instanceof EntityLight && ((EntityLight) obj).e == e;
	}
}
