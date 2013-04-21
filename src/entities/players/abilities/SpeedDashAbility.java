package entities.players.abilities;

import entities.players.Player;

public class SpeedDashAbility extends AbstractPlayerAbility {
	
	private static final long serialVersionUID = 7201612534655193271L;
	
	public SpeedDashAbility() {
		super("Speed dash","Press E to move quickly forward");
	}
	
	public void use(Player p){
		if (p.getDirection() == 1){
			p.translateSmooth(10, (p.getX() + 10f), p.getY());
		} else {
			p.translateSmooth(10, (p.getX() - 10f), p.getY());
		}
	}
	
}
