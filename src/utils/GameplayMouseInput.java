package utils;

import game.config.Config;



public abstract class GameplayMouseInput {

	private static boolean mouseDown = false;
	private static Position mousePos = new Position();
	
	private static final float TILE_SCALE = 1.0f/Config.getTileSize();
	
	public static boolean isMouseDown(){
		return mouseDown;
	}
	
	public static Position getMousePosition(){
		return mousePos;
	}
	
	public static void mouseDragged(int oldx, int oldy, int newx, int newy) {
		mouseDown = true;
		mousePos.set(newx, newy);
		mousePos.scale(TILE_SCALE);
		mousePos.translate(1, 1);
	}

	public static void mouseMoved(int oldx, int oldy, int newx, int newy) {
		mouseDown = false;
		mousePos.set(newx, newy);
		mousePos.scale(TILE_SCALE);
		mousePos.translate(1, 1);
	}

	public static void mousePressed(int button, int x, int y) {
		mouseDown = true;
		mousePos.set(x, y);
		mousePos.scale(TILE_SCALE);
		mousePos.translate(1, 1);
	}

	public static void mouseReleased(int button, int x, int y) {
		mouseDown = false;
		mousePos.set(x, y);
		mousePos.scale(TILE_SCALE);
		mousePos.translate(1, 1);
	}
}
