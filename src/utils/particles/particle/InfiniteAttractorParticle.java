package utils.particles.particle;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import utils.Position;

public abstract class InfiniteAttractorParticle extends FixedTexColRadParticle {
	
	private static final Position NORMAL_GRAVITY = new Position(0f,0.01f);
	
	private final Position attractor;
	
	public InfiniteAttractorParticle(Image texture, Position position, Position velocity, Color color, float radius) {
		this(texture,position,velocity,color,radius,NORMAL_GRAVITY,NORMAL_FRICTION);
	}
	
	public InfiniteAttractorParticle(Image texture, Position position, Position velocity, Color color, float radius, float drag) {
		this(texture,position,velocity,color,radius,getGravityAttractor(drag),getInertia(drag));
	}
	
	public InfiniteAttractorParticle(Image texture, Position position, Position velocity, Color color, float radius, Position attractor, Position inertia) {
		super(texture, position,velocity,color,radius,inertia);
		this.attractor = attractor;
	}
	
	public static Position getGravityAttractor(float drag){
		return NORMAL_GRAVITY.scaledCopy(drag);
	}
	
	public void update(GameContainer gc) {
		super.update(gc);
		dxdy.translate(attractor);
	}
}
