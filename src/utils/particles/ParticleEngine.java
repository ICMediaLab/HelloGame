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
import entities.Entity;

public class ParticleEngine implements Updatable, LayerRenderable {
    private static final Random random = new Random();
    
    private final int layer;
    private final Position emitterLocation;
    private final List<Particle> particles;
    private final List<Image> textures;
    
    private int total = 1;
    private int lifespan;
    private boolean isEmitting;

    public ParticleEngine(List<Image> textures, Entity trigger, Position location, int length){
    	this.layer = trigger.getLayer()-1;
        emitterLocation = location;
        this.textures = textures;
        this.particles = new ArrayList<Particle>();
        this.lifespan = length;
        isEmitting = true;
    }

    private Particle GenerateNewParticle(){
        Image texture = textures.get(random.nextInt(textures.size()));
        Position position = new Position(emitterLocation.getX(), emitterLocation.getY());
        Position velocity = (new Position(random.nextFloat() - 0.5f, (random.nextFloat() * -0.3f)));
        velocity.scale((random.nextFloat() * 2));
        
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
		if (lifespan-- <= 0) {
            isEmitting = particles.size() > 0;
        } else {
            for (int i = 0; i < total; i++) {
                particles.add(GenerateNewParticle());
            }
        }
        

        for (int particle = 0; particle < particles.size(); particle++){
            particles.get(particle).update();
            if (particles.get(particle).ttl <= 0){
                particles.remove(particle);
                particle--;
            }
        }
	}

	public int getLayer() {
		return layer;
	}
}
