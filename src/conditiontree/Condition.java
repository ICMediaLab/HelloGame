package conditiontree;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import entities.MovingEntity;

abstract class Condition implements Serializable {
	
	private static final long serialVersionUID = 1039835617677359706L;

	abstract boolean evaluate(MovingEntity e);

	/**
	 * Returns a new conditional evaluate-able node.
	 * @param condition The string to be parsed when creating the branch.
	 * @throws IllegalArgumentException If a condition is malformed.
	 * @throws NoSuchFieldException If a specified condition parameter does not exist.
	 */
	public static Condition getCondition(String condition) throws IllegalArgumentException, NoSuchFieldException {
		//split disjuncts (or statements) denoted by | characters
		String[] juncts = condition.split("\\|");
		if(juncts.length > 1){
			//if multiple disjuncts exist, create a container for them.
			return new DisjunctiveConditions(juncts);
		}
		return parseFromConjuncts(condition);
	}
		
	private static Condition parseFromConjuncts(String condition) throws IllegalArgumentException, NoSuchFieldException{
		String[] juncts = condition.split("&");
		if(juncts.length > 1){
			//if multiple disjuncts exist, create a container for them.
			return new ConjunctiveConditions(juncts);
		}
		return parseFromCondition(condition);
	}
	
	private static Condition parseFromCondition(String condition) throws IllegalArgumentException, NoSuchFieldException{
		//attempt to split condition into three parts (lhs :operator: rhs)
		String[] parts = condition.split(":");
		if(parts.length == 3){
			//if an operator is present, three parts must exist
			return new ThreePartCondition(parts);
		}else if(parts.length == 1){
			//if no operator exists, attempt to create a boolean variable or constant
			return new SinglePartCondition(parts[0]);
		}
		throw new IllegalArgumentException("Malformed condition: " + condition);
	}

	/**
	 * A class for holding disjuncts of conditions
	 */
	private static class DisjunctiveConditions extends Condition {
		
		private static final long serialVersionUID = 3929631799314822039L;
		
		private final Condition[] conditions;
		
		DisjunctiveConditions(String[] conditionStrs) throws IllegalArgumentException, NoSuchFieldException {
			conditions = new Condition[conditionStrs.length];
			for(int i=0;i<conditionStrs.length;i++){
				conditions[i] = Condition.parseFromConjuncts(conditionStrs[i]);
			}
		}
		
		@Override
		boolean evaluate(MovingEntity e) {
			for(Condition c : conditions){
				if(c.evaluate(e)){
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * A class for holding conjuncts of conditions
	 */
	private static class ConjunctiveConditions extends Condition {
		
		private static final long serialVersionUID = 8138296072122100959L;
		
		private final Condition[] conditions;
		
		ConjunctiveConditions(String[] conditionStrs) throws IllegalArgumentException, NoSuchFieldException {
			conditions = new Condition[conditionStrs.length];
			for(int i=0;i<conditionStrs.length;i++){
				conditions[i] = Condition.parseFromCondition(conditionStrs[i]);
			}
		}
		
		@Override
		boolean evaluate(MovingEntity e) {
			for(Condition c : conditions){
				if(!c.evaluate(e)){
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * A class for holding traditional comparison conditions
	 */
	private static class ThreePartCondition extends Condition {
		
		private static final long serialVersionUID = 3530120957389727516L;
		
		private static final Map<String,Integer> conditions = new HashMap<String,Integer>();
		
		static {
			conditions.put("lessthan", -1);
			conditions.put("equalto", 0);
			conditions.put("greaterthan", 1);
		}
		
		private final ConditionFloatVariable lhs,rhs;
		private final int trueIfCompareTo;
		
		/**
		 * Create a new three-part condition.
		 * @param parts The parts of the conditional in order.
		 * @throws IllegalArgumentException If the conditions parse to be null.
		 * @throws NoSuchFieldException If the conditions reference a non-existent variable.
		 */
		ThreePartCondition(String[] parts) throws IllegalArgumentException, NoSuchFieldException {
			//parse the left and right hand sides of the condition based on floating variables
			lhs = ConditionFloatVariables.getConditionVariable(parts[0]);
			rhs = ConditionFloatVariables.getConditionVariable(parts[2]);
			if(lhs == null || rhs == null){
				throw new IllegalArgumentException("Unknown condition");
			}
			//set the comparison result using signum
			trueIfCompareTo = conditions.get(parts[1].toLowerCase());
		}
		
		@Override
		boolean evaluate(MovingEntity e){
			return Math.signum(Float.compare(lhs.evaluate(e),rhs.evaluate(e))) == trueIfCompareTo;
		}
	}
	
	/**
	 * A class for holding true/false or boolean-evaluating variables
	 */
	private static class SinglePartCondition extends Condition {
		
		private static final long serialVersionUID = 6355461521473160107L;
		
		private final ConditionBooleanVariable value;
	
		/**
		 * Creates a new single-part conditional.
		 * @param part The string to base the conditional on.
		 * @throws NoSuchFieldException If the condition references a non-existent variable 
		 * 			and does not reference "true"/"false"
		 */
		public SinglePartCondition(String part) throws NoSuchFieldException {
			//parse the value based on boolean variables
			value = ConditionBooleanVariables.getConditionVariable(part);
		}
	
		@Override
		boolean evaluate(MovingEntity e) {
			return value.evaluate(e);
		}
		
	}
}
