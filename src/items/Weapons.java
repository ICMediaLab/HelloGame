package items;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import entities.players.Player;

public enum Weapons implements Weapon {
	STICK(new Stick());
	
	private final AbstractWeapon w;
	private final String name;
	
	private Weapons(AbstractWeapon w){
		this.w = w;
		this.name= w.getName();
	}

	@Override
	public void attack(Player p) {
		w.attack(p);
	}

	@Override
	public boolean used() {
		return w.used();
	}

	@Override
	public void update(GameContainer gc) {
		w.update(gc);
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		w.render(gc, g);
	}

}
