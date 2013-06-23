package sounds;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import map.MapLoader;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.EFXEffect;
import org.newdawn.slick.util.EFXFilter;

public abstract class Sounds {
	
	private static final Set<Sound> SOUNDS_PLAYING = new HashSet<Sound>();
	private static Music music; 
	private static EFXEffectReverb cellReverb;
	
	private static String currentCell = "Empty";
	private static String previousCell = "Empty";
	
	public static Sound loadSound(String path){
		Sound s = null;
		try {
			s = new Sound("data/sounds/" + path);
		} catch (SlickException e) {
			System.out.println("Tried to load from: " + path);
			e.printStackTrace();
		}
		return s;
	}
	
	public static SoundGroup loadSoundGroup(String path) {
		SoundGroup s = null;
		try {
			s = new SoundGroup(path);
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
		for (Sound s : SOUNDS_PLAYING){
			if(s.playing()){
				s.stop();
			}
		}
	}
	
	/**
	 * Play the sound and add it to the currently played sounds list
	 * @param sound
	 */
	
	public static void play(Sound sound){
		play(sound, 1.0f, 1.0f);
	}
	
	public static void play(Sound sound, float pitch, float volume){
		sound.play(pitch, volume, cellReverb, null);
		SOUNDS_PLAYING.add(sound);
	}
	
	public static void play(Sound sound, float pitch, float volume, EFXEffect effect, EFXFilter filter){
		sound.play(pitch, volume, effect, filter);
		SOUNDS_PLAYING.add(sound);
	}
	
	public static void playAt(Sound sound, float x, float y, float z){
		playAt(sound, 1.0f, 1.0f, x, y, z);
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
		for (Iterator<Sound> it = SOUNDS_PLAYING.iterator(); it.hasNext();){
			Sound s = it.next();
			if (!s.playing()){
				it.remove();
			}
		}
	}
	
	public static void checkMapChange() {
		currentCell = MapLoader.getCurrentCell().toString();
		if (currentCell != previousCell) {
			if (currentCell.equals("The Forest")) {
				//System.out.println("change");
				//cellReverb = new EFXEffectReverb();
				cellReverb.setDiffusion(0);
			}
		}
		previousCell = currentCell;
	}
	
	/**
	 * Release all the sounds
	 */
	
	public static void releaseSounds(){
		update();
		stopSounds();
		for (Sound s : SOUNDS_PLAYING){
			s.release();
		}
		SOUNDS_PLAYING.clear();
	}

	public static Music getMusic() {
		return music;
	}

	public static void setMusic(Music music) {
		Sounds.music = music;
	}
	
	public static void releaseMusic() {
		music.release();
	}

	public static EFXEffectReverb getCellReverb() {
		return cellReverb;
	}

	public static void setCellReverb(EFXEffectReverb cellReverb) {
		Sounds.cellReverb = cellReverb;
	}

}
