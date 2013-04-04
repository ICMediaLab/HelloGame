package utils.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import utils.LayerRenderable;
import utils.Position;
import utils.Updatable;
import utils.interval.two.Interval2D;
import utils.interval.two.Range2D;
import entities.Entity;

public class ParticleEmitter implements Updatable, LayerRenderable {
    private static final Random random = new Random();
    
    private final int layer;
    private final Position emitterLocation;
    private final Range2D emitterRange;
    private final List<Particle> particles;
    private final List<Image> textures;
    
    private int lifespan;
    private boolean isEmitting;
    
    public ParticleEmitter(List<Image> textures, Entity trigger, Position location, int length){
    	this.layer = trigger.getLayer()-1;
        emitterLocation = location;
        this.textures = textures;
        this.particles = new ArrayList<Particle>();
        this.lifespan = length;
        isEmitting = true;
        //emitterRange = new Sector2D(0f, 2f, Math.PI*3/4, Math.PI*5/4);
        emitterRange = new Interval2D(-0.5f, 0.5f, -0.4f, 0f);
    }
    
    private Particle GenerateNewParticle(){
        Image texture = textures.get(random.nextInt(textures.size()));
        Position position = new Position(emitterLocation.getX(), emitterLocation.getY());
        Position velocity = emitterRange.random();
        
        float angle = 0;
        float angularVelocity = 0.0f;
        Color color = new Color(random.nextFloat() * 0.2f + 0.2f, random.nextFloat() * 0.3f + 0.1f, random.nextFloat() * 0.3f + 0.1f);
        float size = 0.5f * (float)random.nextDouble();
        int ttl = random.nextInt(60);
        
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
