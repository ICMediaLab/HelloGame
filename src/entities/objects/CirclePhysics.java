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

import entities.AbstractEntity;
import entities.DestructibleEntity;
import entities.MovingEntity;
import entities.StaticEntity;
import game.config.Config;

public class CirclePhysics extends AbstractEntity {
	
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
	public void render(GameContainer gc, Graphics g) {
		g.setColor(Color.magenta);
		g.fillOval((body.getPosition().x - 1 - radius)*Config.getTileSize(), (body.getPosition().y - 1 - radius)*Config.getTileSize(), radius*2f*Config.getTileSize(), radius*2f*Config.getTileSize());
	}

	@Override
	public void update(GameContainer gc) {

	}

	@Override
	public void collide(MovingEntity e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void collide(StaticEntity<?> e) {
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

	@Override
	public void collide(DestructibleEntity d) {
		// TODO Auto-generated method stub
		
	}
	
}
