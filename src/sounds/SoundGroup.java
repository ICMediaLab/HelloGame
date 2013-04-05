package sounds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.EFXEffect;
import org.newdawn.slick.util.EFXFilter;

public class SoundGroup {
	
	public List<Sound> SOUNDS;
	private Random rnd = new Random();
	
	public SoundGroup(String s) throws SlickException{
		SOUNDS = new ArrayList<Sound>();
		for (File file : (new File("data/sounds/" + s).listFiles())){
			SOUNDS.add(Sounds.loadSound(s + "/" + file.getName()));
		}
	}
	
	public void playSingle(){
		Sounds.play(SOUNDS.get(rnd.nextInt(SOUNDS.size())), rnd.nextFloat()*0.4f + 0.6f, rnd.nextFloat()*0.4f + 0.6f);
	}
	
	public void playSingle(EFXEffect effect, EFXFilter filter){
		Sounds.play(SOUNDS.get(rnd.nextInt(SOUNDS.size())), 1.0f, 1.0f, effect, filter);
	}
	
	public void playSingle(float pitch, float volume){
		Sounds.play(SOUNDS.get(rnd.nextInt(SOUNDS.size())), pitch, volume);
	}
	
	public void playSingle(float pitchAverage, float pitchDeviation, float volumeAverage, float volumeDeviation){
		Sounds.play(SOUNDS.get(rnd.nextInt(SOUNDS.size())), pitchAverage + (rnd.nextFloat() - 0.5f)*pitchDeviation, volumeAverage + (rnd.nextFloat() - 0.5f)*volumeDeviation);
	}
	
	public void stopSounds(){
		for (int i = 0; i < SOUNDS.size(); i++){
			SOUNDS.get(i).stop();
		}
	}
	
	public void releaseSounds(){
		for (int i = 0; i < SOUNDS.size(); i++){
			SOUNDS.get(i).release();
		}
	}
	
}
