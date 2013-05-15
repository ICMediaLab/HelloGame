package utils.triggers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BasicTriggerEffect implements TriggerEffect {
	
	private final Set<TriggerSource> untriggeredSources = new HashSet<TriggerSource>();
	private final Set<TriggerSource> triggeredSources = new HashSet<TriggerSource>();
	
	@Override
	public void addTriggerSource(TriggerSource t) {
		System.out.println(this + "\t" + t);
		untriggeredSources.add(t);
	}
	
	@Override
	public void triggeredSource(TriggerSource t) {
		System.out.println(this + "\t" + t);
		if(untriggeredSources.remove(t)){
			System.out.println("yay");
			triggeredSources.add(t);
		}
	}
	
	public int getTotalTriggered(){
		return triggeredSources.size();
	}
	
	public int getTotalUntriggered(){
		return untriggeredSources.size();
	}
	
	public int getTotalTriggerSources(){
		return getTotalTriggered() + getTotalUntriggered();
	}
	
	public Iterator<TriggerSource> triggeredSourceIterator(){
		return getTriggeredSources().iterator();
	}
	
	public Set<TriggerSource> getTriggeredSources() {
		return Collections.unmodifiableSet(triggeredSources);
	}
	
	public Iterator<TriggerSource> untriggeredSourceIterator(){
		return getUntriggeredSources().iterator();
	}
	
	public Set<TriggerSource> getUntriggeredSources() {
		return Collections.unmodifiableSet(untriggeredSources);
	}
}
