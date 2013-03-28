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
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

import entities.AbstractEntity;
import entities.Entity;

public class FloorPhysics extends AbstractEntity {

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
		fixtureDef.friction = 0.5f;
		
		body.createFixture(fixtureDef);
		body.setUserData(this);
		
		this.setPosition(x, y);
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
	public void collide(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AbstractEntity clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
