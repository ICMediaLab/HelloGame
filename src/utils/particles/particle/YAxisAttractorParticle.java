package utils.particles.particle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import utils.Position;

public class YAxisAttractorParticle extends FixedTexColRadParticle {
	
	protected final float targetY;
	private final boolean gt;
	
	public YAxisAttractorParticle(Image texture, Position position, Position velocity,
			Color color, float radius, float targety, Position inertia) {
		super(texture, position, velocity, color, radius, inertia);
		this.targetY = targety;
		gt = position.getY() > targety;
	}
	
	@Override
	public boolean isAlive() {
		return getRadius() > 0.001f && gt ? xy.getY() > targetY : xy.getY() < targetY;
	}
}

