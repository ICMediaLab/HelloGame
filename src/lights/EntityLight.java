package lights;

import org.newdawn.slick.Color;

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
	
}
