package items;

import java.util.Set;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import sounds.SoundGroup;
import entities.Entity;
import entities.players.Player;

public class Sword extends Weapon {

    private boolean swung = false;
    private int counter = 0;
    private SoundGroup swingSound;
    
    public Sword(Rectangle hitbox, Image[] images, int damage) {
        super(hitbox, images, damage);
        swingSound = new SoundGroup("stick/swing");
    }
    
    @Override
    public boolean used() {
        return swung;
    }

    @Override
    public void attack(Player p) {
      //if sword isnt already swinging
        if (!swung) {
            hitbox.setLocation(p.getX() + 1.1f, p.getY());
            swung = true;
            swingSound.playSingle();
        }
    }

    @Override
    public void update(long delta, Set<Entity> enemies, Player p) {
        counter += delta;
        // if counter hasn't played full sword animation
        if (swung && counter <= defaultDuration * duration.length) {
            hitbox.setLocation(p.getX() + 1.1f, p.getY());
            sprite.update(delta);
            
            // do NOT pass in ALL enemies in cell, or this will be slow
            // find some way to pass only adjacent enemies to player.
            for (Entity e : enemies) {
                if (e.intersects(hitbox)) {
                    e.takeDamage(damage);
                    e.moveX(1.5f);
                }
            }
        } else {
            //otherwise set swung to false and stop displaing sword
            swung = false;
            counter = 0;
        }
    }

}
