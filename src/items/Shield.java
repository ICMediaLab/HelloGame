package items;

import items.projectiles.Projectile;
import map.Cell;
import map.MapLoader;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import utils.ImageUtils;
import utils.Position;
import entities.MovingEntity;
import entities.players.Player;
import game.MouseCapture;
import game.config.Config;

public class Shield {

    protected Animation sprite;
    protected final Rectangle hitbox;
    private String name = "Shield";
    private boolean raised = false;
    
    public Shield(Rectangle hitbox) {
        this.hitbox = hitbox;
        Image[] images = ImageUtils.populate(
                "data/images/items/shield_front.png",
                "data/images/items/shield_front_blink.png");
        images[0] = images[0].getScaledCopy(32, 32);
        images[1] = images[1].getScaledCopy(32,32);
        int[] duration = {2000,200};
        this.sprite = new Animation(images, duration);
        
        //TODO Sound effect for hitting shield
    }
    
    public Shield() {
        this(new Rectangle(1,1,1,1));
    }

    

    public void raise() {
        raised = true;
    }
    
    public boolean raised() {
        return raised;
    }

    public void update(GameContainer gc) {
        sprite.update(Config.DELTA);
        
        Input i = gc.getInput();
        Cell cell = MapLoader.getCurrentCell();
        Player p = cell.getPlayer();
        
        if (!i.isKeyDown(Input.KEY_LSHIFT)) {
            raised = false;
        }
        
        if (raised) {
            Position mouse = MouseCapture.getMousePositionRelative();
            Position player = p.getPosition();
            
            double angle = player.distanceTo(mouse).getAngle();
            
            hitbox.setLocation(p.getCentreX() - hitbox.getWidth()/2 + (float)Math.cos(angle), p.getCentreY() - hitbox.getHeight()/2 + (float)Math.sin(angle));
        
            for (MovingEntity e : cell.getMovingEntities()) {
                if (e != p && e.intersects(hitbox)) {
                    if (e instanceof Projectile) {
                        cell.removeMovingEntity(e);
                    }
                }
            }
        }
    }
    
    public void render(GameContainer gc, Graphics g) {
        sprite.draw((int)((hitbox.getX() - 1f)*Config.getTileSize()), (int)((hitbox.getY() - 1f)*Config.getTileSize()), new Color(255,255,255));
    
        //debug 
        g.draw(hitbox.transform(Transform.createTranslateTransform(-1, -1)).transform(Transform.createScaleTransform(Config.getTileSize(), Config.getTileSize())));
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
