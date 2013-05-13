package entities.players.abilities;

import org.newdawn.slick.Image;

import entities.players.Player;

interface PlayerAbility {

	public void use(Player p);

	public Image getImage();
	public String getName();
	public String getDescription();
	
}
