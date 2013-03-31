package game;

import map.Cell;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import sounds.Sounds;
import utils.MapLoader;
import utils.npeloader.EnemyLoader;
import GUI.GUI;
import GUI.TextField;
import entities.players.Player;

public class GameplayState extends MouseCapture {
	
	private final int stateID;
	private Cell currentCell;
	private Player player;
	private Music music;
	private static World world = new World(new Vec2(0,  9.8f), false);
	private GUI gui = new GUI();
	private TextField text;
	  
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
		music = new Music("data/sounds/RedCurtain.ogg", true);
		music.play(1.0f, 0.15f);	
		
		text = new TextField("'Tis a silly place", 21*32, 15*32, 5, 100, player, 0, -50, Color.transparent, Color.white, 50, 50);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		currentCell.render(gc, g);
		//renderLighting(gc,g);
		gui.render(gc, g);
		text.render(g);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if(HelloGameContainer.isRunning()){
			//update map
			currentCell = MapLoader.getCurrentCell();
			//check input
			Input input = gc.getInput();
			
			if(gui.anyWindowOpen()){
				if (input.isKeyPressed(Input.KEY_ESCAPE)){
					gui.closeWindow();
				}
			}else {
				currentCell.updateEntities(gc);
				world.step(delta/1000f, 8, 3);
			}
			gui.update(gc);
		}
	}
	
	@Override
	public void leave(GameContainer gc, StateBasedGame game)
			throws SlickException {
		super.leave(gc, game);
		if(!HelloGameContainer.isRunning()){
			gc.sleep(300);
			music.release();
			Sounds.releaseSounds();
		}
	}
	
	public static World getWorld() {
		return world;
	}
}
