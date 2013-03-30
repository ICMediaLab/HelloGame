package utils.mouse;

import utils.Position;

public interface MouseListener {

	/**
	 * Indicates that the mouse has been dragged to position p.
	 */
	void mouseDragged(Position p);
	
	/**
	 * Indicates that the mouse has been moved to position p.
	 */
	void mouseMoved(Position p);
	
	/**
	 * Indicates that the mouse has been pressed with identity given.
	 */
	void mousePressed(MouseContainer mc);
	
	/**
	 * Indicates that the mouse has been released with identity given.
	 */
	void mouseReleased(MouseContainer mc);
	
	/**
	 * Indicates that the mouse has been clicked with identity given.<br />
	 * Note that a click should also trigger and mousePressed and mouseReleased call.
	 */
	void mouseClicked(MouseContainer mc);
	
	/**
	 * Indicates that the mouse wheel has been rotated by a given amount.
	 */
	void mouseWheelMoved(int amount);
	
}
