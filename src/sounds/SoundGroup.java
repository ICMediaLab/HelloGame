package sounds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Sound;

import entities.players.Player;

public class SoundGroup {
	
	public List<Sound> SOUNDS;
	private long accumulator = 0;
	private long oldTime = 0;
	private Random rnd = new Random();
	private String s;
	
	public SoundGroup(String s) {
		this.s = s;
		SOUNDS = new ArrayList<Sound>();
		
		for (File file : (new File("data/sounds/" + s).listFiles())){
			SOUNDS.add(Sounds.loadSound("data/sounds/" + s + "/" + file.getName()));
		}
	}
	
	public void playRandom(GameContainer gc, Player player, int frequencyTime){
		if (player.isOnGround() && player.isMovingX()){
			accumulator += gc.getTime() - oldTime;
			if (accumulator > frequencyTime){
				Sounds.play(SOUNDS.get(rnd.nextInt(SOUNDS.size())), rnd.nextFloat()*0.2f + 0.8f, rnd.nextFloat()*0.3f + 0.1f);
				accumulator = 0;
			}
			oldTime = gc.getTime();
		}
	}
	
	public void playSingle(){
		Sounds.play(SOUNDS.get(rnd.nextInt(SOUNDS.size())), rnd.nextFloat()*0.4f + 0.6f, rnd.nextFloat()*0.4f + 0.6f);
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
