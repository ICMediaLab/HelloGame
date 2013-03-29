package game;

import map.Cell;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import sounds.Sounds;
import utils.ContactListenerSlick;
import utils.EnemyLoader;
import utils.GameplayMouseInput;
import utils.MapLoader;
import entities.objects.Bricks;
import entities.players.Player;

public class GameplayState extends BasicGameState {
	
	private final int stateID;
	private Cell currentCell;
	private Player player;
	private Music music;
	private static World world = new World(new Vec2(0,  2*9.8f), false);
	Image image;
	  
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
		//load enemy data
		EnemyLoader.loadEnemies("data/enemydata.xml");
		//map loading goes here. Needs a better home
		//method needed to load all maps into their correct index in the array
		MapLoader.setDimensions(3,2);
		MapLoader.loadMap("data/JezMap01.tmx", 0, 0);
		MapLoader.loadMap("data/JezMap02.tmx", 1, 0);
		MapLoader.loadMap("data/JezMap03.tmx", 2, 0);
		MapLoader.loadMap("data/JezMap13.tmx", 2, 1);
		//set initial map
		player = new Player(2,2);
		currentCell = MapLoader.setCurrentCell(player,0,0);
		//create player
		currentCell.addEntity(player);
		
		//audio
		music = new Music("data/sounds/RedCurtain.ogg", true);
		music.loop(1.0f, 0.4f);
		
		world.setContactListener(new ContactListenerSlick());
		
		currentCell.addEntity(new Bricks(13, 3));
		currentCell.addEntity(new Bricks(5, 3));
		currentCell.addEntity(new Bricks(10, 5));
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {  
		currentCell.render(gc, sbg, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		//update map
		currentCell = MapLoader.getCurrentCell();
		//check input
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE)){
			gc.sleep(300);
			music.release();
			Sounds.releaseSounds();
			gc.exit();
		}
		
		long start = System.currentTimeMillis();
		currentCell.updateEntities(gc, sbg, delta);
		long start2 = System.currentTimeMillis();
		world.step(1/60f, 1, 1);
		long start3 = System.currentTimeMillis();
		System.out.println("update: " + (start2 - start) + "\t step: " + (start3 - start2));
	}

	public static World getWorld() {
		return world;
	}
	
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		GameplayMouseInput.mouseDragged(oldx, oldy, newx, newy);
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		GameplayMouseInput.mouseMoved(oldx, oldy, newx, newy);
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		GameplayMouseInput.mousePressed(button, x, y);
	}
	
	@Override
	public void mouseReleased(int button, int x, int y) {
		GameplayMouseInput.mouseReleased(button, x, y);
	}
}
