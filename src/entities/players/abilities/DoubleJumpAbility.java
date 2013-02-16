package entities.players.abilities;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import entities.players.Player;

public class DoubleJumpAbility extends PlayerAbility {
	
	private static final Sound SOUND_DOUBLE_JUMP;
	
	static {
		final String path = "data/sounds/double_jump.ogg";
		Sound s = null;
		try {
			s = new Sound(path);
		} catch (SlickException e) {
			System.out.println("Sound file for " + DoubleJumpAbility.class.getSimpleName() + " not found or failed to load.");
			System.out.println("Tried to load from: " + path);
			e.printStackTrace();
		}
		SOUND_DOUBLE_JUMP = s;
	}
	
	private boolean hasJumped = false;

	@Override
	/**
	 * If the player is not on the ground, then jump
	 */
	public void use(Player p) {
		if(!p.isOnGround()) {
			if (!hasJumped) {
				hasJumped = true;
				SOUND_DOUBLE_JUMP.play();
				p.jump();
			}
		} else {
			hasJumped = false;
		}
		
	}

	@Override
	public void stop_sounds() {
		SOUND_DOUBLE_JUMP.stop();
	}

}
