package map.tileproperties;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


/**
 * An enumeration of all Tile Properties with default values.
 */
public enum TileProperty {
	BLOCKED(BooleanTilePropertyValue.class,boolean.class,false),
	FRICTIONX(FloatTilePropertyValue.class,float.class,0.6f),
	FRICTIONY(FloatTilePropertyValue.class,float.class,0.04f),
	GRAVITY(FloatTilePropertyValue.class,float.class,0.04f);
	
	private static final Map<String,TileProperty> map = new HashMap<String,TileProperty>();
	
	static {
		for(TileProperty tp : values()){
			map.put(tp.toString().toLowerCase(), tp);
		}
	}
	
	/**
	 * The default value if the property is not present in the XML file.
	 */
	private final Class<? extends TilePropertyValue> clazz;
	private final Class<?> defclazz;
	private final Object def;
	
	private TileProperty(Class<? extends TilePropertyValue> clazz, Class<?> defclazz, Object def){
		this.clazz = clazz;
		this.defclazz = defclazz;
		this.def = def;
	}
	
	public String toString(){
		return super.toString().toLowerCase();
	}
	
	public static TileProperty parseTileProperty(String str){
		return map.get(str.toLowerCase());
	}

	/**
	 * Returns the default value of this property that should be applied if it is not defined.
	 */
	public TilePropertyValue getUndefinedValue() {
		try {
			return clazz.getConstructor(defclazz).newInstance(def);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
