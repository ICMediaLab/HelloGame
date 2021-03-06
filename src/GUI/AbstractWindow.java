package GUI;


import java.io.Serializable;

import game.config.Config;
import utils.Dimension;
import utils.Position;
import utils.Renderable;
import utils.Updatable;
import utils.mouse.MouseAdapter;


public abstract class AbstractWindow extends MouseAdapter implements Updatable, Renderable, Serializable {
	
	private static final long serialVersionUID = -3588688166743379092L;
	
	private static final Position NORMAL_TOPLEFT = new Position(Config.getScreenWidth()*0.25f,Config.getScreenHeight()*0.25f);
	private static final Dimension NORMAL_DIMENSIONS = new Dimension(Config.getScreenWidth()*0.5f,Config.getScreenHeight()*0.5f);
	
	private final Position topleft;
	private final Dimension dimension;
	private final GUI parent;
	
	public AbstractWindow(GUI gui){
		this(gui,NORMAL_TOPLEFT,NORMAL_DIMENSIONS);
	}
	
	public AbstractWindow(GUI gui, float x, float y, float width, float height) {
		this(gui, new Position(x,y), new Dimension(width, height));
	}
	public AbstractWindow(GUI gui, Position topleft, Dimension dimension) {
		this.parent = gui;
		this.topleft = topleft;
		this.dimension = dimension;
	}

	public float getX() {
		return topleft.getX();
	}
	public float getY() {
		return topleft.getY();
	}
	public float getWidth() {
		return dimension.getWidth();
	}
	public float getHeight() {
		return dimension.getHeight();
	}
	
	public GUI getGUI(){
		return parent;
	}
}
