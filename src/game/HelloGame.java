 package game;

import game.config.Config;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class HelloGame extends StateBasedGame {

	//int representations for the game states.
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	
	public static final HelloGameContainer parent;
	
	static {
		HelloGameContainer app = null;
		try {
			app = HelloGameContainer.getNewInstance(new HelloGame());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		parent = app;
	}
	
	public HelloGame() {
		super("HelloGame");
	}
	
	public static void main(String[] args) {
		try{
			parent.setDisplayMode(Config.getScreenWidth(), Config.getScreenHeight(), Config.isFullscreen());
		}catch(SlickException e){
			System.out.println("Failed to initialise the display. " + e.getMessage());
			e.printStackTrace();
			return;
		}
		parent.setShowFPS(true);
		parent.setVSync(Config.isVsync());
		parent.setTargetFrameRate(Config.getNormalFPS());
		try {
			parent.start();
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
	
	@Override
	public boolean closeRequested() {
		super.closeRequested();
		try {
			getCurrentState().leave(parent, this);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return true;
	}
}
