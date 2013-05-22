package items;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import utils.ImageUtils;
import entities.players.Player;
import game.config.Config;

public abstract class AbstractWeapon implements Weapon {

    protected Animation sprite;
    protected final Animation right;
    protected final Animation left;
    protected final Rectangle hitbox;
    protected final int damage;
    protected final int[] duration;
    protected static int defaultDuration = 15;
    
    
    public AbstractWeapon(Rectangle hitbox, Image[] images, int damage) {
        this.hitbox = hitbox;
        this.damage = damage;
        duration = new int[images.length];
//        Arrays.fill(duration, defaultDuration);
        this.right = new Animation(images, defaultDuration);
        images = ImageUtils.flipImages(images, true, false);
        this.left = new Animation(images, defaultDuration);
        sprite = right;
    }
    
    public abstract void attack(Player p);
    
    @Override
    public void render(GameContainer gc, Graphics g) {
        sprite.draw((int)((hitbox.getX()-1)*Config.getTileSize()), (int)((hitbox.getY()-1)*Config.getTileSize()), new Color(255,255,255));
    }

    public abstract boolean used();
    
    public abstract String getName();
    
}
