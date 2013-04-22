package GUI;

import java.io.Serializable;

import game.MouseCapture;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import utils.Renderable;
import utils.Updatable;

public class GUI implements Updatable, Renderable, Serializable {
	
	private static final long serialVersionUID = -7417513297336661731L;
	
	private Window activeWindow = null;
	private Graphics graphics;
	
	public GUI(Graphics g) {
		this.graphics = g;
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
			getActiveWindow().render(gc,g);
		}
	}
	
	public void update(GameContainer gc) {
		if(anyWindowOpen()){
			getActiveWindow().update(gc);
		}
		Input input = gc.getInput();
		
		for(Window w : Window.values()){
			if(w.isWindowKeyPressed(input)){
				setActiveWindow(w);
			}
		}
	}

	public Graphics getGraphics() {
		return graphics;
	}
}
