package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import sounds.Sounds;

public class MainMenuState extends BasicGameState {

	private static final String enterStartStr = "Press enter to start!";
	private final int stateID;
	private Sound start;
	  
    MainMenuState( int stateID ) 
    {
       this.stateID = stateID;
       this.start = Sounds.loadSound("gui/start_game.wav");
    }
  
    @Override
    public int getID() {
        return stateID;
    }
    
    @Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbc, Graphics g)
			throws SlickException {
		g.drawString(enterStartStr, (gc.getWidth() - g.getFont().getWidth(enterStartStr))/2, 350);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if(gc.getInput().isKeyDown(Input.KEY_ENTER) || gc.getInput().isKeyDown(Input.KEY_NUMPADENTER)){
			sbg.enterState(HelloGame.GAMEPLAYSTATE);
		}
	}
	
	@Override
	public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
		super.leave(gc, game);
		Sounds.play(start, 1, 0.5f);
		gc.sleep(1000);
	}

}
