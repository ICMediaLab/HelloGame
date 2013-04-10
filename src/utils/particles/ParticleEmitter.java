package utils.particles;

import game.config.Config;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import utils.LayerRenderable;
import utils.Updatable;
import utils.interval.two.Range2D;

public abstract class ParticleEmitter<P extends Particle> implements Updatable, LayerRenderable {
	private final int layer;
	private final Range2D positionRange, velocityRange;
	private final Collection<P> particles = Collections.newSetFromMap(new ConcurrentHashMap<P, Boolean>());
			
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
		for (Particle p : particles){
			p.render();
		}
	}
	
	public void update(GameContainer gc) {
		lifespan  += Config.DELTA;
		
		generateParticles();
		
		Iterator<P> it = particles.iterator();
		synchronized (it) {
			while(it.hasNext()){
				Particle p = it.next();
				p.update();
				if(!p.isAlive()){
					it.remove();
				}
			}
		}
	}
	
	protected abstract void generateParticles();
	
	public int getLayer() {
		return layer;
	}
}
