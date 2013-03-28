package entities.players.abilities;

import utils.GameplayMouseInput;
import utils.MapLoader;
import utils.Position;
import entities.players.Player;
import items.projectiles.Projectile;

public class RangedAttackAbility extends PlayerAbility {

	@Override
	public void use(Player p) {
	    Projectile pro = null;
	    Position vec = GameplayMouseInput.getMousePosition().clone();
	    vec.translate(-p.getCentreX(), -p.getCentreY());
	    pro = new Projectile(p.getCentreX(), p.getCentreY(), 10, vec.getAngle(), p.getRangedCounter());
		MapLoader.getCurrentCell().addEntity(pro);
	}

	@Override
	public void stop_sounds() {
		// TODO Auto-generated method stub
		
	}

}
