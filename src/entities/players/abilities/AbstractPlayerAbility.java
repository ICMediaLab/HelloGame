package entities.players.abilities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

abstract class AbstractPlayerAbility implements PlayerAbility {
	
	protected Animation image;
	private final String name;
	private final String description;
	
	AbstractPlayerAbility(String name, String description, String[] path, int[] lengths) {
		this.name = name;
		this.description = description;
		loadImage(path, lengths);
	}
	
	private void loadImage(String[] paths, int[] lengths){
		try {
			Image[] images = new Image[paths.length];
			for (int i = 0; i < paths.length; i++) {
			    images[i] = new Image(paths[i]);
			}
			image = new Animation(images, lengths);
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Animation getImage() {
		return image;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
