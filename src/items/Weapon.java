package items;

import utils.Renderable;
import utils.Updatable;
import entities.players.Player;

public interface Weapon extends Updatable, Renderable {
	public abstract void attack(Player p);
	public abstract boolean used();
}
