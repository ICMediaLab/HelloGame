package entities.players.abilities;

import game.MouseCapture;
import items.projectiles.Projectile;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;

import map.MapLoader;

import org.newdawn.slick.SlickException;

import sounds.SoundGroup;
import utils.Position;
import entities.objects.LeafTest;
import entities.players.Player;

class RangedAttackAbility extends AbstractPlayerAbility {
	
	private transient final SoundGroup release;
	
	RangedAttackAbility() {
		super("Ranged attack","Hold RMB and release to shoot");
		release = loadRelease();
	}
	
	private SoundGroup loadRelease() {
		try {
			return new SoundGroup("player/arrow/release");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void use(Player p) {
	    Projectile pro = null;
	    Position vec = MouseCapture.getMousePositionRelative();
	    vec.translate(-p.getCentreX(), -p.getCentreY());
	    pro = new Projectile(p.getCentreX(), p.getCentreY(), 10, vec.getAngle(), p.getRangedCounter());
		MapLoader.getCurrentCell().addMovingEntity(pro);
		release.playSingle(1, 0.3f);
	}
}
