package items;

import java.util.Set;

import org.lwjgl.util.Renderable;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import utils.ImageUtils;
import entities.Entity;
import entities.players.Player;
import game.config.Config;

public abstract class Weapon implements Renderable {

    protected Animation sprite;
    protected final Animation right;
    protected final Animation left;
    protected final Rectangle hitbox;
    protected final int damage;
    protected final int[] duration;
    protected static int defaultDuration = 15;
    
    
    public Weapon(Rectangle hitbox, Image[] images, int damage) {
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
    
    public abstract void update(long delta, Set<? extends Entity> set, Player p);
    
    public void render() {
        sprite.draw((int)((hitbox.getX()-1)*Config.getTileSize()), (int)((hitbox.getY()-1)*Config.getTileSize()), new Color(255,255,255));
    }

    public abstract boolean used();
    
}
