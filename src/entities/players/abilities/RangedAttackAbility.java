package entities.players.abilities;

import utils.MapLoader;
import entities.players.Player;
import items.projectiles.Projectile;

public class RangedAttackAbility extends PlayerAbility {

	@Override
	public void use(Player p) {
	    Projectile pro = null;
	    if (p.getDirection() == 1) {
	        pro = new Projectile(p.getX(), p.getY(), 1, 1, 10, 0);
	    } else {
	        pro = new Projectile(p.getX(), p.getY(), 1, 1, 10, Math.PI);
	    }
		MapLoader.getCurrentCell().addProjectile(pro);
	}

	@Override
	public void stop_sounds() {
		// TODO Auto-generated method stub
		
	}

}
