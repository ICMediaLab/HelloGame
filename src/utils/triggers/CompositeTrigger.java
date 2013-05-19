package utils.triggers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import map.CellObjectParser;

import entities.DestructibleEntity;
import entities.Entity;

public class CompositeTrigger implements AugmentedTriggerEffect<Entity> {
	
	private final Set<Entity> untriggeredSources = new HashSet<Entity>();
	private final Set<AugmentedTriggerEffect<?>> untriggeredTriggers = new HashSet<AugmentedTriggerEffect<?>>();
	
	private final Set<VoidAugmentedTriggerEffect<?>> effects = new HashSet<VoidAugmentedTriggerEffect<?>>();
	
	private final String id;
	private final boolean ensureNotRemoved;
	
	public CompositeTrigger(String id, boolean ensureNotRemoved) {
		this.id = id;
		this.ensureNotRemoved = ensureNotRemoved;
	}
	
	public String getId(){
		return id;
	}
	
	public void addExpected(Entity src){
		if(src == null){
			throw new NullPointerException();
		}
		if(src != null){
			untriggeredSources.add(src);
		}
	}
	
	public void addExpected(AugmentedTriggerEffect<?> src){
		if(src == null){
			throw new NullPointerException();
		}
		if(src != null){
			untriggeredTriggers.add(src);
		}
	}
	
	public void addEffect(VoidAugmentedTriggerEffect<?> effect){
		if(effect == null){
			throw new NullPointerException();
		}
		effects.add(effect);
	}
	
	@Override
	public void triggered(Entity k) {
		if(untriggeredSources.remove(k)){
			check();
		}
		if(k instanceof DestructibleEntity){
			DestructibleEntity dk = (DestructibleEntity) k;
			for(Iterator<Entity> it = untriggeredSources.iterator();it.hasNext();){
				Entity e = it.next();
				if(dk.cloneOf(e)){
					it.remove();
					check();
				}
			}
		}
	}
	
	public void check(){
		if(untriggeredSources.isEmpty() && untriggeredTriggers.isEmpty() && (!ensureNotRemoved || CellObjectParser.containsTrigger(this))){
			triggerEffects();
		}
	}

	public void triggerEffects() {
		for(VoidAugmentedTriggerEffect<?> e : effects){
			e.triggered();
		}
	}

	public void triggered(AugmentedTriggerEffect<?> t) {
		if(untriggeredTriggers.remove(t)){
			check();
		}
	}
}
