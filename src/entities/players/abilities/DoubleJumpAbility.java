package entities.players.abilities;

import entities.players.Player;

public class DoubleJumpAbility extends PlayerAbility {
	
	private boolean hasJumped = false;

	@Override
	/**
	 * If the player is not on the ground, then jump
	 */
	public void use(Player p) {
		System.out.println("FUCK");
		if(!p.isOnGround()) {
			if (!hasJumped) {
				p.jump();
				hasJumped = true;
			}
		} else {
			hasJumped = false;
		}
		
	}

}
