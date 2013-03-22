package utils;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface LayerRenderable extends Comparable<LayerRenderable> {
	
	/**
	 * Draws an item to the window.
	 */
	void render(GameContainer gc, StateBasedGame sbg, Graphics g);
	
	/**
	 * Returns the layer of this item. This method should be used when implementing Comparable.
	 */
	int getLayer();

}
