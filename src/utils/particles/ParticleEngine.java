package utils.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import utils.Position;

public class ParticleEngine {
    private Random random;
    public Position EmitterLocation;
    private List<Particle> particles;
    private List<Image> textures;
    private int total = 5;

    public ParticleEngine(List<Image> textures, Position location)
    {
        EmitterLocation = location;
        this.textures = textures;
        this.particles = new ArrayList<Particle>();
        random = new Random();
    }

    public void update()
    {
        for (int i = 0; i < total; i++) {
            particles.add(GenerateNewParticle());
        }
        

        for (int particle = 0; particle < particles.size(); particle++)
        {
            particles.get(particle).update();
            if (particles.get(particle).TTL <= 0)
            {
                particles.remove(particle);
                particle--;
            }
        }
    }

    private Particle GenerateNewParticle()
    {
        Image texture = textures.get(random.nextInt(textures.size()));
        Position position = EmitterLocation;
        Position velocity = (new Position((float)(random.nextDouble()) * 0.1f, 1f * (float)(random.nextDouble() * 4 + 1)));
        velocity.scale((random.nextFloat() * 2));
        
        float angle = 0;
        float angularVelocity = 0.0f;
        Color color = new Color(0, 0, (float)random.nextDouble());
        float size = 0.5f * (float)random.nextDouble();
        int ttl = 50 + random.nextInt(60);

        return new Particle(texture, position, velocity, angle, angularVelocity, color, size, ttl);
    }

    public void render()
    {
        for (int index = 0; index < particles.size(); index++)
        {
            particles.get(index).render();
        }
    }
}
