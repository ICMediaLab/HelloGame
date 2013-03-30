package game;

import game.config.Config;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.state.BasicGameState;

import utils.Position;
import utils.mouse.MouseContainer;
import utils.mouse.MouseListener;

public abstract class MouseCapture extends BasicGameState {

	private static final Set<MouseListener> listeners = new HashSet<MouseListener>();
	
	private static final Position mousePosition = new Position();
	
	public static void addListener(MouseListener listener){
		listeners.add(listener);
	}
	
	public static boolean removeListener(MouseListener listener){
		return listeners.remove(listener);
	}
	
	@Override
	public final void mouseClicked(int button, int x, int y, int clickCount) {
		mousePosition.set(x, y);
		for(MouseListener l : listeners){
			l.mouseClicked(new MouseContainer(button, x, y));
		}
	}
	
	@Override
	public final void mouseDragged(int oldx, int oldy, int newx, int newy) {
		mousePosition.set(newx, newy);
		for(MouseListener l : listeners){
			l.mouseDragged(new Position(newx,newy));
		}
	}
	
	@Override
	public final void mouseMoved(int oldx, int oldy, int newx, int newy) {
		mousePosition.set(newx, newy);
		for(MouseListener l : listeners){
			l.mouseMoved(new Position(newx,newy));
		}
	}
	
	@Override
	public final void mousePressed(int button, int x, int y) {
		mousePosition.set(x, y);
		for(MouseListener l : listeners){
			l.mousePressed(new MouseContainer(button, x, y));
		}
	}
	
	@Override
	public final void mouseReleased(int button, int x, int y) {
		mousePosition.set(x, y);
		for(MouseListener l : listeners){
			l.mouseReleased(new MouseContainer(button, x, y));
		}
	}
	
	@Override
	public final void mouseWheelMoved(int amount) {
		for(MouseListener l : listeners){
			l.mouseWheelMoved(amount);
		}
	}

	public static Position getMousePositionAbsolute() {
		return mousePosition.clone();
	}
	
	public static Position getMousePositionRelative() {
		Position ret = mousePosition.clone();
		ret.scale(1.0f/Config.getTileSize());
		ret.translate(1, 1);
		return ret;
	}
}	
