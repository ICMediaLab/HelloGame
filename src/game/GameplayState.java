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
import utils.GameplayMouseInput;
import utils.MapLoader;
import utils.npeloader.EnemyLoader;
import entities.objects.Bricks;
import entities.objects.CirclePhysics;
import entities.objects.FloorPhysics;
import entities.players.Player;

public class GameplayState extends BasicGameState {
	
	private final int stateID;
	private Cell currentCell;
	private Player player;
	private Music music;
	private static World world = new World(new Vec2(0,  9.8f), false);
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
		new EnemyLoader().load("data/enemydata.xml");
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
		music = new Music("data/sounds/theme.ogg", true);
		music.play(1.0f, 0.07f);
		
		currentCell.addEntity(new Bricks(3, 3, world));
		currentCell.addEntity(new Bricks(5, 3, world));
		currentCell.addEntity(new Bricks(10, 5, world));
		currentCell.addEntity(new FloorPhysics(1, 8, 1, 1, world));
		currentCell.addEntity(new FloorPhysics(2, 9, 2, 3, world));
		currentCell.addEntity(new FloorPhysics(4, 12, 3, 1, world));
		currentCell.addEntity(new FloorPhysics(7, 13, 1, 1, world));
		currentCell.addEntity(new FloorPhysics(8, 14, 3, 1, world));
		currentCell.addEntity(new FloorPhysics(11, 15, 4, 1, world));
		currentCell.addEntity(new FloorPhysics(16, 15, 1, 1, world));
		currentCell.addEntity(new FloorPhysics(15, 16, 4, 1, world));
		currentCell.addEntity(new FloorPhysics(19, 17, 5, 1, world));
		currentCell.addEntity(new FloorPhysics(24, 18, 3, 1, world));
		currentCell.addEntity(new FloorPhysics(27, 17, 2, 1, world));
		currentCell.addEntity(new FloorPhysics(29, 16, 3, 1, world));
		currentCell.addEntity(new FloorPhysics(32, 15, 1, 1, world));
		currentCell.addEntity(new CirclePhysics(5, 1, 0.5f, world));
		currentCell.addEntity(new CirclePhysics(5, 3, 0.5f, world));
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
		
		currentCell.updateEntities(gc, sbg, delta);
		world.step(delta/1000f, 8, 3);
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
