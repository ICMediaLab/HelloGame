package sounds;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.EFXEffect;
import org.newdawn.slick.util.EFXFilter;

import entities.players.Player;

public class Sounds {
	
	private static final List<Sound> SOUNDS_PLAYING = new ArrayList<Sound>();
	
	public static void init(GameContainer gc, Player player){
		//TODO: load all the sounds for the level and player abilities
	}
	
	public static Sound loadSound(String path){
		Sound s = null;
		try {
			s = new Sound(path);
		} catch (SlickException e) {
			System.out.println("Tried to load from: " + path);
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * Stop all the sounds currently played
	 */
	
	public static void stopSounds(){
		for (int i = 0; i < SOUNDS_PLAYING.size(); i++){
			SOUNDS_PLAYING.get(i).stop();
		}
	}
	
	/**
	 * Play the sound and add it to the currently played sounds list
	 * @param sound
	 */
	
	public static void play(Sound sound){
		sound.play();
		SOUNDS_PLAYING.add(sound);
	}
	
	public static void play(Sound sound, float pitch, float volume){
		sound.play(pitch, volume);
		SOUNDS_PLAYING.add(sound);
	}
	
	public static void play(Sound sound, float pitch, float volume, EFXEffect effect, EFXFilter filter){
		sound.play(pitch, volume, effect, filter);
		SOUNDS_PLAYING.add(sound);
	}
	
	public static void playAt(Sound sound, float x, float y, float z){
		sound.playAt(x, y, z);
		SOUNDS_PLAYING.add(sound);
	}
	
	public static void playAt(Sound sound, float pitch, float volume, float x, float y, float z){
		sound.playAt(pitch, volume, x, y, z);
		SOUNDS_PLAYING.add(sound);
	}
	
	public static void playAt(Sound sound, float pitch, float volume, float x, float y, float z, EFXEffect effect, EFXFilter filter){
		sound.playAt(pitch, volume, x, y, z, effect, filter);
		SOUNDS_PLAYING.add(sound);
	}
	
	/**
	 * Check if sounds are still playing, if not remove them from the list
	 */
	
	public static void update(){
		for (int i = 0; i < SOUNDS_PLAYING.size(); i++){
			if (!SOUNDS_PLAYING.get(i).playing()){
				SOUNDS_PLAYING.remove(i);
			}
		}
	}
	
	/**
	 * Release all the sounds
	 */
	
	public static void releaseSounds(){
		for (int i = 0; i < SOUNDS_PLAYING.size(); i++){
			SOUNDS_PLAYING.get(i).release();
		}
	}

}
