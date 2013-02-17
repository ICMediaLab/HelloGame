package entities.players.abilities;

import entities.players.Player;

public interface IPlayerAbility {

	public void use(Player p);

	/**
	 * Forces this class to stop all currently playing sounds.
	 */
	public void stop_sounds();
}
