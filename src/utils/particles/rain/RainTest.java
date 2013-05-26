package utils.particles.rain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import map.Cell;
import map.MapLoader;
import map.Tile;
import map.tileproperties.TileProperty;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utils.Position;
import utils.interval.colour.ColourRange;
import utils.interval.colour.DiscreteColourRange;
import utils.interval.one.FixedValue;
import utils.interval.one.Interval;
import utils.interval.two.Interval2D;
import utils.interval.two.Range2D;
import utils.particles.ParticleEmitter;
import utils.particles.ParticleGenerator;
import utils.particles.particle.InfiniteAttractorParticle;
import utils.particles.particle.Particle;
import entities.objects.watereffects.WaterEffectParticle;
import game.config.Config;

public class RainTest extends ParticleEmitter<RainParticle> {
	
	private static final int PARTICLES_PER_FRAME = 20;
	
	private static final Map<Cell,RainTest> rains = new HashMap<Cell,RainTest>();
	
	private final Collection<WaterEffectParticle> bubblez = new LinkedList<WaterEffectParticle>();
	
	public static RainTest getRain(Cell c, int layer){
		RainTest existing = rains.get(c);
		if(existing != null){
			return existing;
		}
		RainTest r = new RainTest(new RainParticleGenerator(c), new Interval2D(new Interval(1f,c.getWidth()-1f), new FixedValue(0.5f)), new Interval2D(new FixedValue(0f),new Interval(0.12f,0.20f)), layer);
		long start = -System.currentTimeMillis();
		for(int i = 0;i < 140;i++){
			r.update(null);
		}
		start += System.currentTimeMillis();
		System.out.println(start);
		rains.put(c, r);
		return r;
	}
	
	private RainTest(ParticleGenerator<? extends RainParticle> pGen,
			Range2D positionRange, Range2D velocityRange, int layer) {
		super(pGen, positionRange, velocityRange, layer);
	}
	
	@Override
	public boolean isEmitting() {
		return true;
	}
	
	@Override
	protected void generateParticles() {
		for(int i=PARTICLES_PER_FRAME;i>=0;i--){
			addParticle(getNewParticle());
		}
		addAllParticles(bubblez);
		bubblez.clear();
	}
	
	@Override
	public void update(GameContainer gc) {
		generateParticles();
		lifespan  += Config.DELTA;
		Iterator<Particle> it = particles.iterator();
		synchronized (it) {
			while(it.hasNext()){
				Particle p = it.next();
				p.update(gc);
				if(!p.isAlive()){
					it.remove();
				}else if(p instanceof RainParticle){
					RainParticle rp = (RainParticle) p;
					if(rp.isInWater() && rp.getRadius() < 0.002f){
						bubblez.add(rp.getBubbleParticle());
					}
				}
			}
		}
	}
}

class RainParticleGenerator implements ParticleGenerator<RainParticle> {
	private static final ColourRange COLOUR_RANGE = new DiscreteColourRange(0.1f, 0.2f, 0.1f, 0.2f, 0.4f, 0.8f,100);
	private static final Interval SIZE_RANGE = new Interval(0.005f,0.01f);
	private static final Image TEXTURE;
	
	private static final float DRAG = 0.93f;
	private static final Position ATTRACTOR = InfiniteAttractorParticle.getGravityAttractor(DRAG);
	private static final Position INERTIA = InfiniteAttractorParticle.getInertia(DRAG);
	
	static {
		Image texture = null;
		try {
			texture = new Image("data/images/circle.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		TEXTURE = texture;
	}
	
	private final int[] fcs; //fallCheckStart
	private final Cell c;
	
	public RainParticleGenerator(Cell cell) {
		c = cell;
		fcs = new int[cell.getWidth()];
		for(int col = 0;col < cell.getWidth(); col++){
			Tile t;
			while(++fcs[col]<cell.getHeight() && !(t = cell.getTile(col, fcs[col])).lookup(TileProperty.BLOCKED) && !t.lookup(TileProperty.WATER));
		}
	}
	
	@Override
	public RainParticle newParticle(Position position, Position velocity) {
		return new RainParticle(c,TEXTURE, position, velocity, COLOUR_RANGE.random(), SIZE_RANGE.random(), ATTRACTOR, INERTIA, fcs[(int) position.getX()]);
	}
}
