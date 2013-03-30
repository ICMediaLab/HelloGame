package utils.mouse;

import java.awt.Point;

import utils.Position;

public class MouseContainer {

	private final MouseButton button;
	private final Point p;
	
	public MouseContainer(int button, int x, int y) {
		this.button = MouseButton.getButton(button);
		this.p = new Point(x, y);
	}
	
	public Position getPosition(){
		return new Position(p);
	}
	
	public int getX(){
		return p.x;
	}
	
	public int getY(){
		return p.y;
	}
	
	public MouseButton getButton(){
		return button;
	}
	
}
