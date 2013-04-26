package utils.particles;

import utils.particles.particle.Particle;

public class SingleParticle<T extends Particle> extends ParticleEmitter<T> {

	public SingleParticle(T particle, int layer) {
		super(null, null, null, layer);
		addParticle(particle);
	}

	@Override
	public boolean isEmitting() {
		return false;
	}

	@Override
	protected void generateParticles() {
		//nothing to do here...
	}

}
