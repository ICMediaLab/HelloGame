package lights;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class AbstractLight extends Image implements Light {
	
	private static final Color DEFAULT_TINT = Color.white;
	
	private final Image i;
	private final Color tint;
	
	public AbstractLight(Image i){
		this(i,DEFAULT_TINT);
	}
	
	public AbstractLight(Image i, Color tint) {
		this.i = i;
		this.tint = tint;
	}

	public static void renderPre(Graphics g){
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	}

	public static void renderPost(Graphics g){
		g.setDrawMode(Graphics.MODE_NORMAL);
	}
	
	protected void renderAbsolute(float x, float y, float width, float height){
		i.draw(x,y,width,height,tint);
	}
	
	protected void renderCentre(float scale, float x, float y){
		//centre the light
		float alphaW = i.getWidth() * scale;
		float alphaH = i.getHeight() * scale;
		float alphaX = x - alphaW/2f;
		float alphaY = y - alphaH/2f;
		
		renderAbsolute(alphaX, alphaY, alphaW, alphaH);
	}
}
