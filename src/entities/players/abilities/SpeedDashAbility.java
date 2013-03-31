package entities.players.abilities;

import entities.players.Player;

public class SpeedDashAbility extends PlayerAbility {
	
	public SpeedDashAbility() {
		this.name = "Speed dash";
		this.description = "Press E to move quickly forward";
	}
	
	public void use(Player p){
		if (p.getDirection() == 1){
			p.translateSmooth(10, (p.getX() + 10f), p.getY());
		} else {
			p.translateSmooth(10, (p.getX() - 10f), p.getY());
		}
	}
	
}
