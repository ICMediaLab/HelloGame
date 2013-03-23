package entities.aistates.decisiontree;

import java.util.HashMap;
import java.util.Map;

import entities.IEntity;

interface ConditionVariable {
	float evaluate(IEntity e);
}

abstract class ConditionVariables {
	
	static ConditionVariable getConditionVariable(String variable) throws NoSuchFieldException {
		if(variable.startsWith("$")){
			variable = variable.substring(1);
			ConditionVariable ret = KnownVariables.index.get(variable.toLowerCase());
			if(ret == null){
				throw new NoSuchFieldException("Field '" + variable + "' not found.");
			}
			return ret;
		}else{
			return new ConstantValue(variable);
		}
	}
	
	private enum KnownVariables implements ConditionVariable {
		ENTITY_HEALTH_ABS("health"),
		ENTITY_HEALTH_MAX("health_max"),
		ENTITY_HEALTH_PERCENT("health_percent");
				
		private static final Map<String,ConditionVariable> index = new HashMap<String, ConditionVariable>();		
		
		static {
			for(KnownVariables cv : values()){
				index.put(cv.str.toLowerCase(), cv);
			}
		}
		
		private final String str;
		
		private KnownVariables(String str) {
			this.str = str;
		}

		@Override
		public float evaluate(IEntity e) {
			switch(this){
			case ENTITY_HEALTH_ABS: return e.getHealth();
			case ENTITY_HEALTH_MAX: return e.getMaxHealth();
			case ENTITY_HEALTH_PERCENT: return e.getHealthPercent();
			}
			throw new IllegalArgumentException("No such variable.");
		}
	}
}

class ConstantValue implements ConditionVariable {
	
	private final float val;
	
	ConstantValue(String val) {
		this.val = Float.parseFloat(val);
	}

	@Override
	public float evaluate(IEntity e) {
		return val;
	}
	
}