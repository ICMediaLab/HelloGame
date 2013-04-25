package utils.particles;

import utils.Position;
import utils.particles.particle.Particle;

public interface ParticleGenerator<T extends Particle> {
	
	T newParticle(Position position, Position velocity);
}
