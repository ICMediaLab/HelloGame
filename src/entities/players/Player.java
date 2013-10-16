package entities.players;

import game.MouseCapture;
import game.config.Config;
import items.Shield;
import items.Weapons;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

import map.Cell;
import map.MapLoader;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import sounds.SoundGroup;
import sounds.Sounds;
import utils.ImageUtils;
import utils.Position;
import utils.ani.AnimationContainer;
import utils.ani.AnimationManager;
import utils.ani.AnimationState;
import utils.interval.colour.ContinuousColourRange;
import utils.interval.one.Interval;
import utils.interval.two.FixedPosition;
import utils.interval.two.Interval2D;
import utils.interval.two.Range2D;
import utils.interval.two.Sector2D;
import utils.particles.NonCollidingParticle;
import utils.particles.NonCollidingParticleGenerator;
import utils.particles.NormalParticleEmitter;
import utils.particles.ParticleGenerator;
import utils.triggers.TriggerEvent;
import entities.AbstractEntity;
import entities.DestructibleEntity;
import entities.MovingEntity;
import entities.StaticEntity;
import entities.players.abilities.PlayerAbilities;

public class Player extends AbstractEntity {
	
	private static final int PLAYER_DEFAULT_LAYER = 0;
	private static final Dimension PLAYER_DEFAULT_SIZE = new Dimension(1, 1);
	private static final int PLAYER_DEFAULT_MAXHEALTH = 100;
	
	private final Set<PlayerAbilities> abilities = PlayerAbilities.getAbilitySet();
	
	private static final Sound SOUND_JUMP = Sounds.loadSound("jump.ogg");
	private static final SoundGroup FOOTSTEPS = Sounds.loadSoundGroup("player/footsteps/grass");
	private static final SoundGroup SOUND_LANDING = Sounds.loadSoundGroup("player/landing");
	
	private static final Random rand = new Random();
	
	private Weapons equippedWeapon = null;
	private Shield equippedShield = null;
	
	private float speed = 0.3f;
	private boolean onGround = true;
	private boolean isRight = true;
	private float rangedCounter = 0;
	private float walkingCounter = 0;
	private float maxDraw = 1500f;
	private int walkingSoundDelay = 0;
	
	private Collection<Image> dust = ImageUtils.populate(new ArrayList<Image>(),"data/images/circle.png");
	
	private Player(float x, float y, float width, float height, int maxhealth, AnimationManager ani) {
		super(x,y, width,height, ani, maxhealth);
	}
	
	private Player(float x, float y, AnimationManager ani) {
		this(x,y,PLAYER_DEFAULT_SIZE.width,PLAYER_DEFAULT_SIZE.height,PLAYER_DEFAULT_MAXHEALTH, ani);
	}
	
	@Override
	public Player clone() {
		return this;
	}
	
	public static Player getPlayerInstance(float x, float y){
		try {
			Image movementRightRaw = new Image("data/images/walk3.png");
			Image movementLeftRaw = movementRightRaw.getFlippedCopy(true, false);
			SpriteSheet movementRightSheet = new SpriteSheet(movementRightRaw, 40, 60);
			SpriteSheet movementLeftSheet = new SpriteSheet(movementLeftRaw, 40, 60);
			AnimationContainer right = new AnimationContainer(movementRightSheet, 22, new Position());
			AnimationContainer left = new AnimationContainer(movementLeftSheet, 22, new Position());
			AnimationContainer rightPause = new AnimationContainer(movementRightSheet, 1000, new Position());
			AnimationContainer leftPause = new AnimationContainer(movementLeftSheet, 1000, new Position());
			AnimationManager ani = new AnimationManager();
			ani.addAnimation(AnimationState.ROAM_RIGHT, right);
			ani.addAnimation(AnimationState.ROAM_LEFT, left);
			ani.addAnimation(AnimationState.PAUSE_RIGHT, rightPause);
			ani.addAnimation(AnimationState.PAUSE_LEFT, leftPause);
			return new Player(x,y,ani);
		} catch (SlickException e) {
			System.out.println("Failed to load player spritesheets. Fatal.");
		}
		return null;
	}
	
	/**
	 * Checks if the player has the ability, if the player does the ability is 'used'. 
	 * @param key The name of the ability (equal to the name of the ability class without the "Ability.java" bit on the end.<br />
	 * For example, DoubleJumpAbility.java would be referenced to be calling useAbility("DoubleJump").<br />
	 * The key is not case sensitive.
	 */
	public void useAbility(PlayerAbilities ability) {
		if(abilities.contains(ability)) {
			ability.use(this);
		}
		
	}
	
