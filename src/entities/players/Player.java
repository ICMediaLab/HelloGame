package entities.players;

import java.util.Map;

import map.Cell;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

import entities.Entity;
import entities.players.abilities.AbilityFinder;
import entities.players.abilities.IPlayerAbility;

public class Player extends Entity {
	
	private Animation sprite;
	private Map<String, IPlayerAbility> abilities = AbilityFinder.initialiseAbilities();
	private static final Sound SOUND_JUMP;
	
	static {
		final String path = "data/sounds/jump.ogg";
		Sound s = null;
		try {
			s = new Sound(path);
		} catch (SlickException e) {
			System.out.println("Sound file for " + Player.class.getSimpleName() + " not found or failed to load.");
			System.out.println("Tried to load from: " + path);
			e.printStackTrace();
		}
		SOUND_JUMP = s;
	}

	public Player(Cell currentCell, Rectangle hitbox, int maxhealth) {
		super(currentCell, hitbox, maxhealth);
		Image[] movementRight = null;
		try {
			movementRight = new Image[]{new Image("data/images/dvl1_rt1.gif"), new Image("data/images/dvl1_rt2.gif")};
		} catch (SlickException e) {
			//do shit all
		}
		int[] duration = {300,300};
		sprite = new Animation(movementRight, duration, false);
	}
	
	@Override
	protected Player clone() {
		return new Player(currentCell, new Rectangle(getX(), getY(), getWidth(), getHeight()),getMaxHealth());
	}
	
	/**
	 * Checks if the player has the ability, if the player does the ability is 'used'. 
	 * @param key The name of the ability (equal to the name of the ability class without the "Ability.java" bit on the end.<br />
	 * For example, DoubleJumpAbility.java would be referenced to be calling useAbility("DoubleJump").<br />
	 * The key is not case sensitive.
	 */
	public void useAbility(String key)
	{
		//lookup the key in the allowed abilities
		//if the key is in the map then ability.use(this)
		IPlayerAbility tempability = abilities.get(key.toLowerCase());
		if(tempability != null)
		{
			tempability.use(this);
		}
		
	}
	
	private void playerJump() {
		useAbility("doublejump");
		if (isOnGround()) {
			super.jump();
			SOUND_JUMP.play();
		}
	}
	
	@Override
	public void stop_sounds(){
		super.stop_sounds();
		SOUND_JUMP.stop();
		for(IPlayerAbility ability : abilities.values()){
			ability.stop_sounds();
		}
	}

	/**
	 * This is the only method that needs to be called to update the player.
	 * Input is assumed to already have been checked for being pressed
	 * the previous frame.
	 * @param input The keyboard input at the time of this frame.
	 * @param delta The time in microseconds since the last update.
	 */
	@Override
	public void update(Input input, int delta) {
		
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			playerJump();
		}
		if (input.isKeyDown(Input.KEY_A)) {
			moveX(-0.35f);
		}
		if (input.isKeyDown(Input.KEY_D)) {
			moveX(0.35f);
		}
		
		frameMove(delta);
	}
	
	@Override
	public void render() {
		sprite.draw((int)getX(), (int)getY(), new Color(256,256,256));
	}
}
