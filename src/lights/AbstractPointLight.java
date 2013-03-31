package lights;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class AbstractPointLight extends AbstractLight {
	
	private static final float DEFAULT_SCALE = 5f;
	private static final Image alphaMap;
	
	static {
		Image map = null;
		try {
			map = new Image("data/alpha_map.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		alphaMap = map;
	}

	private double updateTime = 0;
	private float scale;
	
	public AbstractPointLight() {
		this(DEFAULT_SCALE);
	}
	
	public AbstractPointLight(float scale){
		super(alphaMap);
		this.scale = scale;
	}
	
	public AbstractPointLight(float scale, Color tint){
		super(alphaMap,tint);
		this.scale = scale;
	}
	
	public void update(GameContainer gc) {
        //effect: scale the light slowly using a sin func
		scale += 0.01f*(float)Math.sin(updateTime += 0.05);
    }
	
	public void render(GameContainer gc, Graphics g) {
		super.renderCentre(scale, getX(), getY());
    }
	
	public abstract float getX();
	public abstract float getY();
}
