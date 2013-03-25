package entities.objects;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

import utils.LayerRenderable;

import entities.Entity;
import game.config.Config;

public class Bricks implements Entity {
	
	Image image;
	Body body;
	float width; // in tiles
	float height; // in tiles
	
	public Bricks(float x, float y, World world) throws SlickException {
		this.image = new Image("data/images/bricks.png");
		this.image.setCenterOfRotation(image.getWidth()/2f, image.getHeight()/2f);
		this.width = image.getWidth() / Config.getTileSize();
		this.height = image.getHeight() / Config.getTileSize();
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(x + width/2f, y + height/2f);
		
		body = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2f, height/2f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1000;
		fixtureDef.restitution = 0.1f;
		fixtureDef.friction = 0.7f;
		
		body.createFixture(fixtureDef);
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
	public int compareTo(LayerRenderable arg0) {
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
