package utils.particles;

import org.newdawn.slick.Image;
import org.newdawn.slick.Color;

import utils.Position;

public class Particle {
	
	private static final float ANGULAR_FRICTION = 0.98f;
	private static final Position GRAVITY = new Position(0f,0.01f);
	private static final Position FRICTION = new Position(0.99f, 0.99f);
	
	private final Image texture;
	private final Position position;
	private final Position velocity;
	private final Color colour;
	
	private float radius;
	private final float radiusStepChange;
	
	private float angle;
	private float angularVelocity;
	
	int ttl;

	public Particle(Image texture, Position position, Position velocity,
		float angle, float angularVelocity, Color color, float radius, int ttl) {
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.angle = angle;
		this.angularVelocity = angularVelocity;
		this.colour = color;
		this.radius = radius;
		this.ttl = ttl;
		this.radiusStepChange = radius/ttl;
	}
	
	public Particle(Image texture, Position position, Position velocity, Color color, float size, int ttl) {
			this(texture,position,velocity,0f,0f,color,size,ttl);
		}

	public void update() {
		ttl--;
		radius -= radiusStepChange;
		velocity.translate(GRAVITY);
		velocity.scale(FRICTION);
		position.translate(velocity);
		angle += (angularVelocity *= ANGULAR_FRICTION);
	}

	public void render() {
		//Position origin = new Position(texture.getWidth() / 2, texture.getHeight() / 2);
		
		texture.setRotation(angle); // rotate
		texture.draw(position.getX(), position.getY(), radius, colour); // render
	}
	
}
