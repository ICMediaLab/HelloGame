package lights;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class PointLight {
    /** The position of the light */
    float x, y;
    /** The RGB tint of the light, alpha is ignored */
    Color tint; 
    /** The alpha value of the light, default 1.0 (100%) */
    float alpha;
    /** The amount to scale the light (use 1.0 for default size). */
    private float scale;
    //original scale
    private float scaleOrig;
    
    private double updateTime = 0;
    
    private Image alphaMap;

    public PointLight(float x, float y, float scale, Color tint) {
        this.x = x;
        this.y = y;
        this.scale = scaleOrig = scale;
        this.alpha = 1f;
        this.tint = tint;
        try {
			this.alphaMap = new Image("data/alpha_map.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public PointLight(float x, float y, float scale) {
        this(x, y, scale, Color.white);
    }

    public void update(GameContainer gc, StateBasedGame sbg, float delta) {
        //effect: scale the light slowly using a sin func
        scale += 0.01f*(float)Math.sin(updateTime += 0.05);
    }
    
    public void render(GameContainer gc, Graphics g) {
    	//set up blend functionality
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		
		//centre the light
		int alphaW = (int)(alphaMap.getWidth() * scale);
		int alphaH = (int)(alphaMap.getHeight() * scale);
		int alphaX = (int)(x - alphaW/2f);
		int alphaY = (int)(y - alphaH/2f);
		
		//draw the alpha map
		alphaMap.draw(alphaX, alphaY, alphaW, alphaH, tint);
		
		//return to normal rendering mode for any remaining renders
		g.setDrawMode(Graphics.MODE_NORMAL);
    }
    
    public void setPosition(float x, float y) {
    	this.x = x;
    	this.y = y;
    }
}
