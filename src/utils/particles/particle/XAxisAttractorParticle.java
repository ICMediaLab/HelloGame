package utils.particles.particle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import utils.Position;

public class XAxisAttractorParticle extends FixedTexColRadParticle {
	
	protected final float targetX;
	private final boolean gt;
	
	public XAxisAttractorParticle(Image texture, Position position, Position velocity,
			Color color, float radius, float targetx, Position inertia) {
		super(texture, position, velocity, color, radius, inertia);
		this.targetX = targetx;
		gt = position.getX() > targetx;
	}
	
	@Override
	public boolean isAlive() {
		return getRadius() > 0.001f && gt ? xy.getX() > targetX : xy.getX() < targetX;
	}
}
