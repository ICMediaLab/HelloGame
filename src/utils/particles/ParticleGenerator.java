package utils.particles;

import utils.Position;

public interface ParticleGenerator<T extends Particle> {

	T newParticle(Position position, Position velocity);

}
