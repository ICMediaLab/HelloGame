package entities.players;

import game.GameplayState;
import game.config.Config;
import items.Sword;
import items.Weapon;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

import map.Cell;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import sounds.SoundGroup;
import sounds.Sounds;
import utils.ImageUtils;
import utils.MapLoader;
import utils.Position;
import utils.classfinder.ClassFinder;
import utils.interval.one.ColourRange;
import utils.interval.one.Interval;
import utils.interval.two.FixedPosition;
import utils.interval.two.Interval2D;
import utils.interval.two.Range2D;
import utils.interval.two.Sector2D;
import utils.particles.NonCollidingParticle;
import utils.particles.NonCollidingParticleGenerator;
import utils.particles.NormalParticleEmitter;
import utils.particles.ParticleGenerator;
import entities.AbstractEntity;
import entities.DestructibleEntity;
import entities.MovingEntity;
import entities.StaticEntity;
import entities.players.abilities.PlayerAbility;

public class Player extends AbstractEntity {
	
	private static final int PLAYER_DEFAULT_LAYER = 0;
	private static final Dimension PLAYER_DEFAULT_SIZE = new Dimension(1, 1);
	private static final int PLAYER_DEFAULT_MAXHEALTH = 100;
	
	private final Animation left, right, leftPause, rightPause;
	private Animation sprite;
	private final Map<String, PlayerAbility> abilities = ClassFinder.getAbilityMap();
	
	private static final Sound SOUND_JUMP = Sounds.loadSound("jump.ogg");
	private static final SoundGroup FOOTSTEPS = Sounds.loadSoundGroup("player/footsteps/grass");
	private static final SoundGroup SOUND_LANDING = Sounds.loadSoundGroup("player/landing");
	
	private static final Random rand = new Random();
	
	private float speed = 0.3f;
	private Weapon sword;
	private boolean onGround = true;
	private boolean isRight = true;
	private float rangedCounter = 0;
	private float walkingCounter = 0;
	
    private Collection<Image> dust = ImageUtils.populate(new ArrayList<Image>(),"data/images/circle.png");
	
	private Body body;
	
	public Player(float x, float y, float width, float height, int maxhealth) {
		super(x,y, width,height, maxhealth);
		//Image[] movementRight = null;
		Image movementRightRaw = null;
		Image movementLeftRaw = null;
		SpriteSheet movementRightSheet = null;
		SpriteSheet movementLeftSheet = null;
		//Image[] movementLeft = null;
		
		try {
			movementRightRaw = new Image("data/images/walk3.png");
			movementLeftRaw = movementRightRaw.getFlippedCopy(true, false);
			movementRightSheet = new SpriteSheet(movementRightRaw, 40, 60);
			movementLeftSheet = new SpriteSheet(movementLeftRaw, 40, 60);
			//movementLeft = new Image[]{new Image("data/images/dvl1_lf1.png"), new Image("data/images/dvl1_lf2.png")};
			
			//temp weapon
			//I know the file names suck
			sword = new Sword(new Rectangle(1,1,1,1), new Image[]{
				new Image("data/images/stick/stick_0010_Vector-Smart-Object-copy-4.png"),
				new Image("data/images/stick/stick_0009_Vector-Smart-Object-copy-5.png"),
				new Image("data/images/stick/stick_0008_Vector-Smart-Object-copy-6.png"),
				new Image("data/images/stick/stick_0007_Vector-Smart-Object-copy-7.png"),
				new Image("data/images/stick/stick_0006_Vector-Smart-Object-copy-8.png"),
				new Image("data/images/stick/stick_0005_Vector-Smart-Object-copy-9.png"),
				new Image("data/images/stick/stick_0004_Vector-Smart-Object-copy-10.png"),
				new Image("data/images/stick/stick_0003_Vector-Smart-Object-copy-11.png"),
				new Image("data/images/stick/stick_0002_Vector-Smart-Object-copy-12.png"),
				new Image("data/images/stick/stick_0001_Vector-Smart-Object-copy-13.png"),
				new Image("data/images/stick/stick_0000_Vector-Smart-Object-copy-14.png")},
					5);
		} catch (SlickException e) {
			//do shit all
		}
		//int[] duration = {200,200};
		right = new Animation(movementRightSheet, 22);
		//left = new Animation(movementLeft, duration, false);
		left = new Animation(movementLeftSheet, 22); //TODO: make it left
		rightPause = new Animation(movementRightSheet, 0, 0, 0, 0, true, 1000, false);
		leftPause = new Animation(movementLeftSheet, 0, 0, 0, 0, true, 1000, false);
		sprite = rightPause;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(x + 0.5f, y + 0.5f);
		
		body = GameplayState.getWorld().createBody(bodyDef);
		
		CircleShape shape = new CircleShape();
		shape.m_radius = 0.5f;
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 90f;
		fixtureDef.restitution = 0.05f;
		fixtureDef.friction = 0.7f;
		
		body.createFixture(fixtureDef);
	}
	
