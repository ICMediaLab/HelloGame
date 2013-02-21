package sounds;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Sound;

import entities.players.Player;

public class Sounds {
	
	private List<Sound> ALL_SOUNDS;

	public Sounds() {
		ALL_SOUNDS = new ArrayList<Sound>();
	}
	
	public void init(GameContainer gc, Player player){
		//TODO: load all the sounds for the level and player abilities
	}
	
	/**
	 * Stop all the sounds
	 */
	
	public void stopSounds(){
		for (int i = 0; i < ALL_SOUNDS.size(); i++){
			ALL_SOUNDS.get(i).stop();
		}
	}
	
	/**
	 * Release all the sounds
	 */
	
	public void releaseSounds(){
		for (int i = 0; i < ALL_SOUNDS.size(); i++){
			ALL_SOUNDS.get(i).release();
		}
	}

}
