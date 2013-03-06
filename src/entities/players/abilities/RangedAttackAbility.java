package entities.players.abilities;

import utils.MapLoader;
import entities.players.Player;
import items.projectiles.Projectile;

public class RangedAttackAbility extends PlayerAbility {

	@Override
	public void use(Player p) {
		Projectile pro = new Projectile(p.getX(), p.getY(), 32, 32, 10, 0);
		MapLoader.getCurrentCell().addProjectile(pro);
	}

	@Override
	public void stop_sounds() {
		// TODO Auto-generated method stub
		
	}

}
