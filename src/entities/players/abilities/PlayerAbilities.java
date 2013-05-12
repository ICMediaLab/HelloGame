package entities.players.abilities;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.Image;

import entities.players.Player;

/**
 * Representation of all player abilities through an enumeration.
 */
public enum PlayerAbilities implements PlayerAbility {
	DOUBLE_JUMP(new DoubleJumpAbility()),
	FORWARD_TELEPORT(new ForwardTeleportAbility()),
	RANGED_ATTACK(new RangedAttackAbility()),
	SPEED_DASH(new SpeedDashAbility());
	
	private final PlayerAbility ability;
	
	private PlayerAbilities(PlayerAbility ability){
		this.ability = ability;
	}
	
	public static Set<PlayerAbilities> getAbilitySet(){
		Set<PlayerAbilities> res = new HashSet<PlayerAbilities>();
		for(PlayerAbilities pas : values()){
			res.add(pas);
		}
		return res;
	}
	
	@Override
	public void use(Player p) {
		ability.use(p);
	}
	
	@Override
	public Image getImage() {
		return ability.getImage();
	}
	
	@Override
	public String getName() {
		return ability.getName();
	}
	
	@Override
	public String getDescription() {
		return ability.getDescription();
	}
}
