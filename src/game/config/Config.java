package game.config;

import java.awt.Dimension;

public final class Config {
	private Config(){}; //this class should not be instantiated. 

	public static boolean isVsync() {
		return MODE_VSYNC;
	}

	public static int getScreenWidth() {
		return SCREEN_DIMENSIONS.width;
	}
	
	public static int getScreenHeight() {
		return SCREEN_DIMENSIONS.height;
	}

	public static boolean isFullscreen() {
		return MODE_FULLSCREEN;
	}

	public static int getNormalFPS() {
		return NORMAL_FPS;
	}

	public static int getTileSize() {
		return TILE_SIZE;
	}

	//a bunch of configuration stuff that can probably be moved to an external file...
	private static boolean 	MODE_VSYNC	     	= true;
	private static boolean 	MODE_FULLSCREEN  	= false;
	private static Dimension	SCREEN_DIMENSIONS	= new Dimension(800, 600);
	private static int      	NORMAL_FPS	     	= 60;
	private static int      	TILE_SIZE        	= 32;
	
}
