package utils.particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import utils.Position;
import utils.particles.particle.InfiniteAttractorParticle;

public class NonCollidingParticle extends InfiniteAttractorParticle {
	
	private int ttl;
	private final float ttlSizeStep; 
	
	public NonCollidingParticle(Image texture, Position position, Position velocity, Color color, float size, int ttl) {
		super(texture,position,velocity,color,size);
		this.ttl = ttl;
		this.ttlSizeStep = getRadius()/ttl;
	}
	
	@Override
	public void update(GameContainer gc) {
		setRadius(getRadius() - ttlSizeStep);
		--ttl;
		super.update(gc);
	}

	@Override
	public boolean isAlive() {
		return ttl > 0;
	}

}
