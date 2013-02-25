package entities.players.abilities;

import sounds.Sounds;

import entities.players.Player;

public class DoubleJumpAbility extends PlayerAbility {
	
	private boolean hasJumped = false;

	@Override
	/**
	 * If the player is not on the ground, then jump
	 */
	public void use(Player p) {
		if(!p.isOnGround()) {
			if (!hasJumped) {
				hasJumped = true;
				Sounds.play(Player.SOUND_DOUBLE_JUMP);
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
