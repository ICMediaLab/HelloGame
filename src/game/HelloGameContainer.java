package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

import sounds.Sounds;

public class HelloGameContainer extends AppGameContainer {

	private static boolean running = true;
	
	public static boolean isRunning(){
		return running;
	}
	
	public HelloGameContainer(Game game) throws SlickException {
		super(game);
	}
	
	public HelloGameContainer(Game game, int width, int height, boolean fullscreen) throws SlickException {
		super(game,width,height,fullscreen);
	}
	
	@Override
	public void exit() {
		running = false;
		game.closeRequested();
		Sounds.releaseMusic();
		Sounds.releaseSounds();
		super.exit();
	}
}
