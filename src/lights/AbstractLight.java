package lights;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class AbstractLight implements Light {
	
	private static final float DEFAULT_SCALE = 5f;
	private static final Color DEFAULT_FILTER = Color.white;
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

	private final Color tint;
	
	private double updateTime = 0;
	private float scale;
	
	public AbstractLight() {
		this(DEFAULT_SCALE);
	}
	
	public AbstractLight(float scale){
		this(scale,DEFAULT_FILTER);
	}
	
	public AbstractLight(float scale, Color tint){
		this.scale = scale;
		this.tint = tint;
	}
	
	public void update(GameContainer gc) {
        //effect: scale the light slowly using a sin func
		scale += 0.01f*(float)Math.sin(updateTime += 0.05);
    }
	
	public static void renderPre(Graphics g){
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    }
    
    public static void renderPost(Graphics g){
    	g.setDrawMode(Graphics.MODE_NORMAL);
    }
    
    public void render(GameContainer gc, Graphics g) {
    	//set up blend functionality
        //pre: GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		
		//centre the light
		float alphaW = alphaMap.getWidth() * scale;
		float alphaH = alphaMap.getHeight() * scale;
		float alphaX = getX() - alphaW/2f;
		float alphaY = getY() - alphaH/2f;
		
		//draw the alpha map
		alphaMap.draw(alphaX, alphaY, alphaW, alphaH, tint);
		
		//return to normal rendering mode for any remaining renders
		//post: g.setDrawMode(Graphics.MODE_NORMAL);
    }
}
