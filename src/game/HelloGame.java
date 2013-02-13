package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class HelloGame extends StateBasedGame {

	public static final int MAINMENUSTATE          = 0;
    public static final int GAMEPLAYSTATE          = 1;
  
    public HelloGame()
    {
        super("HelloGame");
    }
  
    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new HelloGame());
         app.setDisplayMode(800, 600, false);
         app.setVSync(true);
         app.start();
    }
  
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
    }
}
