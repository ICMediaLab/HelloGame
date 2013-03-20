package entities.objects;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class CirclePhysics {
	
	Body body;
	float radius;
	
	public CirclePhysics(float x, float y, float radius, World world){
		this.radius = radius;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(x, y);
		
		body = world.createBody(bodyDef);
		
		CircleShape shape = new CircleShape();
		shape.m_radius = radius;
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = 0.0f;
		fixtureDef.friction = 1f;
		
		body.createFixture(fixtureDef);
	}

	public Body getBody() {
		return body;
	}

	public void render(Graphics gr) {
		gr.setColor(Color.magenta);
		gr.fillOval(body.getPosition().x - radius, body.getPosition().y - radius, radius*2f, radius*2f);
	}	
	
}
