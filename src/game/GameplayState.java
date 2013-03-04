package game;

import map.Cell;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import sounds.SoundGroup;
import sounds.Sounds;
import utils.EnemyLoader;
import utils.MapLoader;
import entities.enemies.Enemy;
import entities.players.Player;

public class GameplayState extends BasicGameState {
	
	private final int stateID;
	private Cell currentCell;
	private Player player;
	private Music music;
	SoundGroup footsteps;
	  
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
		//map loading goes here. Needs a better home
		//method needed to load all maps into their correct index in the array
		MapLoader.setDimensions(2,3);
		MapLoader.loadMap("data/testmap.tmx", 0, 0);
		MapLoader.loadMap("data/testmap2.tmx", 1, 0);
		MapLoader.loadMap("data/testmap3.tmx", 0, 1);
		MapLoader.loadMap("data/testmap4.tmx", 0, 2);
		MapLoader.loadMap("data/testmap5.tmx", 1, 2);
		MapLoader.loadMap("data/testmap6.tmx", 1, 1);
		//set initial map
		player = new Player(2,2);
		currentCell = MapLoader.setCurrentCell(player,0,0);
		//create player
		
		currentCell.addEntity(player);
		EnemyLoader.loadEnemies("data/enemydata.xml");
		currentCell.addEntity(Enemy.getNewEnemy(currentCell,"rawr",1,1));
		
		//audio
		music = new Music("data/sounds/theme.ogg", true);
		music.play(1.0f, 0.05f);
		footsteps = new SoundGroup("player/footsteps/gravel");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {  
		currentCell.render();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		//update map
		currentCell = MapLoader.getCurrentCell();
		
		//check input
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE)){
			music.release();
			Sounds.releaseSounds();
			gc.exit();
		}
		
		//update player
		player.update(input);
		
		//update sounds
		footsteps.playRandom(gc, player, 300);
		Sounds.update();
	}

	
}
