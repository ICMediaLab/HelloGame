package entities.objects;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import entities.AbstractEntity;
import entities.Entity;
import game.GameplayState;
import game.config.Config;

public class Bricks extends AbstractEntity {
	
	Image image;
	float width; // in tiles
	float height; // in tiles
	
	public Bricks(float x, float y) {
		try {
			this.image = new Image("data/images/bricks.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.image.setCenterOfRotation(image.getWidth()/2f, image.getHeight()/2f);
		this.width = image.getWidth() / Config.getTileSize();
		this.height = image.getHeight() / Config.getTileSize();
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(x + width/2f, y + height/2f);
		
		body = GameplayState.getWorld().createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2f, height/2f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1000;
		fixtureDef.restitution = 0.1f;
		fixtureDef.friction = 0.7f;
		
		body.createFixture(fixtureDef);
		body.setUserData(this);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		image.drawCentered((body.getPosition().x - 1)*Config.getTileSize(), (body.getPosition().y - 1)*Config.getTileSize());
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		image.rotate((float) Math.toDegrees(body.getAngle()) - image.getRotation());
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
	public AbstractEntity clone() {
		Bricks bricks = new Bricks(getX(), getY());
		destroy();
		return bricks;
	}

	

}
