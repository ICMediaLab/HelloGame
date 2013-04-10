package utils.particles;

import game.config.Config;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import utils.Position;

public abstract class Particle {
	
	private static final float NORMAL_ANGULAR_FRICTION = 0.98f;
	private static final Position NORMAL_GRAVITY = new Position(0f,0.01f);
	private static final Position NORMAL_FRICTION = new Position(0.99f, 0.99f);
	
	private float angFriction = NORMAL_ANGULAR_FRICTION;
	private Position gravity = NORMAL_GRAVITY;
	private Position friction = NORMAL_FRICTION;
	
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
		velocity.translate(gravity);
		velocity.scale(friction);
		position.translate(velocity);
		angle += (angularVelocity *= angFriction);
	}
	
	protected float getRadius(){
		return radius;
	}
	
	protected void setRadius(float rad){
		radius = rad > 0f ? rad : 0f;
	}
	
	public void render() {
		texture.setRotation(angle); // rotate
		texture.draw((position.getX() - 1)*Config.getTileSize(), (position.getY() - 1)*Config.getTileSize(), 
				radius*Config.getTileSize(), colour); // render
	}
	
	public abstract boolean isAlive();
	
	public float getCenterY(){
		return position.getY();
	}
	
	public float getCenterX(){
		return position.getX();
	}
	
	public float getdX(){
		return velocity.getX();
	}
	
	public float getdY(){
		return velocity.getY();
	}

	float getAngularFriction() {
		return angFriction;
	}

	void setAngularFriction(float angularFriction) {
		angFriction = angularFriction;
	}

	Position getGravity() {
		return gravity;
	}

	void setGravity(Position gravity) {
		this.gravity = gravity;
	}

	Position getFriction() {
		return friction;
	}

	void setFriction(Position friction) {
		this.friction = friction;
	}
	
}
