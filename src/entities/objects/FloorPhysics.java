package entities.objects;

import org.jbox2d.collision.shapes.PolygonShape;
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

public class FloorPhysics implements Entity {

	Body body;
	float width;
	float height;
	Shape renderShape;
	
	public FloorPhysics(float x, float y, float width, float height, World world) {
		this.width = width;
		this.height = height;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(x + width/2f, y + height/2f);
		
		body = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2f, height/2f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0;
		fixtureDef.restitution = 0.0f;
		fixtureDef.friction = 2f;
		
		body.createFixture(fixtureDef);
		
	}

	public Body getBody() {
		return body;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(body.getPosition().x - width, body.getPosition().y - height, width, height);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void collide(Entity e) {
		// TODO Auto-generated method stub
		
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
