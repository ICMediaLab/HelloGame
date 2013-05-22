package items;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import sounds.SoundGroup;
import entities.players.Player;
import game.config.Config;

public class Shield {

    protected Image sprite;
    protected final Rectangle hitbox;
    private SoundGroup swingSound;
    private String name = "Shield";
    
    public Shield(Rectangle hitbox) {
        this.hitbox = hitbox;
        
        try {
            this.sprite = new Image("data/images/shield_item.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        
        //TODO Sound effect for hitting shield
//        try {
//            hitSound = new SoundGroup("player/stick/swing");
//        } catch (SlickException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
    
    public Shield() {
        this(new Rectangle(1,1,1,1));
    }

    

    public void raise(Player p) {
        //TODO: Make shield do shit. Rotate around player depending on mouse position and block moving entities.
        // rendering will need changing too.
    }
    
    public boolean raised() {
        return false;
    }

    public void update(GameContainer gc) {
        
    }
    
    public void render(GameContainer gc, Graphics g) {
        int absxOffset = -sprite.getWidth();
        float relxOffset = 1f; 
        sprite.draw((int)((hitbox.getX() + relxOffset)*Config.getTileSize() + absxOffset), (int)((hitbox.getY()-2.5)*Config.getTileSize()), new Color(255,255,255));
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
