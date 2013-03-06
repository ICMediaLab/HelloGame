package items.projectiles;

import game.config.Config;

import org.lwjgl.util.Renderable;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utils.Position;

public class Projectile implements Renderable{
	
	private final Animation moving;
	private Animation sprite;
	private static final float NORMAL_SPEED = 0.45f/32f;
	private final Position xy,dxdy;
	private final float width,height;
	private final int damage;
	private final double angle;
	

	public Projectile(Position xy,float width,float height, int damage, double angle ){
		Image[] movementForward = null;
		try {
			movementForward = new Image[]{new Image("data/images/nyan_0.gif"),new Image("data/images/nyan_1.gif"),new Image("data/images/nyan_2.gif"),new Image("data/images/nyan_3.gif"),
					new Image("data/images/nyan_4.gif"), new Image("data/images/nyan_5.gif")};
		} catch (SlickException e) {
			//do shit all
		}
		int[] duration = {200,200,200,200,200,200};
		moving = new Animation(movementForward, duration, false);
		sprite=moving;
		
		this.angle = angle;
		this.damage = damage;
		this.width = width; this.height = height;
		this.xy = xy;
		this.dxdy = new Position((float)Math.cos(angle),(float)Math.sin(angle)); //currently only works horizontally
		this.dxdy.scale(NORMAL_SPEED);
		
	}
	
	public Projectile(float x, float y, float width, float height, int damage, double angle){
		this(new Position(x,y),width,height,damage,angle);
	}
	
	@Override
	public void render() {
		sprite.draw((int)((xy.getX()-1)*Config.getTileSize()), (int)((xy.getY()-1)*Config.getTileSize()), new Color(255,255,255));		
	}

	public void update(long DELTA) {
		xy.translate(dxdy); //ignores gravity
		sprite.update(DELTA);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Projectile(xy.getX(), xy.getY(), width, height,damage,angle);
	}

}
