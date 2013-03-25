package entities.objects;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

import utils.LayerRenderable;

import entities.Entity;
import game.config.Config;

public class CirclePhysics implements Entity {
	
	Body body;
	float radius;
	
	public CirclePhysics(float x, float y, float radius, World world){
		this.radius = radius;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(x + radius, y + radius);
		
		body = world.createBody(bodyDef);
		
		CircleShape shape = new CircleShape();
		shape.m_radius = radius;
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.3f;
		fixtureDef.restitution = 0.2f;
		fixtureDef.friction = 0.4f;
		
		body.createFixture(fixtureDef);
	}

	public Body getBody() {
		return body;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.setColor(Color.magenta);
		g.fillOval((body.getPosition().x - 1 - radius)*Config.getTileSize(), (body.getPosition().y - 1 - radius)*Config.getTileSize(), radius*2f*Config.getTileSize(), radius*2f*Config.getTileSize());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {

	}

	@Override
	public Entity clone() {
		return null;
	}

	@Override
	public void collide(Entity e) {
		
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(LayerRenderable o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getdX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getdY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int takeDamage(int normalDamage) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNormalDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHealthPercent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void frameMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOnGround() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void jump() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accelerate(float ddx, float ddy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVelocity(float dx, float dy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop_sounds() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean intersects(Entity e2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersects(Rectangle r2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersects(Shape e2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Shape getHitbox() {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
