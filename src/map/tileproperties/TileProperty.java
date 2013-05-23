package map.tileproperties;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
	
	public static final TileProperty<Boolean> BLOCKED = new TileProperty<Boolean>("BLOCKED",BooleanTilePropertyValue.class,boolean.class,false);
	public static final TileProperty<Float> FRICTIONX = new TileProperty<Float>("FRICTIONX",FloatTilePropertyValue.class,float.class,0.6f);
	public static final TileProperty<Float> FRICTIONY = new TileProperty<Float>("FRICTIONY",FloatTilePropertyValue.class,float.class,0.04f);
	public static final TileProperty<Float> GRAVITY = new TileProperty<Float>("GRAVITY",FloatTilePropertyValue.class,float.class,0.04f);
	public static final TileProperty<Boolean> LADDER = new TileProperty<Boolean>("LADDER",BooleanTilePropertyValue.class,boolean.class,false);
	public static final TileProperty<Boolean> WATER = new TileProperty<Boolean>("WATER",BooleanTilePropertyValue.class,boolean.class,false);
	
	private final Constructor<? extends TilePropertyValue<K>> constructor;
	private final K def;
	
	private TileProperty(String str, Class<? extends TilePropertyValue<K>> clazz, Class<K> defclazz, K def){
		Constructor<? extends TilePropertyValue<K>> c = null;
		try {
			c = clazz.getConstructor(defclazz);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		this.constructor = c;
		this.def = def;
		map.put(str.toUpperCase(), this);
	}
	
	public TilePropertyValue<K> getUndefinedValueInstance() {
		try {
			return constructor.newInstance(def);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Collection<TileProperty<?>> values() {
		return map.values();
	}

	public static TileProperty<?> parseTileProperty(String key) {
		return map.get(key.toUpperCase());
	}
}
