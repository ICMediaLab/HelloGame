package GUI;

import game.MouseCapture;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class GUI {

	private Window activeWindow = null;

	public GUI() {

	}
	
	void setActiveWindow(Window window) {
		MouseCapture.removeListener(getActiveWindow());
		if(window == activeWindow){
			activeWindow = null;
		}else{
			activeWindow = window;
			MouseCapture.addListener(getActiveWindow());
		}
	}

	public AbstractWindow getActiveWindow() {
		return activeWindow == null ? null : activeWindow.getInstance(this);
	}
	
	public void closeWindow(){
		setActiveWindow(null);
	}
	
	public boolean anyWindowOpen() {
		return getActiveWindow() != null;
	}
	
	public void render(GameContainer gc, Graphics g) {
		if(anyWindowOpen()){
			getActiveWindow().render(g);
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
		if(anyWindowOpen()){
			getActiveWindow().update(gc, sbg, delta);
		}
		Input input = gc.getInput();
		
		for(Window w : Window.values()){
			if(w.isWindowKeyPressed(input)){
				setActiveWindow(w);
			}
		}
	}
}
