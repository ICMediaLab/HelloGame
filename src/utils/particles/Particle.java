package utils.particles;

import game.config.Config;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import utils.Position;

public abstract class Particle {
	
	private static final Position NORMAL_GRAVITY = new Position(0f,0.01f);
	private static final Position NORMAL_FRICTION = new Position(0.99f, 0.99f);
	
	private final Image texture;
	private final Position position;
	private final Position velocity;
	private final Position attractor;
	private final Position inertia;
	private final Color colour;
	
	private float radius;
	
	public Particle(Image texture, Position position, Position velocity, Color color, float radius) {
		this(texture,position,velocity,color,radius,NORMAL_GRAVITY,NORMAL_FRICTION);
	}
	
	public Particle(Image texture, Position position, Position velocity, Color color, float radius, float drag) {
		this(texture,position,velocity,color,radius,getAttractor(drag),getInertia(drag));
	}
	
	public Particle(Image texture, Position position, Position velocity, Color color, float radius, Position attractor, Position inertia) {
		this.attractor = attractor;
		this.position = position;
		this.velocity = velocity;
		this.texture = texture;
		this.inertia = inertia;
		this.colour = color;
		this.radius = radius;
	}
	
	static Position getInertia(float drag) {
		return NORMAL_FRICTION.scaledCopy(drag);
	}

	static Position getAttractor(float drag){
		return NORMAL_GRAVITY.scaledCopy(drag);
	}
	
	public void update() {
		velocity.translate(attractor);
		velocity.scale(inertia);
		position.translate(velocity);
	}
	
	protected float getRadius(){
		return radius;
	}
	
	protected void setRadius(float rad){
		radius = rad > 0f ? rad : 0f;
	}
	
	public void render() {
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
}
