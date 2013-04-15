package sounds;

import java.io.File;
import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.EFXEffect;
import org.newdawn.slick.util.EFXFilter;

import utils.interval.one.Range;

public class SoundGroup {
	
	private final Sound[] sounds;
	private final Random rand = new Random();
	
	public SoundGroup(String s) throws SlickException{
		File[] files = new File("data/sounds/" + s).listFiles();
		sounds = new Sound[files.length];
		for (int i=0;i<files.length;i++){
			sounds[i] = Sounds.loadSound(s + "/" + files[i].getName());
		}
	}
	
	private Sound randomSound(){
		return sounds[rand.nextInt(sounds.length)];
	}
	
	public void playSingle(){
		Sounds.play(randomSound(), rand.nextFloat()*0.4f + 0.6f, rand.nextFloat()*0.4f + 0.6f);
	}
	
	public void playSingle(Range<Float> pitch, Range<Float> volume){
		Sounds.play(randomSound(), pitch.random(), volume.random());
	}
	
	public void playSingle(EFXEffect effect, EFXFilter filter){
		Sounds.play(randomSound(), 1.0f, 1.0f, effect, filter);
	}
	
	public void playSingle(float pitch, float volume){
		Sounds.play(randomSound(), pitch, volume);
	}
	
	public void playSingle(float pitchAverage, float pitchDeviation, float volumeAverage, float volumeDeviation){
		Sounds.play(randomSound(), pitchAverage + (rand.nextFloat() - 0.5f)*pitchDeviation, volumeAverage + (rand.nextFloat() - 0.5f)*volumeDeviation);
	}
	
	public void stopSounds(){
		for (Sound s : sounds){
			s.stop();
		}
	}
	
	public void releaseSounds(){
		stopSounds();
		for (Sound s : sounds){
			s.release();
		}
	}
	
}
