package entities.objects;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public class FloorPhysics {

	Body body;
	float width;
	float height;
	Shape renderShape;
	
	public FloorPhysics(float x, float y, float width, float height, World world) {
		this.width = width;
		this.height = height;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(x, y);
		
		body = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0;
		fixtureDef.restitution = 0.0f;
		fixtureDef.friction = 2f;
		
		body.createFixture(fixtureDef);
		
	}
	
	public void render(Graphics gr) {
		gr.setColor(Color.cyan);
		gr.fillRect(body.getPosition().x - width, body.getPosition().y - height, width, height);
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
	
}
