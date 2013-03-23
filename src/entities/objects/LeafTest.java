package entities.objects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

import entities.AbstractEntity;
import entities.Entity;
import entities.StaticEntity;
import game.config.Config;

public class LeafTest extends StaticEntity {
	
	private static  final int LEAF_DEFAULT_LAYER = 100; 
	
	private final Animation moving; //,stopped; //removed this as it wasn't being used.
	//private Animation sprite; //likewise 
	
	public LeafTest(int x, int y){
		super(x,y,4f,1f);

		//initialise graphics
		//Image staticRaw = null; //unused
		Image movingRaw = null;
		//SpriteSheet staticSheet = null; //unused
		SpriteSheet movingSheet = null;
		
		try {
			//staticRaw = new Image("data/images/leafTest01.png"); //unused
			movingRaw = new Image("data/images/leafTest01.png");
			//staticSheet = new SpriteSheet(staticRaw, 131, 88); //unused
			movingSheet = new SpriteSheet(movingRaw, 131, 88);
			
		} catch (SlickException e) {
			//do shit all
		}

		moving = new Animation(movingSheet, 150);
		//moving.setAutoUpdate(false);
		//stopped = new Animation(staticSheet, 0, 0, 0, 0, true, 1000, false); //unused
		//sprite = stopped;
		moving.stop();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, org.newdawn.slick.Graphics g) {
		moving.draw(((getX()-1)*Config.getTileSize()-5), ((getY()-1)*Config.getTileSize())-54);
	}

	@Override
	public AbstractEntity clone() {
		return this;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if (!moving.isStopped()){
			if (moving.getFrame() == 0) {
				moving.stop();
			}
		}
	}

	@Override
	public void collide(Entity e) {
		moving.start();
	}

	@Override
	public int getLayer() {
		return LEAF_DEFAULT_LAYER;
	}
}