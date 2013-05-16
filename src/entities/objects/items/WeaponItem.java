package entities.objects.items;

import map.MapLoader;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import entities.AbstractEntity;
import entities.DestructibleEntity;
import entities.MovingEntity;
import entities.StaticEntity;
import entities.players.Player;
import game.config.Config;

public abstract class WeaponItem extends AbstractEntity {
	
	private static final int WEAPONITEM_DEFAULT_LAYER = -100;
	
	private final Animation ani;
	
	public WeaponItem(float x, float y, float width, float height, Animation ani) {
		super(x, y, width, height,1);
		this.ani = ani;
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
	public int getLayer() {
		return WEAPONITEM_DEFAULT_LAYER;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		Image f = ani.getCurrentFrame();
		renderSprite(ani,((int) (getWidth()*Config.getTileSize()) - f.getWidth())/2,-Config.getTileSize());
	}

	@Override
	public void collide(StaticEntity<?> e) { }

	@Override
	public void collide(DestructibleEntity d) { }
}