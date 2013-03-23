package entities.aistates.decisiontree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import entities.IEntity;

class Condition {
	
	private static final Map<String,Integer> conditions = new HashMap<String,Integer>();
	
	static {
		conditions.put("lessthan", -1);
		conditions.put("equalto", 0);
		conditions.put("greaterthan", 1);
	}
	
	private final ConditionVariable lhs,rhs;
	private final int trueIfCompareTo;

	Condition(String cond) throws IllegalArgumentException, NoSuchFieldException {
		String[] parts = cond.split(":");
		System.out.println(Arrays.toString(parts));
		if(parts.length != 3){
			throw new IllegalArgumentException("Condition does not contain three components");
		}
		lhs = ConditionVariables.getConditionVariable(parts[0].trim());
		rhs = ConditionVariables.getConditionVariable(parts[2].trim());
		if(lhs == null || rhs == null){
			throw new IllegalArgumentException("Unknown condition");
		}
		trueIfCompareTo = conditions.get(parts[1].trim().toLowerCase());
	}
	
	boolean evaluate(IEntity e){
		return Math.signum(Float.compare(lhs.evaluate(e),rhs.evaluate(e))) == trueIfCompareTo;
	}
	
}
