package utils.particles;

import java.util.Collection;
import java.util.Random;

import org.newdawn.slick.Image;

import utils.Position;
import utils.interval.one.ColourRange;
import utils.interval.one.Interval;

public class NonCollidingParticleGenerator implements ParticleGenerator<NonCollidingParticle>{
	
	private static final Random r = new Random();
	
	private final Collection<Image> textures;
	private final ColourRange cRange;
	private final Interval ttlRange, sizeRange;

	public NonCollidingParticleGenerator(Collection<Image> textures, ColourRange cRange, Interval sizeRange, Interval ttlRange) {
		this.textures = textures;
		this.cRange = cRange;
		this.sizeRange = sizeRange;
		this.ttlRange = ttlRange;
	}
	
	@Override
	public NonCollidingParticle newParticle(Position position, Position velocity) {
		Image tex = textures.toArray(new Image[textures.size()])[r.nextInt(textures.size())];
		return new NonCollidingParticle(tex, position, velocity, cRange.random(), sizeRange.random(), ttlRange.random().intValue());
	}

}
