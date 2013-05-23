package entities;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import map.MapLoader;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;

import utils.triggers.AugmentedTriggerEffect;

public abstract class VeryAbstractDestructibleEntity<S extends Shape> extends VeryAbstractStaticEntity<S> implements DestructibleEntity {
	
	/**
	 * Used to keep track of what is a clone of what. This is required so the trigger system has at least a mild comprehension of the source of the triggers. :/
	 */
	private static final Map<WeakReference<DestructibleEntity>,Set<WeakReference<DestructibleEntity>>> aliasMap = new HashMap<WeakReference<DestructibleEntity>, Set<WeakReference<DestructibleEntity>>>();
	
	private final Set<AugmentedTriggerEffect<? super DestructibleEntity>> deathTriggers = new HashSet<AugmentedTriggerEffect<? super DestructibleEntity>>();
	
	private final WeakReference<DestructibleEntity> selfRef = new WeakReference<DestructibleEntity>(this);
	
	public VeryAbstractDestructibleEntity(S hitbox) {
		super(hitbox);
		aliasMap.put(selfRef, new HashSet<WeakReference<DestructibleEntity>>());
	}
	
	/**
	 * Copy constructor
	 */
	protected VeryAbstractDestructibleEntity(S hitbox, VeryAbstractDestructibleEntity<S> base){
		super(hitbox);
		deathTriggers.addAll(base.deathTriggers);
		Set<WeakReference<DestructibleEntity>> set = aliasMap.get(base.getWeakReference());
		set.add(selfRef);
		aliasMap.put(selfRef, new HashSet<WeakReference<DestructibleEntity>>());
	}
	
	@Override
	public void addDeathTrigger(AugmentedTriggerEffect<? super DestructibleEntity> t) {
		deathTriggers.add(t);
	}
	
	@Override
	public void die() {
		MapLoader.getCurrentCell().removeDestructibleEntity(this);
		for(AugmentedTriggerEffect<? super DestructibleEntity> t : deathTriggers){
			t.triggered(this);
		}
	}
	
	@Override
	public final boolean isDead() {
		return getHealth() <= 0;
	}
	
	@Override
	public final float getHealthPercent(){
		return (float)getHealth()/getMaxHealth();
	}
	
	@Override
	public void update(GameContainer gc) {
		if(isDead()){
			die();
		}
	}
	
	@Override
	public abstract VeryAbstractDestructibleEntity<S> clone();
	
	@Override
	public boolean cloneOf(Entity o) {
		// checks the transitive closure of the alias mapping to check 
		// pre-computing the closure might be a good idea, but likely no performance/understandability gain
		if(o != null && o instanceof DestructibleEntity) {
			Set<WeakReference<DestructibleEntity>> set = aliasMap.get(((DestructibleEntity) o).getWeakReference());
			// should never return a null set due to it being added in both constructors
			for(WeakReference<DestructibleEntity> de : set){
				if(de == selfRef || cloneOf(de.get())){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public WeakReference<DestructibleEntity> getWeakReference() {
		return selfRef;
	}
	
}
