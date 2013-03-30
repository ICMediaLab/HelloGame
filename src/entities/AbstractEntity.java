package entities;import game.config.Config;import game.debug.FrameTrace;import map.Cell;import map.tileproperties.TileProperty;import org.newdawn.slick.Animation;import org.newdawn.slick.Color;import org.newdawn.slick.GameContainer;import org.newdawn.slick.Graphics;import org.newdawn.slick.geom.Rectangle;import org.newdawn.slick.state.StateBasedGame;import utils.AnimationContainer;import utils.Dimension;import utils.MapLoader;import utils.Position;public abstract class AbstractEntity extends VeryAbstractEntity implements FixedRotationEntity {	private static final float HITBOX_MARGIN = 0.25f;	private static final int DEFAULT_MAXHEALTH = 100;	private final Position xy, dxdy = new Position(0f,0f);	private final Dimension size;	//TODO:	Implement entity image system.	//		No idea how at the moment.	private int health,maxhealth;	private int counter, time;	private float destX, destY, initX, initY;		// for making it darker, depends on layer	private Color filter = Color.white;	//for debugging purposes:	private final FrameTrace frameTrace = new FrameTrace();	public AbstractEntity(Position xy,Dimension size, int maxhealth) {		this.xy = xy;		this.size = size;		this.health = maxhealth;		this.maxhealth = maxhealth;	}	public AbstractEntity(float x, float y, float width, float height, int maxhealth) {		this(new Position(x,y),new Dimension(width,height),maxhealth);	}	public AbstractEntity(float x, float y, float width, float height){		this(new Position(x,y),new Dimension(width,height),DEFAULT_MAXHEALTH);	}	public AbstractEntity(){		this(new Position(0,0),new Dimension(1,1),DEFAULT_MAXHEALTH);	}	/**	 * Returns the current x-position of this entity.	 */	@Override	public float getX(){		return xy.getX();	}	/**	 * Returns the current y-position of this entity.	 */	@Override	public float getY(){		return xy.getY();	}	/**	 * Returns the current x-velocity of this entity.	 */	@Override	public float getdX(){		return dxdy.getX();	}	/**	 * Returns the current y-velocity of this entity.	 */	@Override	public float getdY(){		return dxdy.getY();	}	/**	 * Returns the width of the hitbox of this entity.	 */	@Override	public float getWidth(){		return size.getWidth();	}	/**	 * Returns the height of the hitbox of this entity;	 */	@Override	public float getHeight(){		return size.getHeight();	}	/**	 * Reduces this entity's health by an amount influenced by the argument provided according to some formula.	 * @param normalDamage The damage dealt normally ignoring special hits and armour effects etc...	 * @return The actual amount of damage taken by this entity.	 */	@Override	public int takeDamage(int normalDamage){		//TODO: update for armour etc...		int originalHealth = health;		health = Math.max(0, health - normalDamage);		return originalHealth - health;	}	/**	 * Returns the amount of damage done by this entity when taking into account critical hits etc...	 */	@Override	public int getDamage(){		//TODO: implement		return -1;	}	/**	 * Returns the normal damage (excluding critical hits etc...) done by this entity. 	 */	@Override	public int getNormalDamage(){		//TODO: implement		return -1;	}	/**	 * Returns the absolute value of this entity's current health.	 */	@Override	public int getHealth(){		return health;	}	/**	 * Returns the absolute value of this entity's maximum possible health.	 */	@Override	public int getMaxHealth(){		return maxhealth;	}	/**	 * Moves this entity by it's current velocity values and applies constants such as friction and gravity.	 * @param delta The time in microseconds since the last frame update.	 */	@Override	public void frameMove() {//		float modFriction = getFrictionDelta(delta);//		float modGravity  = getGravityDelta(delta);		//both x and y axis are affected by scalar friction		if (!isOnGround()) {			dxdy.translate(0f,GRAVITY); //fall if not on the ground		} else if (getdY() > 0) {			dxdy.setY(0);		}		dxdy.translate(-getdX()*FRICTION.getX(), -getdY()*FRICTION.getY());		//dxdy.scale(FRICTION);		frameTrace.add(xy,dxdy);		xy.translate(dxdy); //move to new location		//collision stuff		try{			int bottom = bottom();			int top    = top();			if (bottom > top) {				//if the new location is on the ground, set it so entity isn't clipping into the ground				setPosition(getX(), (int)getY());			}			//vertical collision			if (top > bottom) {			    dxdy.setY(0f);			    setPosition(getX(), (int)getY() + 1);			}			int left   = left();			int right  = right();			//horizontal collision			if (left > right) {			    dxdy.setX(0f);			    setPosition((int)getX() + 1, getY());			}			if (right > left) {			    dxdy.setX(0f);			    setPosition((int)getX(), getY());			}		}catch(RuntimeException e){			System.out.println(e.getMessage());			frameTrace.printTrace();			throw e;		}	}	@Override	public void setPosition(float x, float y) {		xy.set(x,y);	}		/**	 * returns whether the entity is touching the ground	 * @return true if touching ground	 */	@Override	public boolean isOnGround() {		return bottom() > 0;	}	//collision checkers	private int top() {		Cell currentCell = MapLoader.getCurrentCell();		int count = 0;		for(float x=getX()+HITBOX_MARGIN;x<getX()+getWidth()-HITBOX_MARGIN;x+=0.5f){			if(currentCell.getTile((int) x, (int) getY()).lookupProperty(TileProperty.BLOCKED).getBoolean()){				++count;			}		}		return count;	}	private int bottom() {		Cell currentCell = MapLoader.getCurrentCell();		int count = 0;		for(float x=getX()+HITBOX_MARGIN;x<=getX()+getWidth()-HITBOX_MARGIN;x+=0.5f){			if(currentCell.getTile((int) x, (int) (getY() + getHeight())).lookupProperty(TileProperty.BLOCKED).getBoolean()){				++count;			}		}		return count;          	}	private int left() {		Cell currentCell = MapLoader.getCurrentCell();		int count = 0;		for(float y=getY()+HITBOX_MARGIN;y<getY()+getHeight()-HITBOX_MARGIN;y+=0.5f){			if(currentCell.getTile((int) getX(), (int) y).lookupProperty(TileProperty.BLOCKED).getBoolean()){				++count;			}		}		return count;	}	private int right() {		Cell currentCell = MapLoader.getCurrentCell();		int count = 0;		for(float y=getY()+HITBOX_MARGIN;y<getY()+getHeight()-HITBOX_MARGIN;y+=0.5f){			if(currentCell.getTile((int) (getX() + getWidth()), (int) y).lookupProperty(TileProperty.BLOCKED).getBoolean()){				++count;			}		}		return count;	}	public void scale(float sx, float f){		size.scale(sx,f);	}	/**	 * makes the entity jump. if it is falling, sets its vertical change to zero first.	 */	@Override	public void jump() {		dxdy.translate(0f,-JUMP_AMOUNT);	}	@Override	public void accelerate(float ddx, float ddy){		dxdy.translate(ddx,ddy);	}	@Override	public void setVelocity(float dx, float dy) {		dxdy.set(dx, dy);	}	public void translateSmooth(int time, float destX, float destY){		this.time = time; this.destX = destX; this.destY = destY; this.counter = 0; this.initX = getX(); this.initY = getY();	}	public void updateTranslateSmooth(){		counter ++;		if (counter < time){			accelerate((destX - initX)/(time/2 + counter),(destY - initY)/(time/2 + counter));		}	}	public boolean isMovingX(){		return Math.abs(dxdy.getX()) > 0.02f;	}		protected void renderSprite(Animation sprite, float offsetX, float offsetY){		//TODO: use somehow filter.darker or filter.lighter with layer		sprite.draw((int) ((getX() - 1)*Config.getTileSize()) + offsetX, (int) ((getY() - 1)*Config.getTileSize()) + offsetY, filter);	}		protected void renderSprite(Animation sprite, Position offset){		renderSprite(sprite, offset.getX(),offset.getY());	}		protected void renderSprite(AnimationContainer sprite){		renderSprite(sprite.getAnimation(),sprite.getOffset());	}	protected void renderHealthBar(int offsetY){		float perc = getHealthPercent();		Color c = new Color(1f - perc, perc, 0f);		float width = 0.8f*perc*getWidth();		float offsetX = (getWidth()-width)*0.5f;		Graphics g = new Graphics();		g.setColor(c);		g.fillRect((int) ((getX() + offsetX - 1)*Config.getTileSize()), (int) ((getY() - 1)*Config.getTileSize() + offsetY), (int) (width*Config.getTileSize()), 3);	}	@Override	public abstract void update(GameContainer gc, StateBasedGame sbg, int delta);	@Override	public AbstractEntity clone(){		return this;	}	@Override	public void stop_sounds(){		//left blank in case sounds are moved to this class.		//should be overridden to add class-specific sounds with a call to the super method.	}	@Override	public Rectangle getHitbox() {		return new Rectangle(xy.getX(), xy.getY(), size.getWidth(), size.getHeight());	}	public Color getFilter() {		return filter;	}	public void setFilter(Color filter) {		this.filter = filter;	}}