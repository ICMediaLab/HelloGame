package entities.objects.items;

import items.Weapons;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utils.ImageUtils;
import entities.players.Player;

public class StickItem extends WeaponItem {
	
	private static final Animation ani;
	
	static {
		Image img = null;
		try {
			img = new Image("data/images/stick_item.png");
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
	
	public StickItem(float x, float y, float width, float height) {
		super(x, y, width, height, ani);
	}
	
	@Override
	protected void applyEffect(Player p) {
		p.setWeapon(Weapons.STICK);
	}

}
