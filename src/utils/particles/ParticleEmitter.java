package utils.particles;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import utils.LayerRenderable;
import utils.Position;
import utils.Updatable;
import utils.interval.two.Range2D;
import entities.Entity;
import game.config.Config;

public class ParticleEmitter implements Updatable, LayerRenderable {
    private static final Random random = new Random();
    
    private final int layer;
    private final Position emitterLocation;
    private final Range2D emitterRange;
    private final List<Particle> particles = new LinkedList<Particle>();
    private final List<Image> textures;
    
    private final int particleLifespan;
    private int lifespan;
    private boolean isEmitting;
    
    public ParticleEmitter(List<Image> textures, Entity trigger, Position src, Range2D emitRange, int emitterLifespan, int maxParticleLifespan){
    	this.layer = trigger.getLayer()-1;
        emitterLocation = src;
        this.textures = textures;
        isEmitting = true;
        emitterRange = emitRange;
        lifespan = emitterLifespan;
        particleLifespan = maxParticleLifespan;
    }
    
    private Particle GenerateNewParticle(){
        Image texture = textures.get(random.nextInt(textures.size()));
        Position position = new Position(emitterLocation.getX(), emitterLocation.getY());
        Position velocity = emitterRange.random();
        
        float angle = 0;
        float angularVelocity = 0.0f;
        Color color = new Color(random.nextFloat() * 0.2f + 0.2f, random.nextFloat() * 0.3f + 0.1f, random.nextFloat() * 0.3f + 0.1f);
        float size = 0.5f * (float)random.nextDouble();
        int ttl = random.nextInt(Config.getTileSize()*particleLifespan);
        
        return new Particle(texture, position, velocity, angle, angularVelocity, color, size, ttl);
    }
    
    public boolean isEmitting() {
        return isEmitting;
    }
    
	public void render(GameContainer gc, Graphics g) {
		for (int index = 0; index < particles.size(); index++){
            particles.get(index).render();
        }
	}
	
	public void update(GameContainer gc) {
		if (lifespan > 0) {
            particles.add(GenerateNewParticle());
			lifespan--;
		}else {
            isEmitting = particles.size() > 0;
        }
		
        for (int index = 0; index < particles.size();){
        	Particle p = particles.get(index);
            p.update();
            if (p.ttl <= 0){
                particles.remove(index);
            }else{
            	index++;
            }
        }
	}
	
	public int getLayer() {
		return layer;
	}
}
