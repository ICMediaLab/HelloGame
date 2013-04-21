package conditiontree;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import map.MapLoader;

import entities.MovingEntity;
import entities.players.Player;

/**
 * An abstract interface for boolean variables to express
 */
interface ConditionBooleanVariable extends Serializable {
	
	/**
	 * Returns the evaluation of this variable with respect to the entity provided.
	 * @throws IllegalArgumentException If the variable definition is missing.
	 */
	boolean evaluate(MovingEntity e) throws IllegalArgumentException;
	
}

/**
 * An abstract class to hold static methods and implementations of classes
 */
abstract class ConditionBooleanVariables {
	
	/**
	 * Returns the conditional variable represented by the string specified.
	 * @throws NoSuchFieldException If no such field exists (or "true" or "false")
	 */
	static ConditionBooleanVariable getConditionVariable(String variable) throws NoSuchFieldException {
		//checks if the variable begins with a dollar sign ($, i.e. it is a variable)
		if(variable.startsWith("$")){
			variable = variable.substring(1);
			//attempt to find the variable in the known variables, exception thrown if nothing is found
			ConditionBooleanVariable ret = KnownBooleanVariables.index.get(variable.toLowerCase());
			if(ret == null){
				throw new NoSuchFieldException("Field '" + variable + "' not found.");
			}
			return ret;
		}else{
			//attempt to parse as a constant
			return new ConstantBooleanValue(variable);
		}
	}
	
	/**
	 * An enumeration of all specified boolean variables for conditionals.
	 * It is a requirement of this class that all specified values have a case in the evaluate method.
	 */
	private enum KnownBooleanVariables implements ConditionBooleanVariable {
		ENTITY_ON_GROUND("on_ground"),
		PLAYER_ON_GROUND("player_on_ground");
		
		private static final Map<String,KnownBooleanVariables> index = new HashMap<String,KnownBooleanVariables>();
		
		static {
			for(KnownBooleanVariables kbv : values()){
				index.put(kbv.str, kbv);
			}
		}

		private final String str;
		
		private KnownBooleanVariables(String str){
			this.str = str;
		}

		@Override
		public boolean evaluate(MovingEntity e) throws IllegalArgumentException {
			Player player = MapLoader.getCurrentCell().getPlayer();
			switch(this){
			case ENTITY_ON_GROUND: return e.isOnGround();
			case PLAYER_ON_GROUND: return player.isOnGround();
			}
			throw new IllegalArgumentException("No such variable function defined (" + this + ").");
		}
		
	}

	/**
	 * A class for storing constant boolean values (i.e. true or false)
	 */
	private static class ConstantBooleanValue implements ConditionBooleanVariable {
	
		private static final long serialVersionUID = -8428540330974511716L;
		
		private final boolean value;
		
		public ConstantBooleanValue(String variable) {
			this.value = Boolean.parseBoolean(variable);
		}
	
		@Override
		public boolean evaluate(MovingEntity e) {
			return value;
		}
		
	}
}