	private void playerJump() {
		useAbility(PlayerAbilities.DOUBLE_JUMP);
		if (isOnGround()) {
			super.jump();
//			Sounds.play(SOUND_JUMP, 1.0f, 0.3f);
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
	
	public void update(GameContainer gc) {
		Input input = gc.getInput();
		
		if (isDead()) {
			//die();
			takeDamage(-100); // auto-consume-reviving-potion upon the event of death
			return;
		}
		if(onLadder()){
			if (input.isKeyDown(Input.KEY_SPACE)){
				setVelocity(getdX(), -0.3f);
			}else{
				setVelocity(getdX(), 0f);
			}
		}else if (input.isKeyPressed(Input.KEY_SPACE)) {
			playerJump();
			
			AnimationState state = getCurrentAnimationState();
			if (state == AnimationState.ROAM_LEFT){
				setCurrentAnimationState(AnimationState.PAUSE_LEFT);
			}
			else if (state == AnimationState.ROAM_RIGHT){
				setCurrentAnimationState(AnimationState.PAUSE_RIGHT);
			}
			
		}
		if (input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)) {
			accelerate(-speed,0f);
			setCurrentAnimationState(AnimationState.ROAM_LEFT);
			isRight = false;
		} else if (input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)) {
			accelerate(speed,0f);
			setCurrentAnimationState(AnimationState.ROAM_RIGHT);
			isRight = true;
		} else if (!input.isKeyPressed(Input.KEY_SPACE)) {
			AnimationState state = getCurrentAnimationState();
			if (state == AnimationState.ROAM_LEFT){
				setCurrentAnimationState(AnimationState.PAUSE_LEFT);
			}
			else if (state == AnimationState.ROAM_RIGHT){
				setCurrentAnimationState(AnimationState.PAUSE_RIGHT);
			}
		}
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && equippedWeapon != null) {
		    equippedWeapon.attack(this);
		}
		if (input.isKeyPressed(Input.KEY_LSHIFT) && equippedShield != null) {
            equippedShield.raise();
        }
		if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
			if (rangedCounter < maxDraw)  rangedCounter += Config.DELTA; // accumulate time button held
		} else if (rangedCounter != 0) {
		    useAbility(PlayerAbilities.RANGED_ATTACK); // fire projectile
		    rangedCounter = 0; // reset time
		}
		if (input.isKeyPressed(Input.KEY_E)){
			useAbility(PlayerAbilities.SPEED_DASH);
		}
		if (input.isKeyPressed(Input.KEY_Q)){
			useAbility(PlayerAbilities.FORWARD_TELEPORT);
		}
		
		if (input.isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON)){
			translateSmooth(10, input.getMouseX()/32f + getWidth()/2f, input.getMouseY()/32f + getHeight()/2f);
		}
		
		if (isOnGround() && isMovingX()) {
			walkingCounter -= Config.DELTA;
			if (walkingCounter < 0) {

				walkingCounter += rand.nextInt(Config.DELTA*4);
				
				if (walkingSoundDelay == 3) {
					FOOTSTEPS.playSingle(0.8f, 0.2f, 0.03f, 0.02f);
					walkingSoundDelay = 0;
				}
				walkingSoundDelay++;
				
				Range2D spawn;
				if(getDirection() > 0){ //right
					spawn = new Sector2D(0.1f, 0.15f, (float) (-Math.PI), (float) (-Math.PI*2.0/3.0));
				}else{
					spawn = new Sector2D(0.1f, 0.15f, (float) (-Math.PI/3), 0);
				}
				MapLoader.getCurrentCell().addParticleEmmiter(
						new NormalParticleEmitter<NonCollidingParticle>
								(pGen, new FixedPosition((getCentreX()), getY() + getHeight()), spawn,getLayer()-1,2));
			}
			this.setShouldAnimationUpdate(true);
		} else {
			this.setShouldAnimationUpdate(false);
		}
		
		if(equippedWeapon != null){
			equippedWeapon.update(gc);
		}
		if(equippedShield != null){
            equippedShield.update(gc);
        }
		
		updateTranslateSmooth();
		
		
		frameMove();
		
		boolean newOnGround = isOnGround();
		if (!onGround && newOnGround && getdY() > 0){
			SOUND_LANDING.playSingle(1.0f, 0.3f * getdY());
			
			// Would be nice to make it bigger (more particles) in shorter time and dependent on player falling speed
			MapLoader.getCurrentCell().addParticleEmmiter(
					new NormalParticleEmitter<NonCollidingParticle>(pGen, new FixedPosition((getCentreX()), getY() + getHeight()), new Interval2D(-0.1f, 0.1f, -0.15f, -0.05f),getLayer()-1,3));
		}
		onGround = newOnGround;
		
		checkMapChanged();
	}
	
	private final ContinuousColourRange cRange = new ContinuousColourRange(0.2f, 0.4f, 0.1f, 0.4f, 0.1f, 0.4f);
	private final ParticleGenerator<NonCollidingParticle> pGen = new NonCollidingParticleGenerator(dust, cRange, new Interval(0.01f,0.02f), new Interval(9f,10f));
	
	/**
	 * Checks the player's x and y position to see if they have reached the edge of the map. 
	 * @return -1 for no change, 0 for up, 1 for right, 2 for down, 3 for left
	 */
	@Override
	public boolean checkMapChanged() {
		Cell cell = MapLoader.getCurrentCell();
		boolean changed = false;
		//check top
		if (getY() < 1 && getdY() < 0) {
			cell = MapLoader.setCurrentCell(this,MapLoader.getCurrentX(), MapLoader.getCurrentY() - 1);
			setPosition(getX(), cell.getHeight() - getHeight() - 1);
			changed = true;
		//bottom
		}else if (getY() >= cell.getHeight() - 1 - getHeight() && getdY() > 0) {
			cell = MapLoader.setCurrentCell(this,MapLoader.getCurrentX(), MapLoader.getCurrentY() + 1);
			setPosition(getX(), 1);
			changed = true;
		}
		//right
		if (getX() >= cell.getWidth() - 1 - getWidth() && getdX() > 0) {
			cell = MapLoader.setCurrentCell(this,MapLoader.getCurrentX() + 1, MapLoader.getCurrentY());
			setPosition(1, getY());
			changed = true;
		//left
		}else if (getX() < 1 && getdX() < 0) {
			cell = MapLoader.setCurrentCell(this,MapLoader.getCurrentX() - 1, MapLoader.getCurrentY());
			setPosition(cell.getWidth() - getWidth() - 1, getY());
			changed = true;
		}
		return changed;
	}
	
	/** 
	 * returns -1 for left, +1 for right
	 */
	public int getDirection() {
	    if (isRight) {
	        return 1;
	    } else {
	        return -1;
	    }
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		//sprite.draw((int)((getX()-1)*Config.getTileSize() - 4), (int)((getY()-1)*Config.getTileSize() - 25), new Color(255,255,255));
		AnimationManager animag = getAnimationManager();
		Animation sprite = getCurrentAnimationContainer().getAnimation();
		renderSprite(sprite, -4, -25);
		
		if (equippedWeapon != null && equippedWeapon.used()) {
			equippedWeapon.render(gc,g);
		}
		if (equippedShield != null && equippedShield.raised()) {
            equippedShield.render(gc,g);
        }
		
		if (rangedCounter > 0) {
		    Position mouse = MouseCapture.getMousePositionRelative();
            Position player = getPosition();
            
            float angle = (float)player.distanceTo(mouse).getAngle();
            System.out.println(angle);
            
            Image bow = PlayerAbilities.RANGED_ATTACK.getImage().getImage((PlayerAbilities.RANGED_ATTACK.getImage().getFrame()));
            bow.setRotation((float) (angle * 180 / Math.PI));
            
            if (angle >= 0 && angle < Math.PI/2 || angle >= -(Math.PI/2) && angle < 0) {
                animag.setCurrentState(AnimationState.ROAM_RIGHT);
                isRight = true;
                
            } else {
            	animag.setCurrentState(AnimationState.ROAM_LEFT);
                isRight = false;
            }
            
            
		    PlayerAbilities.RANGED_ATTACK.getImage().draw((getX() - 1f + (float)Math.cos(angle)/2)*Config.getTileSize(), 
		            (getY() - 1.5f + (float)Math.sin(angle)/2)*Config.getTileSize());
		    //TODO: Make the bow rotate to face mouse, invert when player turns!
		}
		
		// Health bar above player
		renderHealthBar(-15);
		
		g.setColor(Color.red);
		g.fillArc((getX() - 1)*Config.getTileSize(), (getY() - 2)*Config.getTileSize(), Config.getTileSize(), Config.getTileSize(), 180, rangedCounter*180/maxDraw - 180);
	}
	
	@Override
	public void collide(MovingEntity e) { }
	
	@Override
	public void collide(StaticEntity<?> e) { }
	
	@Override
	public int getLayer() {
		return PLAYER_DEFAULT_LAYER;
	}
	
	public float getRangedCounter() {
	    return rangedCounter / 1000;
	}
	
	@Override
	public void collide(DestructibleEntity d) { }

	public void setWeapon(Weapons newWeapon) {
		equippedWeapon = newWeapon;
		TriggerEvent.PLAYER_ITEM_PICKUP.triggered(newWeapon);
	}
	
	public void setShield(Shield s) {
        equippedShield = s;
        TriggerEvent.PLAYER_SHIELD_PICKUP.triggered(s);          // doesnt support shields yet
    }
}
