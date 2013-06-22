package entities.players.abilities;

import entities.players.Player;

class SpeedDashAbility extends AbstractPlayerAbility {
	
	SpeedDashAbility() {
		super("Speed dash","Press E to move quickly forward", new String[]{"data/images/playerAbility.png", }, new int[]{1000});
	}
	
	public void use(Player p){
		if (p.getDirection() == 1){
			p.translateSmooth(10, (p.getX() + 10f), p.getY());
		} else {
			p.translateSmooth(10, (p.getX() - 10f), p.getY());
		}
	}
	
}
