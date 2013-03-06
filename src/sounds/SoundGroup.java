package sounds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.EFXEffect;
import org.newdawn.slick.util.EFXFilter;

import entities.players.Player;

public class SoundGroup {
	
	public List<Sound> SOUNDS;
	private long accumulator = 0;
	private long oldTime = 0;
	private Random rnd = new Random();
	private String s;
	
	public SoundGroup(String s) throws SlickException{
		this.s = s;
		SOUNDS = new ArrayList<Sound>();
		
		for (File file : (new File("data/sounds/" + s).listFiles())){
			SOUNDS.add(Sounds.loadSound(s + "/" + file.getName()));
		}
		
	}
	
	public void playRandom(GameContainer gc, Player player, int frequencyTime, float pitchavg, float pitchrnd, float volavg, float volrnd){
		if (player.isOnGround() && player.isMovingX()){
			accumulator += gc.getTime() - oldTime;
			if (accumulator > frequencyTime){
				Sounds.play(SOUNDS.get(rnd.nextInt(SOUNDS.size())), rnd.nextFloat()*pitchrnd + pitchavg, rnd.nextFloat()*volrnd + volavg);
				accumulator = 0;
			}
			oldTime = gc.getTime();
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
	
	public String toString(){
		return s; 
		//yeah i made this just to get rid of the warning icon...
	}
}
