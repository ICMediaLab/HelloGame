package entities.players.abilities;

import org.jbox2d.common.Vec2;
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
				Sounds.play(SOUND_DOUBLE_JUMP);
				p.getBody().setLinearVelocity(new Vec2(p.getBody().getLinearVelocity().x, p.getBody().getLinearVelocity().y - 8));
			}
		} else {
			hasJumped = false;
		}
		
	}

	@Override
	public void stop_sounds() {
	}

}
