package items.projectiles;

import map.AbstractLayerRenderable;
import map.Cell;
import map.tileproperties.TileProperty;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.StateBasedGame;

import utils.MapLoader;
import utils.Position;
import entities.Entity;
import game.config.Config;

public class Projectile extends AbstractLayerRenderable {
	
	private static final int PROJECTILE_DEFAULT_LAYER = -500;
	
	private final Animation moving;
	private Animation sprite;
	private static final float NORMAL_SPEED = 0.0765f;
	private final Position dxdy;
	private final float width,height;
	private final int damage;
	private final double angle;
	private Shape hitbox;
	

	public Projectile(Position centre,float width,float height, int damage, double angle ){
		Image[] movementForward = null;
		try {
		    Image projImage = new Image("data/images/projectile.png");
		    projImage.rotate((float)(angle * 180/Math.PI));
		    movementForward = new Image[]{projImage};
		} catch (SlickException e) {
			//do shit all
		}
		moving = new Animation(movementForward, 200, false);
		sprite=moving;
		
		this.angle = angle;
		this.damage = damage;
		this.width = width;
		this.height = height;
		this.hitbox = new Rectangle(centre.getX()-width/2, centre.getY()-height/2, width, height).transform(Transform.createRotateTransform((float) angle,centre.getX(),centre.getY()));
		this.dxdy = new Position((float)Math.cos(angle),(float)Math.sin(angle));
		this.dxdy.scale(NORMAL_SPEED);
		
	}
	
	public Projectile(float x, float y, float width, float height, int damage, double angle){
		this(new Position(x,y),width,height,damage,angle);
	}
	
	public void update(long DELTA) {
		hitbox = hitbox.transform(Transform.createTranslateTransform(dxdy.getX(), dxdy.getY()));
		sprite.update(DELTA);
		Cell currentCell = MapLoader.getCurrentCell();
		int x = (int) hitbox.getCenterX(); int y = (int) hitbox.getCenterY();
		if(x < 0 || y <= 0 || x >= currentCell.getWidth() - 1 || y >= currentCell.getHeight() - 1 || 
		        currentCell.getTile(x, y).lookupProperty(TileProperty.BLOCKED).getBoolean()) {
            MapLoader.getCurrentCell().removeProjectile(this);
		}
		for (Entity e : MapLoader.getCurrentCell().getEntities()) {
		    // apply damage
		    if (hitbox.intersects(e.getHitbox()) && !e.equals(currentCell.getPlayer())) {
	            e.takeDamage(damage);
	            currentCell.removeProjectile(this);
	        }
		}
		
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Projectile(hitbox.getCenterX(),hitbox.getCenterY(), width, height,damage,angle);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		sprite.draw((int)((hitbox.getCenterX()-1f)*Config.getTileSize()-sprite.getWidth()/2), (int)((hitbox.getCenterY()-1)*Config.getTileSize()-sprite.getHeight()/2));
		/* For debugging purposes.
		g.setColor(Color.green); 
		g.draw(hitbox.transform(Transform.createTranslateTransform(-1, -1)).transform(Transform.createScaleTransform(Config.getTileSize(), Config.getTileSize())));//*/
		
	}

	@Override
	public int getLayer() {
		return PROJECTILE_DEFAULT_LAYER;
	}

}
