package items;

import map.Cell;
import map.MapLoader;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import sounds.SoundGroup;
import utils.ImageUtils;
import entities.MovingEntity;
import entities.players.Player;
import game.config.Config;

public class Stick extends AbstractWeapon {

    private boolean swung = false;
    private int counter = 0;
    private SoundGroup swingSound;
    private int dir;
    
    public Stick(Rectangle hitbox, Image[] images, int damage) {
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
    
    public Stick() {
		this(new Rectangle(1,1,1,1), ImageUtils.populate(
				"data/images/stick/stick_0010_Vector-Smart-Object-copy-4.png",
				"data/images/stick/stick_0009_Vector-Smart-Object-copy-5.png",
				"data/images/stick/stick_0008_Vector-Smart-Object-copy-6.png",
				"data/images/stick/stick_0007_Vector-Smart-Object-copy-7.png",
				"data/images/stick/stick_0006_Vector-Smart-Object-copy-8.png",
				"data/images/stick/stick_0005_Vector-Smart-Object-copy-9.png",
				"data/images/stick/stick_0004_Vector-Smart-Object-copy-10.png",
				"data/images/stick/stick_0003_Vector-Smart-Object-copy-11.png",
				"data/images/stick/stick_0002_Vector-Smart-Object-copy-12.png",
				"data/images/stick/stick_0001_Vector-Smart-Object-copy-13.png",
				"data/images/stick/stick_0000_Vector-Smart-Object-copy-14.png"),
					5);
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
    public void update(GameContainer gc) {
        counter += Config.DELTA;
        // if counter hasn't played full sword animation
        // TODO: for some reason this turns out to be twice the animation length.  I made a quick fix by setting the sprite to ping pong mode, doubling the length
        if (swung && counter <= defaultDuration * duration.length * 2) {
        	Cell cell = MapLoader.getCurrentCell();
        	Player p = cell.getPlayer();
        	
        	
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
            for (MovingEntity e : cell.getMovingEntities()) {
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
    public void render(GameContainer gc, Graphics g) {
        int absxOffset = -sprite.getWidth();
        float relxOffset = 1f; 
        sprite.draw((int)((hitbox.getX() + relxOffset)*Config.getTileSize() + absxOffset), (int)((hitbox.getY()-2.5)*Config.getTileSize()), new Color(255,255,255));
    }

}
