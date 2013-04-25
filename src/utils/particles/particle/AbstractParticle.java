package utils.particles.particle;

import game.config.Config;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import utils.Position;

public abstract class AbstractParticle implements Particle {
	
	protected static final Position NORMAL_FRICTION = new Position(0.99f, 0.99f);
	
	public static Position getInertia(float drag) {
		return NORMAL_FRICTION.scaledCopy(drag);
	}
	
	protected final Position xy,dxdy;
	
	public AbstractParticle(Position xy) {
		this(xy,new Position(0,0));
	}
	
	public AbstractParticle(Position xy, Position dxdy) {
		this.xy = xy;
		this.dxdy = dxdy;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		getTexture().draw((getCenterX() - 1)*Config.getTileSize(), (getCenterY() - 1)*Config.getTileSize(), 
				getRadius()*Config.getTileSize(), getColour());
	}
	
	protected abstract float getRadius();
	protected abstract Color getColour();
	protected abstract Image getTexture();
	protected abstract Position getInertia();
	
	@Override
	public void update(GameContainer gc) {
		dxdy.scale(getInertia());
		xy.translate(dxdy);
	}
	
	@Override
	public Position getPosition() {
		return xy.clone();
	}

	@Override
	public float getCenterY() {
		return xy.getY();
	}

	@Override
	public float getCenterX() {
		return xy.getX();
	}
	
	public float getdX(){
		return dxdy.getX();
	}
	
	public float getdY(){
		return dxdy.getY();
	}
	
}
