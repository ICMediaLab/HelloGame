package game;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;

import sounds.Sounds;

public class HelloGameContainer extends AppGameContainer {
	
	private static HelloGameContainer inst = null;
	
	public static HelloGameContainer getNewInstance(Game game) throws SlickException{
		inst = new HelloGameContainer(game);
		return inst;
	}
	
	public static HelloGameContainer getNewInstance(Game game, int width, int height, boolean fullscreen) throws SlickException{
		inst = new HelloGameContainer(game,width,height,fullscreen);
		return inst;
	}
	
	public static HelloGameContainer getInstance() {
		return inst;
	}

	public boolean isRunning(){
		return running;
	}
	
	private boolean antiAlias = false;
	private boolean running = true;
	private boolean showFPS = false;
	
	private HelloGameContainer(Game game) throws SlickException {
		super(game);
		super.setShowFPS(false);
	}
	
	private HelloGameContainer(Game game, int width, int height, boolean fullscreen) throws SlickException {
		super(game,width,height,fullscreen);
		super.setShowFPS(false);
	}
	
	public void setShowFPS(boolean show) {
		showFPS = show;
	}
	
	@Override
	public boolean isShowingFPS() {
		return showFPS;
	}
	
	@Override
	public void exit() {
		running = false;
		if(game.closeRequested()){
			Sounds.releaseMusic();
			Sounds.releaseSounds();
			super.exit();
		}else{
			running = true;
		}
		
	}
	
	/**
	 * Sets graphics anti-aliasing. Note this applies to primitive drawing ONLY.
	 */
	public void setAntiAlias(boolean aa){
		antiAlias = aa;
	}
	
	public boolean isAntiAliased(){
		return antiAlias;
	}
	
	@Override
	protected void updateAndRender(int delta) throws SlickException {
		if (smoothDeltas) {
			if (getFPS() != 0) {
				delta = 1000 / getFPS();
			}
		}
		
		input.poll(width, height);
	
		Music.poll(delta);
		
		try {
			game.update(this, delta);
		} catch (Throwable e) {
			Log.error(e);
			throw new SlickException("Game.update() failure - check the game code.");
		}
		
		if (hasFocus() || getAlwaysRender()) {
			if (clearEachFrame) {
				GL.glClear(SGL.GL_COLOR_BUFFER_BIT | SGL.GL_DEPTH_BUFFER_BIT);
			}
			
			GL.glLoadIdentity();
			
			Graphics g = getGraphics();
			g.resetFont();
			g.resetLineWidth();
			g.setAntiAlias(antiAlias);
			try {
				game.render(this, g);
			} catch (Throwable e) {
				Log.error(e);
				throw new SlickException("Game.render() failure - check the game code.");
			}
			g.resetTransform();
			
			if (showFPS) {
				String fpsStr = "FPS: " + recordedFPS;
				getDefaultFont().drawString(
						getWidth()-(10 + g.getFont().getWidth(fpsStr)), 
						getHeight()-(10 + g.getFont().getHeight(fpsStr)), 
						fpsStr);
			}
			
			GL.flush();
		}
		
		if (targetFPS != -1) {
			Display.sync(targetFPS);
		}
	}
}
