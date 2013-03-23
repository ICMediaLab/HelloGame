package entities.aistates.decisiontree;

import java.util.HashMap;
import java.util.Map;

import entities.Entity;

abstract class Condition {
	
	abstract boolean evaluate(Entity e);

	public static Condition getCondition(String condition) throws IllegalArgumentException, NoSuchFieldException {
		String[] parts = condition.split(":");
		if(parts.length == 3){
			return new ThreePartCondition(parts);
		}else if(parts.length == 1){
			return new SinglePartCondition(parts[0]);
		}
		throw new IllegalArgumentException("Misformed condition: " + condition);
	}
	
}

class ThreePartCondition extends Condition {
	
	private static final Map<String,Integer> conditions = new HashMap<String,Integer>();
	
	static {
		conditions.put("lessthan", -1);
		conditions.put("equalto", 0);
		conditions.put("greaterthan", 1);
	}
	
	private final ConditionFloatVariable lhs,rhs;
	private final int trueIfCompareTo;

	ThreePartCondition(String[] parts) throws IllegalArgumentException, NoSuchFieldException {
		lhs = ConditionFloatVariables.getConditionVariable(parts[0].trim());
		rhs = ConditionFloatVariables.getConditionVariable(parts[2].trim());
		if(lhs == null || rhs == null){
			throw new IllegalArgumentException("Unknown condition");
		}
		trueIfCompareTo = conditions.get(parts[1].trim().toLowerCase());
	}
	
	@Override
	boolean evaluate(Entity e){
		return Math.signum(Float.compare(lhs.evaluate(e),rhs.evaluate(e))) == trueIfCompareTo;
	}
}

class SinglePartCondition extends Condition {
	
	private final ConditionBooleanVariable value;

	public SinglePartCondition(String part) throws NoSuchFieldException {
		value = ConditionBooleanVariables.getConditionVariable(part);
	}

	@Override
	boolean evaluate(Entity e) {
		return value.evaluate(e);
	}
	
}
