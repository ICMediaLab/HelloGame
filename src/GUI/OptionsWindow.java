package GUI;

import game.config.Config;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import sounds.Sounds;
import utils.mouse.MouseContainer;

public class OptionsWindow extends AbstractWindow {
	
	private final Button cancel, ok, fullscreen, music, sound, musicUp, musicDown, soundUp, soundDown;
	
	private boolean isFullscreen = Config.isFullscreen();
	private boolean musicChanged, soundChanged, musicUpChanged, musicDownChanged, soundUpChanged, soundDownChanged = false;
	
	private Sound click;

	public OptionsWindow(GUI gui) {
		super(gui);
		cancel = new Button("Cancel", getX() + getWidth() - 90, getY() + getHeight() - 30, 80, 20, 5);
		ok = new Button("Ok", getX() + getWidth() - 190, getY() + getHeight() - 30, 80, 20, 5);
		fullscreen = new Button("Fullscreen", getX() + 20, getY() + 30, 120, 40, 5);
		music = new Button("Music", getX() + 20, getY() + 80, 120, 40, 5);
		sound = new Button("Sound", getX() + 20, getY() + 130, 120, 40, 5);
		musicUp = new Button("+", music.getX() + music.getWidth() + 10, music.getY() + 5, 30, 30, 5);
		musicDown = new Button("-", music.getX() + music.getWidth() + 40, music.getY() + 5, 30, 30, 5);
		soundUp = new Button("+", sound.getX() + sound.getWidth() + 10, sound.getY() + 5, 30, 30, 5);
		soundDown = new Button("-", sound.getX() + sound.getWidth() + 40, sound.getY() + 5, 30, 30, 5);
		
		this.click = Sounds.loadSound("gui/menu_select.wav");
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		g.fillRoundRect(x, y, width, height, 5);
		
		g.setColor(Color.black);
		g.drawString("Options", x + width/2 - g.getFont().getWidth("Options")/2, y + 10);
		
		g.setColor(Color.white);
		cancel.render(g);
		ok.render(g);
		fullscreen.render(g);
		music.render(g); g.drawString(gc.isMusicOn() ? "on" : "off", music.getX() + music.getWidth() - g.getFont().getWidth("Off"), music.getY() + ((music.getHeight() - g.getFont().getHeight("Off"))/2f));
		sound.render(g); g.drawString(gc.isSoundOn() ? "on" : "off", sound.getX() + sound.getWidth() - g.getFont().getWidth("Off"), sound.getY() + ((sound.getHeight() - g.getFont().getHeight("Off"))/2f));
		musicUp.render(g);
		musicDown.render(g); g.drawString(new Integer((int) (gc.getMusicVolume()*100)).toString(), musicDown.getX() + musicDown.getWidth() + 10, musicDown.getY() + ((musicDown.getHeight() - g.getFont().getHeight("1"))/2f));
		soundUp.render(g);
		soundDown.render(g);  g.drawString(new Integer((int) (gc.getSoundVolume()*100)).toString(), soundDown.getX() + soundDown.getWidth() + 10, soundDown.getY() + ((soundDown.getHeight() - g.getFont().getHeight("1"))/2f));
	}

	@Override
	public void update(GameContainer gc) {
		try {
			gc.setFullscreen(isFullscreen); // For now it doesn't work, don't know why...
		} catch (SlickException e) {
			e.printStackTrace();
		}
		if (musicChanged) gc.setMusicOn(!gc.isMusicOn()); 
		if (soundChanged) gc.setSoundOn(!gc.isSoundOn());
		if (musicUpChanged) gc.setMusicVolume(gc.getMusicVolume() + 0.05f);
		if (musicDownChanged) gc.setMusicVolume(gc.getMusicVolume() - 0.05f);
		if (soundUpChanged) gc.setSoundVolume(gc.getSoundVolume() + 0.05f);
		if (soundDownChanged) gc.setSoundVolume(gc.getSoundVolume() - 0.05f);
		
		isFullscreen = false;
		musicChanged = false;
		soundChanged = false;
		musicUpChanged = false;
		musicDownChanged = false;
		soundUpChanged = false;
		soundDownChanged = false;
	}
	
	@Override
	public void mouseReleased(MouseContainer mc) {
		     if(cancel.contains(mc)) 		{Sounds.play(click); getGUI().setActiveWindow(Window.MENU);}
		else if(ok.contains(mc)) 			{Sounds.play(click); getGUI().setActiveWindow(Window.MENU);}
		else if(fullscreen.contains(mc)) 	{Sounds.play(click); isFullscreen = !isFullscreen;}
		else if(music.contains(mc)) 		{Sounds.play(click); musicChanged = true;}
		else if(sound.contains(mc)) 		{Sounds.play(click); soundChanged = true;}
		else if(musicUp.contains(mc))   	{Sounds.play(click); musicUpChanged = true;}
		else if(musicDown.contains(mc)) 	{Sounds.play(click); musicDownChanged = true;}
		else if(soundUp.contains(mc))   	{Sounds.play(click); soundUpChanged = true;}
		else if(soundDown.contains(mc)) 	{Sounds.play(click); soundDownChanged = true;}
	}

}
