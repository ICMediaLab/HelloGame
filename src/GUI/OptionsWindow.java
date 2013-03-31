package GUI;

import game.config.Config;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.mouse.MouseContainer;

public class OptionsWindow extends AbstractWindow {
	
	private final Button cancel;
	private final Button ok;
	private final Button fullscreen;
	private final Button music;
	private final Button sound;
	
	private boolean isFullscreen = Config.isFullscreen();
	private boolean isMusic = true;
	private boolean isSound = true;

	public OptionsWindow(GUI gui) {
		super(gui);
		cancel = new Button("Cancel", getX() + getWidth() - 90, getY() + getHeight() - 30, 80, 20, 5);
		ok = new Button("Ok", getX() + getWidth() - 190, getY() + getHeight() - 30, 80, 20, 5);
		fullscreen = new Button("Fullscreen", getX() + 20, getY() + 30, 120, 40, 5);
		music = new Button("Music", getX() + 20, getY() + 80, 120, 40, 5);
		sound = new Button("Sound", getX() + 20, getY() + 130, 120, 40, 5);
	}

	@Override
	public void render(Graphics gr) {
		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
		gr.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		gr.fillRoundRect(x, y, width, height, 5);
		
		gr.setColor(Color.black);
		gr.drawString("Options", x + width/2 - gr.getFont().getWidth("Options")/2, y + 10);
		
		cancel.render(gr, Color.darkGray, Color.black);
		ok.render(gr, Color.darkGray, Color.black);
		fullscreen.render(gr, Color.darkGray, Color.black);
		music.render(gr, Color.darkGray, Color.black);
		sound.render(gr, Color.darkGray, Color.black);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
		try {
			gc.setFullscreen(isFullscreen); // For now it doesn't work, don't know why...
		} catch (SlickException e) {
			e.printStackTrace();
			isFullscreen = false;
		}
		gc.setMusicOn(isMusic);
		gc.setSoundOn(isSound);
	}
	
	@Override
	public void mouseReleased(MouseContainer mc) {
		if(cancel.contains(mc.getX(), mc.getY())){
			getGUI().setActiveWindow(Window.MENU);
		} else if(ok.contains(mc.getX(), mc.getY())){
			getGUI().setActiveWindow(Window.MENU);
		} else if(fullscreen.contains(mc.getX(), mc.getY())){
			isFullscreen = isFullscreen ? false : true;
		} else if(music.contains(mc.getX(), mc.getY())){
			isMusic = isMusic ? false : true;
		} else if(sound.contains(mc.getX(), mc.getY())){
			isSound = isSound ? false : true;
		}
		
		
	}

}
