package utils.particles.rain;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import map.Cell;
import map.Tile;
import map.tileproperties.TileProperty;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utils.Position;
import utils.interval.ColourRange;
import utils.interval.one.FixedValue;
import utils.interval.one.Interval;
import utils.interval.two.Interval2D;
import utils.interval.two.Range2D;
import utils.particles.ParticleEmitter;
import utils.particles.ParticleGenerator;
import utils.particles.particle.InfiniteAttractorParticle;
import utils.particles.particle.Particle;
import entities.objects.watereffects.WaterEffectParticle;

public class RainTest extends ParticleEmitter<RainParticle> implements Runnable {
	
	private static final Map<Cell,RainTest> rains = new HashMap<Cell,RainTest>();
	
	private final Cell cell;
	private GameContainer gc = null;
	
	private final Collection<WaterEffectParticle> bubblez = new HashSet<WaterEffectParticle>();
	
	public static RainTest getRain(Cell c, int layer){
		RainTest existing = rains.get(c);
		if(existing != null){
			return existing;
		}
		RainTest r = new RainTest(c,new RainParticleGenerator(c), new Interval2D(new Interval(1f,c.getWidth()-1f), new FixedValue(0.5f)), new Interval2D(new FixedValue(0f),new Interval(0.12f,0.20f)), layer);
		long start = -System.currentTimeMillis();
		for(int i = 0;i < 140;i++){
			r.updateThreadless();
		}
		start += System.currentTimeMillis();
		System.out.println(start);
		rains.put(c, r);
		return r;
	}
	
	private RainTest(Cell c, ParticleGenerator<? extends RainParticle> pGen,
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
		for(int i=(int) cell.getWidth()/3;i>=0;i--){
			addParticle(getNewParticle());
			addAllParticles(bubblez);
			bubblez.clear();
		}
	}
	
	public void updateThreadless() {
		super.update(gc);
		for(Particle p : getParticles()){
			if(p instanceof RainParticle){
				RainParticle rp = (RainParticle) p;
				if(rp.isInWater() && rp.getRadius() < 0.002f){
					bubblez.add(rp.getBubbleParticle());
				}
			}
		}
	}
	
	private final Thread updateThread = new Thread(this,"RainThread");
	private boolean running = true;
	
	@Override
	public void update(GameContainer gc) {
		this.gc = gc;
		if(!updateThread.isAlive()){
			updateThread.start();
		}else{
			synchronized (updateThread) {
				updateThread.notify();
			}
		}
	}
	
	@Override
	public void run() {
		while(running){
			updateThreadless();
			synchronized (updateThread) {
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		running = false;
		synchronized (updateThread) {
			updateThread.notify();
		}
	}
}

class RainParticleGenerator implements ParticleGenerator<RainParticle> {
	private static final ColourRange COLOUR_RANGE = new ColourRange(0.1f, 0.2f, 0.1f, 0.2f, 0.4f, 0.8f);
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