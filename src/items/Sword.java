package items;

import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import sounds.SoundGroup;
import entities.Entity;
import entities.players.Player;
import game.config.Config;

public class Sword extends Weapon {

    private boolean swung = false;
    private int counter = 0;
    private SoundGroup swingSound;
    private int dir;
    
    public Sword(Rectangle hitbox, Image[] images, int damage) {
        super(hitbox, images, damage);
        right.setPingPong(true); left.setPingPong(true);
		// also TODO: make animation run through but then stop, ready to start at first frame again when called again
        // no idea what this actually means though...
        try {
			swingSound = new SoundGroup("player/stick/swing");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    public boolean used() {
        return swung;
    }

    @Override
    public void attack(Player p) {
      //if sword isnt already swinging
        if (!swung) {
            dir = p.getDirection();
            if (dir == 1) {
                hitbox.setLocation(p.getX() + 1.1f, p.getY());
                sprite = right;
            } else {
                hitbox.setLocation(p.getX() - 1.0f, p.getY());
                sprite = left;
            }
            swung = true;
            swingSound.playSingle();
        }
    }

    @Override
    public void update(long delta, Set<? extends Entity> enemies, Player p) {
        counter += delta;
        // if counter hasn't played full sword animation
        // TODO: for some reason this turns out to be twice the animation length.  I made a quick fix by setting the sprite to ping pong mode, doubling the length
        if (swung && counter <= defaultDuration * duration.length * 2) {
            dir = p.getDirection(); //update sword location
            sprite.restart(); // restart sprite's internal counter
            if (dir == 1) {
                hitbox.setLocation(p.getX() + 1.1f, p.getY());
                sprite = right; //change for direction
            } else {
                hitbox.setLocation(p.getX() - 1.0f, p.getY());
                sprite = left;
            }
            sprite.update(counter); //update sprite to correct frame in anim
            
            // do NOT pass in ALL enemies in cell, or this will be slow
            // find some way to pass only adjacent enemies to player.
            for (Entity e : enemies) {
                if (e != p && e.intersects(hitbox)) {
                    e.takeDamage(damage); //take damage
                    if (dir == 1) {
                        e.accelerate(1.5f,0); //move enemy right or left
                    } else {
                    	e.accelerate(-1.5f,0);
                    }
                }
            }
        } else {
            //otherwise set swung to false and stop displaing sword
            swung = false;
            counter = 0;
            left.restart();
            right.restart(); //reset both anims
        }
    }
    
    @Override
    public void render() {
        int xOffset = 2 * dir;
        if (dir == -1) {
            xOffset /= 2;
        }
        sprite.draw((int)((hitbox.getX()-xOffset)*Config.getTileSize()), (int)((hitbox.getY()-2.5)*Config.getTileSize()), new Color(255,255,255));
    }

}
