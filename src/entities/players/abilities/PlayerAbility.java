package entities.players.abilities;

import java.io.Serializable;

import org.newdawn.slick.Image;

import entities.players.Player;

public interface PlayerAbility extends Serializable {

	public void use(Player p);

	public Image getImage();
	public String getName();
	public String getDescription();
	
}
