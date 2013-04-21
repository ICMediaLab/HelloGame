package entities.players.abilities;

import java.io.IOException;
import java.io.ObjectInputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.objects.LeafTest;

public abstract class AbstractPlayerAbility implements PlayerAbility {
	
	private static final long serialVersionUID = 433187519826341229L;
	
	protected Image image;
	private final String name;
	private final String description;
	
	public AbstractPlayerAbility(String name, String description) {
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
	
	/**
	 * Serialisation loading method for {@link LeafTest}
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		loadImage();
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
