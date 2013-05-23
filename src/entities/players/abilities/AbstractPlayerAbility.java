package entities.players.abilities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

abstract class AbstractPlayerAbility implements PlayerAbility {
	
	protected Image image;
	private final String name;
	private final String description;
	
	AbstractPlayerAbility(String name, String description) {
		this.name = name;
		this.description = description;
		loadImage();
	}
	
	private void loadImage(){
		try {
			image = new Image("data/images/playerAbility.png");
		} catch (SlickException e) {
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
}
