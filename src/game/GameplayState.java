package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Input;
import utils.MapLoader;

public class GameplayState extends BasicGameState {
	
	int stateID = -1;
	MapLoader maps;
	  
    GameplayState(int stateID) {
       this.stateID = stateID;
    }
  
    @Override
    public int getID() {
        return stateID;
    }

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		maps = new MapLoader(1,1);
		maps.loadMap("data/grassmap.tmx",0,0);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		maps.getMap(0,0).render(0,0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
				Input input = gc.getInput();
				if (input.isKeyDown(Input.KEY_ESCAPE)){
					gc.exit();
				}
		// TODO Auto-generated method stub

	}

	
}
