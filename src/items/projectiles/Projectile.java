package items.projectiles;

import game.config.Config;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

public class Projectile extends entities.Entity{
	
	private final Animation moving;
	private Animation sprite;
	private static final float speed = 0.45f/32f;
	private float xSpeed;
	private float ySpeed;
	/**
	 * 
	 * @param hitbox
	 * @param maxhealth
	 * @param damage
	 * @param angle in radians
	 */
	public Projectile(Rectangle hitbox, int maxhealth, int damage, double angle ){
		super(hitbox, maxhealth, damage);
		Image[] movementForward = null;
		try {
			movementForward = new Image[]{new Image("data/images/nyan.png")};
		} catch (SlickException e) {
			//do shit all
		}
		int[] duration = {400};
		moving = new Animation(movementForward, duration, false);
		sprite=moving;
		
		xSpeed = speed*(float)Math.cos(angle);
		ySpeed = speed*(float)Math.sin(angle); //currently only works horizontally
		
	}
	
	public Projectile(Rectangle hitbox, int maxhealth, int damage)
	{
		this(hitbox, maxhealth, damage, 0f);
	}
	
	public Projectile(Rectangle hitbox, int maxhealth) {
		this(hitbox, maxhealth, 10, 0f);
	}

	@Override
	public void render() {
		sprite.draw((int)((getX()-1)*Config.getTileSize()), (int)((getY()-1)*Config.getTileSize()), new Color(255,255,255));		
	}

	public void update(Input input) {
		setPosition(getX() + getdX(), getY()); //ignores gravity
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Projectile(new Rectangle(getX(), getY(), getWidth(), getHeight()),getMaxHealth());
	}

}
