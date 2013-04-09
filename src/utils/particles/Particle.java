package utils.particles;

import game.config.Config;

import org.newdawn.slick.Image;
import org.newdawn.slick.Color;

import utils.Position;

public abstract class Particle {
	
	private static final float ANGULAR_FRICTION = 0.98f;
	private static final Position GRAVITY = new Position(0f,0.01f);
	private static final Position FRICTION = new Position(0.99f, 0.99f);
	
	private final Image texture;
	private final Position position;
	private final Position velocity;
	private final Color colour;
	
	private float radius;
	
	private float angle;
	private float angularVelocity;
	
	public Particle(Image texture, Position position, Position velocity, float angle, float angularVelocity, Color color, float radius) {
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.angle = angle;
		this.angularVelocity = angularVelocity;
		this.colour = color;
		this.radius = radius;
	}
	
	public Particle(Image texture, Position position, Position velocity, Color color, float size) {
		this(texture,position,velocity,0f,0f,color,size);
	}
	
	public void update() {
		velocity.translate(GRAVITY);
		velocity.scale(FRICTION);
		position.translate(velocity);
		angle += (angularVelocity *= ANGULAR_FRICTION);
	}
	
	protected float getRadius(){
		return radius;
	}
	
	protected void setRadius(float rad){
		radius = rad > 0f ? rad : 0f;
	}
	
	public void render() {
		texture.setRotation(angle); // rotate
		texture.draw((position.getX())*Config.getTileSize(), (position.getY())*Config.getTileSize(), 
				radius*Config.getTileSize(), colour); // render
	}
	
	public abstract boolean isAlive();
	
}
