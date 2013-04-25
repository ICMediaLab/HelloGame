package utils.particles.particle;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import utils.Position;

public class PositionAttractorParticle extends FixedTexColRadParticle {
	
	private final float limit;
	private final Position attractor;
	
	public PositionAttractorParticle(Image texture, Position position, Position velocity,
			Color color, float radius, Position attractor, Position inertia, float limit) {
		super(texture, position, velocity, color, radius, inertia);
		this.limit = limit*limit;
		this.attractor = attractor;
	}
	
	@Override
	public boolean isAlive() {
		return xy.distanceTo(attractor).getMagnitudeSquared() > limit;
	}
	
	@Override
	public void update(GameContainer gc) {
		dxdy.translate(xy.distanceTo(attractor).scaledCopy(0.01f));
		super.update(gc);
	}
}
