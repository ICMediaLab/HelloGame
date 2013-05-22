package utils.triggers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import map.CellObjectParser;

import entities.DestructibleEntity;
import entities.Entity;

public class CompositeTrigger implements AugmentedTriggerEffect<Entity> {
	
	private final Set<Entity> untriggeredSources = new HashSet<Entity>(), defaultUntriggeredSources = new HashSet<Entity>();
	private final Set<AugmentedTriggerEffect<?>> untriggeredTriggers = new HashSet<AugmentedTriggerEffect<?>>(), defaultUntriggeredTriggers = new HashSet<AugmentedTriggerEffect<?>>();
	
	private final Set<VoidAugmentedTriggerEffect<?>> effects = new HashSet<VoidAugmentedTriggerEffect<?>>();
	
	private final String id;
	private final boolean ensureNotRemoved, reset;
	
	public CompositeTrigger(String id, boolean ensureNotRemoved, boolean resetOnTriggered) {
		this.id = id;
		this.ensureNotRemoved = ensureNotRemoved;
		reset = resetOnTriggered;
	}
	
	public String getId(){
		return id;
	}
	
	public void addExpected(Entity src){
		if(src == null){
			throw new NullPointerException();
		}
		if(src != null){
			defaultUntriggeredSources.add(src);
			untriggeredSources.add(src);
		}
	}
	
	public void addExpected(AugmentedTriggerEffect<?> src){
		if(src == null){
			throw new NullPointerException();
		}
		if(src != null){
			untriggeredTriggers.add(src);
			defaultUntriggeredTriggers.add(src);
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
		//System.out.println("triggered by " + k);
		if(untriggeredSources.remove(k)){
			check();
		}
		if(k instanceof DestructibleEntity){
			DestructibleEntity dk = (DestructibleEntity) k;
			//System.out.println("checking for aliasing of " + k + " in " + untriggeredSources);
			for(Iterator<Entity> it = untriggeredSources.iterator();it.hasNext();){
				Entity e = it.next();
				if(dk.cloneOf(e)){
					//System.out.println("aliasing found");
					it.remove();
					check();
				}
			}
		}
	}
	
	public void check(){
		//System.out.println("checking");
		if(untriggeredSources.isEmpty() && untriggeredTriggers.isEmpty() && (!ensureNotRemoved || CellObjectParser.containsTrigger(this))){
			triggerEffects();
		}
	}

	public void triggerEffects() {
		//System.out.println("triggering");
		if(reset){
			untriggeredSources.addAll(defaultUntriggeredSources);
			untriggeredTriggers.addAll(defaultUntriggeredTriggers);
		}
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
