package utils.particles;

import game.config.Config;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import utils.LayerRenderable;
import utils.Updatable;
import utils.interval.two.Range2D;

public abstract class ParticleEmitter<P extends Particle> implements Updatable, LayerRenderable {
	private final int layer;
	private final Range2D positionRange, velocityRange;
	private final List<P> particles = new LinkedList<P>();
	
	private int lifespan = 0;
	private ParticleGenerator<? extends P> particleGenerator;
	
	public ParticleEmitter(ParticleGenerator<? extends P> pGen, Range2D positionRange, Range2D velocityRange, int layer){
		this.layer = layer;
		this.positionRange = positionRange;
		this.velocityRange = velocityRange;
		this.particleGenerator = pGen;
	}

	protected P getNewParticle(){
		return particleGenerator.newParticle(positionRange.random(),velocityRange.random());
	}
	
	public void addParticle(P p){
		particles.add(p);
	}
	
	public void clearParticles(){
		particles.clear();
	}
	
	public void removeParticle(P p){
		particles.remove(p);
	}
	
	public int numParticles(){
		return particles.size();
	}
	
	public abstract boolean isEmitting();
	
	protected int getAliveTime(){
		return lifespan;
	}
	
	public void render(GameContainer gc, Graphics g) {
		for (int index = 0; index < particles.size(); index++){
			particles.get(index).render();
		}
	}
	
	public void update(GameContainer gc) {
		lifespan  += Config.DELTA;
		
		generateParticles();
		
		Iterator<P> it = particles.iterator();
		while(it.hasNext()){
			Particle p = it.next();
			p.update();
			if(!p.isAlive()){
				it.remove();
			}
		}
	}
	
	protected abstract void generateParticles();
	
	public int getLayer() {
		return layer;
	}
}
