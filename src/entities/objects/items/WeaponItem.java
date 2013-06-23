package entities.objects.items;

import map.MapLoader;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import entities.MovingEntity;
import entities.VeryAbstractDestructibleEntity;
import entities.players.Player;
import game.config.Config;

public abstract class WeaponItem extends VeryAbstractDestructibleEntity<Rectangle> {
	
	private static final int WEAPONITEM_DEFAULT_LAYER = -100;
	
	private final Animation ani;
	
	public WeaponItem(float x, float y, float width, float height, Animation ani) {
		super(new Rectangle(x, y, width, height));
		this.ani = ani;
	}
	
	//copy constructor
	protected WeaponItem(WeaponItem base) {
		super(base.getHitbox(),base);
		this.ani = base.ani;
	}

	@Override
	public void update(GameContainer gc) {
		super.update(gc);
		ani.update(Config.DELTA);
	}
	
	@Override
	public void collide(MovingEntity e) {
		Player p = MapLoader.getCurrentCell().getPlayer();
		if(e == p){
			applyEffect(p);
			die();
		}
	}

	protected abstract void applyEffect(Player p);
	
	@Override
	public int takeDamage(int normalDamage) { return 0; }
	@Override
	public int getHealth() { return 1; }
	@Override
	public int getMaxHealth() { return 1; }

	@Override
	public int getLayer() {
		return WEAPONITEM_DEFAULT_LAYER;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		Image f = ani.getCurrentFrame();
		renderSprite(ani,((int) (getWidth()*Config.getTileSize()) - f.getWidth())/2,-Config.getTileSize());
	}
}