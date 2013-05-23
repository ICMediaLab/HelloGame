package map.tileproperties;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * An representation of all Tile Properties with default values.<br />
 * Each property is associated with a string lookup, a {@link TilePropertyValue} class to store a type of data and finally a default value.<br />
 * New properties may not be created, {@link TilePropertyValue} instances for the variables given may be obtained through the 
 * {@code GetUndefinedValueInstance} method.
 */
public class TileProperty<K> {
	
	private static final Map<String,TileProperty<?>> map = new HashMap<String, TileProperty<?>>();
	
	public static final TileProperty<Boolean> BLOCKED = new TileProperty<Boolean>("BLOCKED",BooleanTilePropertyValue.FALSE);
	public static final TileProperty<Float> FRICTIONX = new TileProperty<Float>("FRICTIONX",new FloatTilePropertyValue(0.6f));
	public static final TileProperty<Float> FRICTIONY = new TileProperty<Float>("FRICTIONY",new FloatTilePropertyValue(0.04f));
	public static final TileProperty<Float> GRAVITY = new TileProperty<Float>("GRAVITY",new FloatTilePropertyValue(0.04f));
	public static final TileProperty<Boolean> LADDER = new TileProperty<Boolean>("LADDER",BooleanTilePropertyValue.FALSE);
	public static final TileProperty<Boolean> WATER = new TileProperty<Boolean>("WATER",BooleanTilePropertyValue.FALSE);
	
	private final TilePropertyValue<K> def;
	
	private TileProperty(String str, TilePropertyValue<K> def){
		this.def = def;
		map.put(str.toUpperCase(), this);
	}
	
	public TilePropertyValue<K> getUndefinedValueInstance() {
		return def;
	}

	public static Collection<TileProperty<?>> values() {
		return map.values();
	}

	public static TileProperty<?> parseTileProperty(String key) {
		return map.get(key.toUpperCase());
	}
}