	public Player(float x, float y) {
		this(x,y,PLAYER_DEFAULT_SIZE.width,PLAYER_DEFAULT_SIZE.height,PLAYER_DEFAULT_MAXHEALTH);
	}
	
	@Override
	public Player clone() {
		return new Player(getX(), getY(), getWidth(), getHeight(),getMaxHealth());
	}
	
	/**
	 * Checks if the player has the ability, if the player does the ability is 'used'. 
	 * @param key The name of the ability (equal to the name of the ability class without the "Ability.java" bit on the end.<br />
	 * For example, DoubleJumpAbility.java would be referenced to be calling useAbility("DoubleJump").<br />
	 * The key is not case sensitive.
	 */
	public void useAbility(String key) {
		//lookup the key in the allowed abilities
		//if the key is in the map then ability.use(this)
		PlayerAbility tempability = abilities.get(key.toLowerCase());
		if(tempability != null)
		{
			tempability.use(this);
		}
		
	}
	
	private void playerJump() {
		useAbility("doublejump");
		if (isOnGround()) {
			super.jump();
			//Sounds.play(SOUND_JUMP, 1.0f, 0.3f);
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
			//MapLoader.getCurrentCell().removeEntity(this);
			takeDamage(-100); // auto-consume-reviving-potion upon the event of death
			return;
		}
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			playerJump();
			
			if (sprite == left){
				sprite = leftPause;
			}
			else if (sprite == right){
				sprite = rightPause;
			}
			
		}
		if (input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)) {
			accelerate(-speed,0f);
			sprite = left;
			isRight = false;
			sprite.update(Config.DELTA);
			body.m_linearVelocity.x = -speed*32;
		}
		else if (input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)) {
			accelerate(speed,0f);
			sprite = right;
			isRight = true;
			sprite.update(Config.DELTA);
			body.m_linearVelocity.x = speed*32;
		}
		else if (!input.isKeyPressed(Input.KEY_SPACE)) {
			if (sprite == left)
			{
				sprite = leftPause;
			}
			else if (sprite == right)
			{
				sprite = rightPause;
			}
		}
		if (input.isKeyPressed(Input.KEY_W) || input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
		    sword.attack(this);
		}
//		if (input.isKeyPressed(Input.KEY_S) || input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
//		    useAbility("rangedattack");
//		}
		if (input.isKeyDown(Input.KEY_S) || input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
		    rangedCounter += Config.DELTA; // accumulate time button held
		} else if (rangedCounter != 0) {
		    useAbility("rangedattack"); // fire projectile
		    rangedCounter = 0; // reset time
		}
		if (input.isKeyPressed(Input.KEY_E)){
			useAbility("speeddash");
		}
		if (input.isKeyPressed(Input.KEY_Q)){
			useAbility("forwardteleport");
		}
		
		if (input.isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON)){
			translateSmooth(10, input.getMouseX()/32f + getWidth()/2f, input.getMouseY()/32f + getHeight()/2f);
		}
		
		if (isOnGround() && isMovingX()) {
			walkingCounter -= Config.DELTA;
			if (walkingCounter < 0) {
				
				walkingCounter += rand.nextInt(Config.DELTA*4);
				FOOTSTEPS.playSingle(0.8f, 0.2f, 0.1f, 0.02f);
				
				Range2D spawn;
				if(getDirection() > 0){ //right
					spawn = new Sector2D(0.1f, 0.15f, -Math.PI, -Math.PI*2.0/3.0);
				}else{
					spawn = new Sector2D(0.1f, 0.15f, -Math.PI/3, 0);
				}
				MapLoader.getCurrentCell().addParticleEmmiter(
						new NormalParticleEmitter<NonCollidingParticle>
								(pGen, new FixedPosition((getCentreX()), getY() + getHeight()), spawn,getLayer()-1,2));
			}
		}
		
		sword.update(gc);
		
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
		
		body.setTransform(new Vec2(getX() + 0.5f, getY() + 0.5f), 0);
	}
	
	private final ColourRange cRange = new ColourRange(0.2f, 0.4f, 0.1f, 0.4f, 0.1f, 0.4f);
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
		
		renderSprite(sprite, -4, -25);
		
		if (sword != null && sword.used()) {
		    sword.render(gc,g);
		}
		
		// Health bar above player
		renderHealthBar(-15);
	}
	
	@Override
	public void collide(MovingEntity e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void collide(StaticEntity<?> e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getLayer() {
		return PLAYER_DEFAULT_LAYER;
	}
	
	public float getRangedCounter() {
	    return rangedCounter / 1000;
	}
	
	@Override
	public void collide(DestructibleEntity d) {
		// TODO Auto-generated method stub
		
	}
}
