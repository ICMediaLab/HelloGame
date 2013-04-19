package entities.players.abilities;

import map.MapLoader;

import org.newdawn.slick.SlickException;

import game.MouseCapture;
import items.projectiles.Projectile;
import sounds.SoundGroup;
import utils.Position;
import entities.players.Player;

public class RangedAttackAbility extends AbstractPlayerAbility {
	
	private SoundGroup release;
	
	public RangedAttackAbility() {
		super("Ranged attack","Hold RMB and release to shoot");
		try {
			this.release = new SoundGroup("player/arrow/release");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void use(Player p) {
	    Projectile pro = null;
	    Position vec = MouseCapture.getMousePositionRelative();
	    vec.translate(-p.getCentreX(), -p.getCentreY());
	    pro = new Projectile(p.getCentreX(), p.getCentreY(), 10, vec.getAngle(), p.getRangedCounter());
		MapLoader.getCurrentCell().addMovingEntity(pro);
		release.playSingle();
	}

}