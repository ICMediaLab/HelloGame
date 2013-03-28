package entities.aistates.decisiontree;

import java.util.HashMap;
import java.util.Map;

import utils.MapLoader;
import utils.Position;

import entities.Entity;

/**
 * An abstract interface for storing non-boolean variables or values.
 */
interface ConditionFloatVariable {
	
	/**
	 * Returns the evaluation of this variable with respect to the entity provided.
	 * @throws IllegalArgumentException If the variable definition is missing.
	 */
	float evaluate(Entity e) throws IllegalArgumentException;
}

/**
 * An abstract class to hold static methods and implementations of classes
 */
abstract class ConditionFloatVariables {
	
	/**
	 * Returns the conditional variable represented by the string specified.
	 * @throws NoSuchFieldException If no such field exists
	 */
	static ConditionFloatVariable getConditionVariable(String variable) throws NoSuchFieldException {
		//checks if the variable begins with a dollar sign ($, i.e. it is a variable)
		if(variable.startsWith("$")){
			variable = variable.substring(1);
			//attempt to find the variable in the known variables, exception thrown if nothing is found
			ConditionFloatVariable ret = KnownFloatVariables.index.get(variable.toLowerCase());
			if(ret == null){
				throw new NoSuchFieldException("Field '" + variable + "' not found.");
			}
			return ret;
		}else{
			//attempt to parse as a constant
			return new ConstantFloatValue(variable);
		}
	}
	
	/**
	 * An enumeration of all specified floating variables for conditionals.
	 * It is a requirement of this class that all specified values have a case in the evaluate method.
	 */
	private enum KnownFloatVariables implements ConditionFloatVariable {
		ENTITY_HEALTH_ABS("health"),
		ENTITY_HEALTH_MAX("health_max"),
		ENTITY_HEALTH_PERCENT("health_percent"),
		PLAYER_HEALTH_ABS("player_health"),
		PLAYER_HEALTH_MAX("player_health_max"),
		PLAYER_HEALTH_PERCENT("player_health_percent"),
		ENTITY_DISTANCE_TO_PLAYER("player_distance");
		
		private static final Map<String,ConditionFloatVariable> index = new HashMap<String, ConditionFloatVariable>();		
		
		static {
			for(KnownFloatVariables cv : values()){
				index.put(cv.str.toLowerCase(), cv);
			}
		}
		
		private final String str;
		
		private KnownFloatVariables(String str) {
			this.str = str;
		}
		
		@Override
		public float evaluate(Entity e) throws IllegalArgumentException {
			Entity player = MapLoader.getCurrentCell().getPlayer();
			switch(this){
			case ENTITY_HEALTH_ABS: return e.getHealth();
			case ENTITY_HEALTH_MAX: return e.getMaxHealth();
			case ENTITY_HEALTH_PERCENT: return e.getHealthPercent();
			case PLAYER_HEALTH_ABS: return player.getHealth();
			case PLAYER_HEALTH_MAX: return player.getMaxHealth();
			case PLAYER_HEALTH_PERCENT: return player.getHealthPercent();
			case ENTITY_DISTANCE_TO_PLAYER: return new Position(e.getCentreX() - player.getCentreX(), e.getCentreY() - player.getCentreY()).getMagnitudeSquared();
			}
			throw new IllegalArgumentException("No such variable function defined (" + this + ").");
		}
	}
	
	/**
	 * A class for storing constant floating-point values (e.g. 0 or 1.45)
	 */
	private static class ConstantFloatValue implements ConditionFloatVariable {
		
		private final float val;
		
		ConstantFloatValue(String val) {
			this.val = Float.parseFloat(val);
		}
		
		@Override
		public float evaluate(Entity e) {
			return val;
		}
	}
}
