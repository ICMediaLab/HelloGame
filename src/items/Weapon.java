package items;

import entities.Entity;
import entities.players.Player;
import game.config.Config;

import java.util.Arrays;
import java.util.Set;

import org.lwjgl.util.Renderable;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public abstract class Weapon implements Renderable {

    protected final Animation sprite;
    protected final Rectangle hitbox;
    protected final int damage;
    protected final int[] duration;
    protected static final int defaultDuration = 15;
    
    
    public Weapon(Rectangle hitbox, Image[] images, int damage) {
        this.hitbox = hitbox;
        this.damage = damage;
        duration = new int[images.length];
        Arrays.fill(duration, defaultDuration);
        this.sprite = new Animation(images, duration);
        sprite.setPingPong(true); // TODO: implement this in the sword class rather than the weapon class
        							// also TODO: make animation run through but then stop, ready to start at first frame again when called again
    }
    
    public abstract void attack(Player p);
    
    public abstract void update(long delta, Set<Entity> enemies, Player p);
    
    public void render() {
        sprite.draw((int)((hitbox.getX()-1)*Config.getTileSize()), (int)((hitbox.getY()-1)*Config.getTileSize()), new Color(255,255,255));
    }

    public abstract boolean used();
    
}
