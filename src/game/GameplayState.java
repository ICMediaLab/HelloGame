package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Input;

import entities.players.Player;
import game.config.Config;
import utils.MapLoader;
import utils.Tile;

public class GameplayState extends BasicGameState {
	
	private final int stateID;
	private MapLoader maps;
	private Tile[][] properties;
	private Player player;
	  
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
		maps = new MapLoader(1,1, Config.getTileSize());
		maps.loadMap("data/grassmap.tmx",0,0);
		properties = maps.getProperties(0, 0);
		player = new Player(new Rectangle(32,32,32,32), 100);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		maps.getMap(0,0).render(0,0);
		player.render();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE)){
			player.stop_sounds();
			gc.exit();
		}
		player.update(input, properties, delta);

	}

	
}
