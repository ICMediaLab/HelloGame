package utils.particles;

import map.Cell;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utils.Position;
import utils.interval.one.ColourRange;
import utils.interval.one.FixedValue;
import utils.interval.one.Interval;
import utils.interval.two.Interval2D;
import utils.interval.two.Range2D;

public class RainTest extends ParticleEmitter<BlockedCollidingParticle> {
	
	private Cell cell;
	
	public static RainTest getRain(Cell c, int layer){
		return new RainTest(c,new RainParticleGenerator(c), new Interval2D(new Interval(1f,c.getWidth()-1f), new FixedValue(1f)), new Interval2D(new FixedValue(0f),new Interval(0.05f,0.06f)), layer);
	}

	private RainTest(Cell c, ParticleGenerator<? extends BlockedCollidingParticle> pGen,
			Range2D positionRange, Range2D velocityRange, int layer) {
		super(pGen, positionRange, velocityRange, layer);
		this.cell = c;
	}

	@Override
	public boolean isEmitting() {
		return true;
	}

	@Override
	protected void generateParticles() {
		for(int i=(int) cell.getWidth()/2;i>=0;i--){
			addParticle(getNewParticle());
		}
	}
}

class RainParticleGenerator implements ParticleGenerator<BlockedCollidingParticle> {
	private static final ColourRange cRange = new ColourRange(0.1f, 0.2f, 0.1f, 0.2f, 0.2f, 0.5f);
	private static final Interval sizeRange = new Interval(0.005f,0.01f);
	private static final Image tex;
	
	static {
		Image texture = null;
		try {
			texture = new Image("data/images/circle.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		tex = texture;
	}
	
	private final Cell c;
	
	public RainParticleGenerator(Cell cell) {
		c = cell;
	}
	
	@Override
	public BlockedCollidingParticle newParticle(Position position, Position velocity) {
		return new BlockedCollidingParticle(c,tex, position, velocity, cRange.random(), sizeRange.random());
	}
}
