package entities.aistates.decisiontree;

import java.util.HashMap;
import java.util.Map;

import utils.MapLoader;
import utils.Position;

import entities.Entity;

interface ConditionFloatVariable {
	float evaluate(Entity e);
}

abstract class ConditionFloatVariables {
	
	static ConditionFloatVariable getConditionVariable(String variable) throws NoSuchFieldException {
		if(variable.startsWith("$")){
			variable = variable.substring(1);
			ConditionFloatVariable ret = KnownFloatVariables.index.get(variable.toLowerCase());
			if(ret == null){
				throw new NoSuchFieldException("Field '" + variable + "' not found.");
			}
			return ret;
		}else{
			return new ConstantFloatValue(variable);
		}
	}
	
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
		public float evaluate(Entity e) {
			Entity player = MapLoader.getCurrentCell().getPlayer();
			switch(this){
			case ENTITY_HEALTH_ABS: return e.getHealth();
			case ENTITY_HEALTH_MAX: return e.getMaxHealth();
			case ENTITY_HEALTH_PERCENT: return e.getHealthPercent();
			case PLAYER_HEALTH_ABS: return player.getHealth();
			case PLAYER_HEALTH_MAX: return player.getMaxHealth();
			case PLAYER_HEALTH_PERCENT: return player.getHealthPercent();
			case ENTITY_DISTANCE_TO_PLAYER: return new Position(e.getX() - player.getX(), e.getY() - player.getY()).getMagnitudeSquared();
			}
			throw new IllegalArgumentException("No such variable function defined (" + this + ").");
		}
	}
}

class ConstantFloatValue implements ConditionFloatVariable {
	
	private final float val;
	
	ConstantFloatValue(String val) {
		this.val = Float.parseFloat(val);
	}

	@Override
	public float evaluate(Entity e) {
		return val;
	}
	
}
