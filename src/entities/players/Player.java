package entities.players;

import java.util.Map;

import org.newdawn.slick.geom.Rectangle;

import entities.Entity;
import entities.players.abilities.AbilityFinder;
import entities.players.abilities.IPlayerAbility;

public class Player extends Entity {
	
	private Map<String, IPlayerAbility> abilities = AbilityFinder.initialiseAbilities();

	public Player(Rectangle hitbox, int maxhealth) {
		super(hitbox, maxhealth);
	}
	
	protected Object clone() {
		return new Player(new Rectangle(getX(), getY(), getWidth(), getHeight()),getMaxHealth());
	}
	
	/**
	 * checks if the player has the ability, if the player does the ability is 'used'
	 * @param key
	 */
	public void useAbility(String key)
	{
		//lookup the key in the allowed abilities
		//if the key is in the map then ability.use(this)
		IPlayerAbility tempability = abilities.get(key.toLowerCase());
		if(tempability!=null)
		{
			tempability.use(this);
		}
		
	}


	@Override
	public void jump() {
		super.jump();
		useAbility("doublejump");
	}
	
}
