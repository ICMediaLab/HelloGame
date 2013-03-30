package entities.players.abilities;

import game.MouseCapture;
import items.projectiles.Projectile;
import utils.MapLoader;
import utils.Position;
import entities.players.Player;

public class RangedAttackAbility extends PlayerAbility {
	
	public RangedAttackAbility() {
		this.name = "Ranged attack";
		this.description = "Hold RMB and release to shoot";
	}
	
	@Override
	public void use(Player p) {
	    Projectile pro = null;
	    Position vec = MouseCapture.getMousePositionRelative();
	    vec.translate(-p.getCentreX(), -p.getCentreY());
	    pro = new Projectile(p.getCentreX(), p.getCentreY(), 10, vec.getAngle(), p.getRangedCounter());
		MapLoader.getCurrentCell().addEntity(pro);
	}

	@Override
	public void stop_sounds() {
		// TODO Auto-generated method stub
		
	}

}