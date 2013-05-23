package entities.players.abilities;

import org.newdawn.slick.Sound;

import sounds.Sounds;
import entities.players.Player;

class DoubleJumpAbility extends AbstractPlayerAbility {
	
	private static final Sound SOUND_DOUBLE_JUMP = Sounds.loadSound("double_jump.ogg");
	private boolean hasJumped = false;
	
	DoubleJumpAbility(){
		super("Double jump","Press SPACE twice to double jump");
	}
	
	@Override
	/**
	 * If the player is not on the ground, then jump
	 */
	public void use(Player p) {
		if(!p.isOnGround()) {
			if (!hasJumped) {
				hasJumped = true;
//				Sounds.play(SOUND_DOUBLE_JUMP);
				p.jump();
			}
		} else {
			hasJumped = false;
		}
		
	}

}
