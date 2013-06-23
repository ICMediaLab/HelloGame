package entities.objects.items;

import items.Shield;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utils.ImageUtils;
import entities.players.Player;

public class ShieldItem extends WeaponItem {
    
    private static final Animation ani;
    
    static {
        Image img = null;
        try {
            img = new Image("data/images/items/shield_front.png").getScaledCopy(32, 32);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        List<Image> imgs = new ArrayList<Image>();
        int h = img.getHeight(), w = img.getWidth(), dw = 0, ddw = -1;
        while(w > 0){
            imgs.add(img.getScaledCopy(w, h));
            w += dw;
            dw += ddw;
        }
        ImageUtils.appendReverse(imgs, ImageUtils.flipImages(imgs, true, false));
        
        ani = new Animation(imgs.toArray(new Image[imgs.size()]), 50);
        ani.setPingPong(true);
    }
    
    public ShieldItem(float x, float y, float width, float height) {
        super(x, y, width, height, ani);
    }
    
    private ShieldItem(ShieldItem base) {
		super(base);
	}

	@Override
    protected void applyEffect(Player p) {
        p.setShield(new Shield());
    }

	@Override
	public ShieldItem clone() {
		return new ShieldItem(this);
	}

}
