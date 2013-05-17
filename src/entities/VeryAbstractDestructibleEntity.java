package entities;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.GameContainer;

import map.MapLoader;

import utils.triggers.AugmentedTriggerEffect;

public abstract class VeryAbstractDestructibleEntity extends VeryAbstractStaticEntity implements DestructibleEntity {
	
private final Set<AugmentedTriggerEffect<? super DestructibleEntity>> deathTriggers = new HashSet<AugmentedTriggerEffect<? super DestructibleEntity>>();
	
	public VeryAbstractDestructibleEntity() { }
	
	/**
	 * Copy constructor
	 */
	protected VeryAbstractDestructibleEntity(VeryAbstractDestructibleEntity base){
		deathTriggers.addAll(base.deathTriggers);
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
	public abstract VeryAbstractDestructibleEntity clone();
	
}
