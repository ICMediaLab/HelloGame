package utils.mouse;

import utils.Position;

/**
 * An abstract instantiation of {@link MouseListener} with blank method 
 * bodies to remove the requirement of providing implementations for 
 * all methods if only one or a few are required.
 */
public abstract class MouseAdapter implements MouseListener {

	@Override
	public void mouseDragged(Position p) { }

	@Override
	public void mouseMoved(Position p) { }

	@Override
	public void mousePressed(MouseContainer mc) { }

	@Override
	public void mouseReleased(MouseContainer mc) { }

	@Override
	public void mouseClicked(MouseContainer mc) { }

	@Override
	public void mouseWheelMoved(int amount) { }

}
