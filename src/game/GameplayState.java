package game;

import java.io.Serializable;

import map.Cell;
import map.MapLoader;
import notify.Notification;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import sounds.EFXEffectReverb;
import sounds.Sounds;
import utils.npeloader.EnemyLoader;
import utils.npeloader.NPCLoader;
import GUI.GUI;
import entities.players.Player;
import game.config.Config;

public class GameplayState extends MouseCapture implements Serializable {
	
	private static final long serialVersionUID = -3988950280670496548L;
	
	public static boolean drawDebug = false;
	
	private final int stateID;
	private Cell currentCell;
	private Player player;
	private GUI gui;
	
	GameplayState(int stateID) {
		this.stateID = stateID;
	}
	
	private GUI getGUI(Graphics g){
		return new GUI(g);
	}
	
	@Override
	public int getID() {
		return stateID;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//load enemy/npc data
		new EnemyLoader().load("data/enemydata.xml");
		new NPCLoader().load("data/npcdata.xml");
		
		//load and initialise maps
		MapLoader.loadAllMaps("data/layout.xml");
		
		//set initial map and player
		player = Player.getPlayerInstance(20f, 2f);
		currentCell = MapLoader.setInitialCell(player);
		
		gui = getGUI(gc.getGraphics());
		
		//audio
		Sounds.setMusic(new Music("data/sounds/RedCurtain.ogg", true));
		gc.setMusicVolume(0.5f);
		Sounds.setCellReverb(new EFXEffectReverb());
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		currentCell.render(gc, g);
		Notification.render(gc, g);
		gui.render(gc, g);
		
		if (drawDebug) {
			drawDebug(g);
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Notification.update(gc);
		if(HelloGameContainer.getInstance().isRunning()){
			//update map
			currentCell = MapLoader.getCurrentCell();
			//check input
			Input input = gc.getInput();
			
			if(gui.anyWindowOpen()){
				if (input.isKeyPressed(Input.KEY_ESCAPE)){
					gui.closeWindow();
				}
			}else {
				currentCell.update(gc);
			}
			gui.update(gc);
			Sounds.checkMapChange();
			
			if (input.isKeyPressed(Input.KEY_G)) {
				drawDebug = !drawDebug;
			}
		}
	}
	
	public void drawDebug(Graphics g) {
		g.setColor(Color.black);
		for (int i = 0; i < Config.getScreenHeight()/Config.getTileSize(); i++) {
			g.drawLine(0, i*Config.getTileSize(), Config.getScreenWidth(), i*Config.getTileSize());
		}
		for (int j = 0; j < Config.getScreenWidth()/Config.getTileSize(); j++) {
			g.drawLine(j*Config.getTileSize(), 0, j*Config.getTileSize(), Config.getScreenHeight());
		}
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
		Sounds.getMusic().loop(1, 0.5f);
	}
	
}
