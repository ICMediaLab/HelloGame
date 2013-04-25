package entities.objects.watereffects;

import utils.Position;
import utils.particles.ParticleGenerator;

public class WaterEffectParticleGenerator implements ParticleGenerator<WaterEffectParticle> {
	
	private final float targety;

	public WaterEffectParticleGenerator(float targety) {
		this.targety = targety;
	}
	
	@Override
	public WaterEffectParticle newParticle(Position position,
			Position velocity) {
		return new WaterEffectParticle(position, velocity,targety);
	}
}
