package GUI;

import entities.objects.LeafTest;
import game.config.Config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import sounds.Sounds;
import utils.mouse.MouseContainer;

public class OptionsWindow extends AbstractWindow {
	
	private static final long serialVersionUID = -6134059150113164930L;
	
	private transient final Button cancel, ok, fullscreen, music, sound, musicUp, musicDown, soundUp, soundDown;
	
	private transient boolean isFullscreen = Config.isFullscreen();
	private transient boolean musicChanged = false, soundChanged = false, musicUpChanged = false, musicDownChanged = false, soundUpChanged = false, soundDownChanged = false;
	
	private transient final Sound click;
	
	public OptionsWindow(GUI gui) {
		super(gui);
		cancel = getCancelButton();
		ok = getOkButton();
		fullscreen = getFullScreenButton();
		music = getMusicButton();
		sound = getSoundButton();
		musicUp = getMusicUpButton();
		musicDown = getMusicDownButton();
		soundUp = getSoundUpButton();
		soundDown = getSoundDownButton();
		
		click = getClickSound();
	}
	
	private Sound getClickSound() {
		return Sounds.loadSound("gui/menu_select.wav");
	}
	
	private Button getOkButton(){
		return new Button("Ok", getX() + getWidth() - 190, getY() + getHeight() - 30, 80, 20, 5);
	}
	
	private Button getCancelButton(){
		return new Button("Cancel", getX() + getWidth() - 90, getY() + getHeight() - 30, 80, 20, 5);
	}
	
	private Button getFullScreenButton(){
		return new Button("Fullscreen", getX() + 20, getY() + 30, 120, 40, 5);
	}
	
	private Button getMusicButton(){
		return new Button("Music", getX() + 20, getY() + 80, 120, 40, 5);
	}
	
	private Button getSoundButton(){
		return new Button("Sound", getX() + 20, getY() + 130, 120, 40, 5);
	}
	
	private Button getMusicUpButton(){
		return new Button("+", music.getX() + music.getWidth() + 10, music.getY() + 5, 30, 30, 5);
	}
	
	private Button getMusicDownButton(){
		return new Button("-", music.getX() + music.getWidth() + 40, music.getY() + 5, 30, 30, 5);
	}
	
	private Button getSoundUpButton(){
		return new Button("+", sound.getX() + sound.getWidth() + 10, sound.getY() + 5, 30, 30, 5);
	}
	
	private Button getSoundDownButton(){
		return new Button("-", sound.getX() + sound.getWidth() + 40, sound.getY() + 5, 30, 30, 5);
	}
	
	/**
	 * Serialisation loading method for {@link LeafTest}
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		in.defaultReadObject();
		Field f = getClass().getDeclaredField("click");
		f.setAccessible(true);
		f.set(this, getClickSound());
		isFullscreen = Config.isFullscreen();
		//oh god everything :/
		f = getClass().getDeclaredField("ok");
		f.setAccessible(true);
		f.set(this, getOkButton());
		f = getClass().getDeclaredField("cancel");
		f.setAccessible(true);
		f.set(this, getCancelButton());
		f = getClass().getDeclaredField("fullscreen");
		f.setAccessible(true);
		f.set(this, getFullScreenButton());
		f = getClass().getDeclaredField("music");
		f.setAccessible(true);
		f.set(this, getMusicButton());
		f = getClass().getDeclaredField("sound");
		f.setAccessible(true);
		f.set(this, getSoundButton());
		f = getClass().getDeclaredField("musicUp");
		f.setAccessible(true);
		f.set(this, getMusicUpButton());
		f = getClass().getDeclaredField("musicDown");
		f.setAccessible(true);
		f.set(this, getMusicDownButton());
		f = getClass().getDeclaredField("soundDown");
		f.setAccessible(true);
		f.set(this, getSoundDownButton());
		f = getClass().getDeclaredField("soundUp");
		f.setAccessible(true);
		f.set(this, getSoundUpButton());
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
