package entities.players.abilities;

import org.newdawn.slick.Sound;

import sounds.Sounds;

import entities.players.Player;

public class DoubleJumpAbility extends PlayerAbility {
	
	private static final  Sound SOUND_DOUBLE_JUMP = Sounds.loadSound("double_jump.ogg");
	private boolean hasJumped = false;

	@Override
	/**
	 * If the player is not on the ground, then jump
	 */
	public void use(Player p) {
		if(!p.isOnGround()) {
			if (!hasJumped) {
				hasJumped = true;
				//Sounds.play(SOUND_DOUBLE_JUMP);
				p.jump();
			}
		} else {
			hasJumped = false;
		}
		
	}

	@Override
	public void stop_sounds() {
	}

}
