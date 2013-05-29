package items.projectiles;

import map.Cell;
import map.MapLoader;
import map.Tile;
import map.tileproperties.TileProperty;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import utils.Position;
import entities.DestructibleEntity;
import entities.MovingEntity;
import entities.StaticEntity;
import entities.VeryAbstractEntity;
import game.config.Config;

public class Projectile extends VeryAbstractEntity<Shape> {
	
	private static final int PROJECTILE_DEFAULT_LAYER = -500;
	
	private transient final Image baseImage;
	private transient Animation sprite;
	
	private static final float NORMAL_SPEED = 0.5f;
	private static final float MAX_SPEED = 0.5f;
	
	private final Position dxdy;
	private final int damage;
	private double angle;
	private boolean justCreated = true;
    private boolean collidedWithPlayer = true;
	
	public Projectile(Position centre, int damage, double angle, float speed){
		super(null);
		baseImage = getBaseImage();
		updateSprite();
		
		this.angle = angle;
		this.damage = damage;
		
		float width  = (float) baseImage.getWidth()  / Config.getTileSize();
		float height = (float) baseImage.getHeight() / Config.getTileSize();
		
		setHitbox(new Rectangle(centre.getX()-width/2, centre.getY()-height/2, width, height).transform(Transform.createRotateTransform((float) angle,centre.getX(),centre.getY())));
		this.dxdy = new Position((float)Math.cos(angle),(float)Math.sin(angle));
		this.dxdy.scale(Math.min(NORMAL_SPEED * speed, MAX_SPEED));
		
	}
	
	private Image getBaseImage(){
		try {
			return new Image("data/images/projectile.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Projectile(float x, float y, int damage, double angle, float speed){
		this(new Position(x,y),damage,angle,speed);
	}
	
	@Override
	public Projectile clone() {
		return this; //TODO
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		sprite.draw((int)((getHitbox().getCenterX()-1f)*Config.getTileSize()-sprite.getWidth()/2), (int)((getHitbox().getCenterY()-1)*Config.getTileSize()-sprite.getHeight()/2));
		/*For debugging purposes.*/
		/*g.setColor(Color.green); 
		g.draw(hitbox.transform(Transform.createTranslateTransform(-1, -1)).transform(Transform.createScaleTransform(Config.getTileSize(), Config.getTileSize())));
		//*/
	}

	@Override
	public int getLayer() {
		return PROJECTILE_DEFAULT_LAYER;
	}

	@Override
	public float getdX() {
		return dxdy.getX();
	}

	@Override
	public float getdY() {
		return dxdy.getY();
	}

	@Override
	public int takeDamage(int normalDamage) {
		MapLoader.getCurrentCell().removeMovingEntity(this);
		return 0;
	}

	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public int getNormalDamage() {
		return damage;
	}

	@Override
	public int getHealth() {
		return 0;
	}

	@Override
	public int getMaxHealth() {
		return 0;
	}

	@Override
	public void frameMove() {
		transformHitbox(Transform.createTranslateTransform(dxdy.getX(), dxdy.getY()));
		//sprite.update(Config.DELTA); useless as the image is being reloaded each time anyway
		{
			int cX = (int) getCentreX(), cY = Math.max(1, (int) getCentreY());
			if (cX < 0) {
				MapLoader.getCurrentCell().removeMovingEntity(this);
				return;
			}
			Tile cT = MapLoader.getCurrentCell().getTile(cX, cY);
			// gravity
			dxdy.translate(0, 0.1f*cT.lookup(TileProperty.GRAVITY)); 
			dxdy.translate(-getdX()*0.02f*cT.lookup(TileProperty.FRICTIONX), -getdY()*0.02f*cT.lookup(TileProperty.FRICTIONY));
		}
		// update angle and hitbox
		double lastangle = angle;
		angle = Math.atan2(dxdy.getY(), dxdy.getX());
		transformHitbox(Transform.createRotateTransform((float) (angle - lastangle),getCentreX(),getCentreY()));
		
		// update animation (copied and pasted from above)
		updateSprite();
	}
	
	private void updateSprite(){
		baseImage.setRotation((float)(angle * 180/Math.PI));
		sprite = new Animation(new Image[]{baseImage}, 200, false);
	}
	
	@Override
	public boolean isOnGround() {
		return false;
	}

	@Override
	public void jump() {
	}

	@Override
	public void accelerate(float ddx, float ddy) {
		dxdy.translate(ddx, ddy);
	}

	@Override
	public void setVelocity(float dx, float dy) {
		dxdy.set(dx, dy);
	}

	@Override
	public void update(GameContainer gc) {
		frameMove();
		int x = (int) getCentreX(), y = Math.max(1, (int) getCentreY());
		Cell cell = MapLoader.getCurrentCell();
		if(x <= 0 || x >= cell.getWidth() - 1 || y >= cell.getHeight() - 1 || 
				cell.getTile(x, y).lookup(TileProperty.BLOCKED)) {
			MapLoader.getCurrentCell().removeMovingEntity(this);
		}
		
		if (!collidedWithPlayer) {
		    justCreated = false;
		}
		collidedWithPlayer = false;
	}

	@Override
	public void stop_sounds() {
	}

	@Override
	public void collide(MovingEntity e) {
		if(e != MapLoader.getCurrentCell().getPlayer() || !justCreated) {
			e.takeDamage(damage);
			MapLoader.getCurrentCell().removeMovingEntity(this);
		} else {
		    collidedWithPlayer = true;
		}
	}

	@Override
	public void collide(StaticEntity<?> e) {
		if(e.isSolid()){
			MapLoader.getCurrentCell().removeMovingEntity(this);
		}
	}

	@Override
	public void collide(DestructibleEntity d) {
		MapLoader.getCurrentCell().removeMovingEntity(this);
	}
	

	@Override
	public boolean isOnWall() {
		return false;
	}

}
