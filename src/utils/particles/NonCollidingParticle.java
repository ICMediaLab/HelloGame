package utils.particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import utils.Position;

public class NonCollidingParticle extends Particle {
	
	private int ttl;
	private final float ttlSizeStep; 
	
	public NonCollidingParticle(Image texture, Position position, Position velocity, Color color, float size, int ttl) {
		super(texture,position,velocity,color,size);
		this.ttl = ttl;
		this.ttlSizeStep = getRadius()/ttl;
	}
	
	@Override
	public void update() {
		setRadius(getRadius() - ttlSizeStep);
		--ttl;
		super.update();
	}

	@Override
	public boolean isAlive() {
		return ttl > 0;
	}

}
