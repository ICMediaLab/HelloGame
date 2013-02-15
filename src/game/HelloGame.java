package game;

import java.awt.Dimension;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class HelloGame extends StateBasedGame {

	//a bunch of configuration stuff that can probably be moved to an external file...
	public static final boolean	 MODE_VSYNC	     	= true;
	public static final boolean	 MODE_FULLSCREEN  	= false;
	public static final Dimension	 SCREEN_DIMENSIONS	= new Dimension(800, 600);
	public static final int 		 NORMAL_FPS	     	= 60;
	
	//int representations for the game states.
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	
	
  
    public HelloGame() {
        super("HelloGame");
    }
  
    public static void main(String[] args) {
    	AppGameContainer app;
    	try{
	    	app = new AppGameContainer(new HelloGame());
	    	app.setDisplayMode(SCREEN_DIMENSIONS.width, SCREEN_DIMENSIONS.height, MODE_FULLSCREEN);
    	}catch(SlickException e){
    		System.out.println("Failed to initialise the display. " + e.getMessage());
    		e.printStackTrace();
    		return;
    	}
    	app.setVSync(MODE_VSYNC);
    	app.setTargetFrameRate(NORMAL_FPS);
    	try {
			app.start();
		} catch (SlickException e) {
			System.out.println("Display successfully initialised.");
			System.out.println("Failed to initialise slick.");
			e.printStackTrace();
		}
    }
  
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new GameplayState(GAMEPLAYSTATE));
        this.addState(new MainMenuState(MAINMENUSTATE));
    }
}
