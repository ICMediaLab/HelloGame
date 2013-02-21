package sounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
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
		if (s == "grass"){
			try {
				SOUNDS.add(new Sound("data/sounds/footsteps/grass1.wav"));
				SOUNDS.add(new Sound("data/sounds/footsteps/grass2.wav"));
				SOUNDS.add(new Sound("data/sounds/footsteps/grass3.wav"));
				SOUNDS.add(new Sound("data/sounds/footsteps/grass4.wav"));
				SOUNDS.add(new Sound("data/sounds/footsteps/grass5.wav"));
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (s == "gravel"){
			try {
				SOUNDS.add(new Sound("data/sounds/footsteps/gravel1.wav"));
				SOUNDS.add(new Sound("data/sounds/footsteps/gravel2.wav"));
				SOUNDS.add(new Sound("data/sounds/footsteps/gravel3.wav"));
				SOUNDS.add(new Sound("data/sounds/footsteps/gravel4.wav"));
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void playRandom(GameContainer gc, Player player){
		if (player.isOnGround() && player.isMovingX() && s == "grass"){
			accumulator += gc.getTime() - oldTime;
			if (accumulator > 300){
				SOUNDS.get(rnd.nextInt(5)).play(rnd.nextFloat()*0.2f + 0.8f, rnd.nextFloat()*0.3f + 0.1f);
				accumulator = 0;
			}
			oldTime = gc.getTime();
		} else if (player.isOnGround() && player.isMovingX() && s == "gravel"){
			accumulator += gc.getTime() - oldTime;
			if (accumulator > 300){
				SOUNDS.get(rnd.nextInt(4)).play(rnd.nextFloat()*0.2f + 0.8f, rnd.nextFloat()*0.3f + 0.1f);
				accumulator = 0;
			}
			oldTime = gc.getTime();
		}
	}
	
	public void stopSounds(){
		for (int i = 0; i < SOUNDS.size(); i++){
			SOUNDS.get(i).stop();
		}
	}
}
