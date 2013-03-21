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
	    /*if (p.getDirection() == 1) {
	        pro = new Projectile(p.getX(), p.getY(), 1, 1, 10, 0);
	    } else {
	        pro = new Projectile(p.getX(), p.getY(), 1, 1, 10, Math.PI);
	    }*/
	    pro = new Projectile(p.getX(), p.getY(), 1, 1, 10, vec.getAngle());
		MapLoader.getCurrentCell().addProjectile(pro);
	}

	@Override
	public void stop_sounds() {
		// TODO Auto-generated method stub
		
	}

}
