package entities.aistates.decisiontree;

import java.util.HashMap;
import java.util.Map;

import utils.MapLoader;

import entities.Entity;

interface ConditionBooleanVariable {
	
	boolean evaluate(Entity e);
	
}

abstract class ConditionBooleanVariables {
	
	static ConditionBooleanVariable getConditionVariable(String variable) throws NoSuchFieldException {
		if(variable.startsWith("$")){
			variable = variable.substring(1);
			ConditionBooleanVariable ret = KnownBooleanVariables.index.get(variable.toLowerCase());
			if(ret == null){
				throw new NoSuchFieldException("Field '" + variable + "' not found.");
			}
			return ret;
		}else{
			return new ConstantBooleanValue(variable);
		}
	}
	
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
		public boolean evaluate(Entity e) {
			Entity player = MapLoader.getCurrentCell().getPlayer();
			switch(this){
			case ENTITY_ON_GROUND: return e.isOnGround();
			case PLAYER_ON_GROUND: return player.isOnGround();
			}
			throw new IllegalArgumentException("No such variable function defined (" + this + ").");
		}
		
	}

	private static class ConstantBooleanValue implements ConditionBooleanVariable {
	
		private final boolean value;
		
		public ConstantBooleanValue(String variable) {
			this.value = Boolean.parseBoolean(variable);
		}
	
		@Override
		public boolean evaluate(Entity e) {
			return value;
		}
		
	}
}
