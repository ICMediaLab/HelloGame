package entities.objects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import entities.MovingEntity;
import entities.StaticRectEntity;
import game.config.Config;

public class LeafTest extends StaticRectEntity {
	
	private static  final int LEAF_DEFAULT_LAYER = 100; 
	
	private boolean running = false;
	private final Animation moving;
	
	public LeafTest(int x, int y){
		super(x,y,4f,1f);

		//initialise graphics
		Image movingRaw = null;
		SpriteSheet movingSheet = null;
		
		try {
			movingRaw = new Image("data/images/leafTest01.png");
			movingSheet = new SpriteSheet(movingRaw, 131, 88);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		moving = new Animation(movingSheet, 150);
		moving.setAutoUpdate(false);
		moving.setLooping(false);
		moving.stop();
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		renderSprite(moving, -5, -54);
	}

	@Override
	public void update(GameContainer gc) {
		if(running){
			if(moving.isStopped()){
				moving.start();
			}else if (moving.getFrame() == moving.getFrameCount()-1) {
				moving.setCurrentFrame(0);
				moving.stop();
				running = false;
			}
			moving.update(Config.DELTA);
		}
	}

	@Override
	public void collide(MovingEntity e) {
		running = true;
	}

	@Override
	public int getLayer() {
		return LEAF_DEFAULT_LAYER;
	}
}