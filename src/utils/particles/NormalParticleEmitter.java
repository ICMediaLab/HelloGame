package utils.particles;

import utils.interval.two.Range2D;
import utils.particles.particle.InfiniteAttractorParticle;

public class NormalParticleEmitter<P extends InfiniteAttractorParticle> extends ParticleEmitter<P> {

	private int ttl;

	public NormalParticleEmitter(ParticleGenerator<? extends P> pGen, Range2D sourceRange, Range2D emitVelocityRange, int layer, int ttl) {
		super(pGen,sourceRange, emitVelocityRange, layer);
		this.ttl = ttl;
	}
	
	@Override
	public boolean isEmitting() {
		return numParticles() > 0 || getAliveTime() == 0;
	}

	@Override
	protected void generateParticles() {
		if(ttl-- > 0){
			addParticle(getNewParticle());
			addParticle(getNewParticle());
		}
	}
	
}
