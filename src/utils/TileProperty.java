package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of all Tile Properties with default values.
 */
public enum TileProperty {
	BLOCKED("false"),ENEMY("null");
	
	private final String undef;
	
	private TileProperty(String undef){
		this.undef = undef;
	}
	
	private static final Map<String,TileProperty> tileProperties = new HashMap<String, TileProperty>();
	
	static{
		for(TileProperty prop : values()){
			tileProperties.put(prop.toString().toUpperCase(), prop);
		}
	}
	
	public TileProperty getTileProperty(String prop){
		return tileProperties.get(prop.toUpperCase()); 
	}
	
	public String toString(){
		return super.toString().toLowerCase();
	}

	public String getUndefinedValue() {
		return undef;
	}
	
	
}
