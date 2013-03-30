package entities.players.abilities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class PlayerAbility implements IPlayerAbility {
	
	protected Image image;
	protected String name = "Ability";
	protected String description = "Description";
	protected boolean active = true;
	
	public PlayerAbility() {
		try {
			this.image = new Image("data/images/playerAbility.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Image getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
