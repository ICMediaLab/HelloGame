package entities.players.abilities;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.Image;

import entities.players.Player;

public enum PlayerAbilities implements PlayerAbility {
	DOUBLE_JUMP(DoubleJumpAbility.class),
	FORWARD_TELEPORT(ForwardTeleportAbility.class),
	RANGED_ATTACK(RangedAttackAbility.class),
	SPEED_DASH(SpeedDashAbility.class);
	
	private final PlayerAbility ability;
	
	private PlayerAbilities(Class<? extends PlayerAbility> clazz){
		PlayerAbility pa = null;
		try {
			pa = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		ability = pa;
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
