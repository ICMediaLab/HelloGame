package lights;

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
    
    private Image alphaMap;
    private Color sharedColor = new Color(1f, 1f, 1f, 1f);

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
        scale = scaleOrig + 1f + .5f*(float)Math.sin(delta);
    }
    
    public void render(Graphics g) {
    	//set up our alpha map for the light
		g.setDrawMode(Graphics.MODE_ALPHA_MAP);
		//clear the alpha map before we draw to it...
		g.clearAlphaMap();
		
		//centre the light
		int alphaW = (int)(alphaMap.getWidth() * scale);
		int alphaH = (int)(alphaMap.getHeight() * scale);
		int alphaX = (int)(x - alphaW/2f);
		int alphaY = (int)(y - alphaH/2f);
		
		//we apply the light alpha here; RGB will be ignored
		sharedColor.a = alpha;
		
		//draw the alpha map
		alphaMap.draw(alphaX, alphaY, alphaW, alphaH, sharedColor);
		
		//start blending in our tiles
		g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
		
		//we'll clip to the alpha rectangle, since anything outside of it will be transparent
		g.setClip(alphaX, alphaY, alphaW, alphaH);
    }
}
