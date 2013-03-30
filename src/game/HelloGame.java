 package game;

import game.config.Config;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class HelloGame extends StateBasedGame {

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
	    	app.setDisplayMode(Config.getScreenWidth(), Config.getScreenHeight(), Config.isFullscreen());
    	}catch(SlickException e){
    		System.out.println("Failed to initialise the display. " + e.getMessage());
    		e.printStackTrace();
    		return;
    	}
    	app.setVSync(Config.isVsync());
    	app.setTargetFrameRate(Config.getNormalFPS());
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
    	this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
    }
}
