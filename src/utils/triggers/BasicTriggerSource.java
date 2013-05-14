package utils.triggers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BasicTriggerSource implements TriggerSource {
	
	private final Set<TriggerEffect> triggerEffects = new HashSet<TriggerEffect>();
	
	@Override
	public void addTriggerEffect(TriggerEffect t) {
		triggerEffects.add(t);
	}
	
	public Collection<TriggerEffect> getTriggerEffects(){
		return Collections.unmodifiableCollection(triggerEffects);
	}
	
	public Iterator<TriggerEffect> triggerEffectIterator(){
		return getTriggerEffects().iterator();
	}
	
	public void trigger(){
		for(TriggerEffect t : triggerEffects){
			t.triggeredSource(this);
		}
	}
}
