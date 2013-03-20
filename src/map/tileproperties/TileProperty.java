package map.tileproperties;


/**
 * An enumeration of all Tile Properties with default values.
 */
public enum TileProperty {
	BLOCKED(BooleanTilePropertyValue.class);
	
	/**
	 * The default value if the property is not present in the XML file.
	 */
	private final Class<? extends TilePropertyValue> clazz;
	
	private TileProperty(Class<? extends TilePropertyValue> clazz){
		this.clazz = clazz;
	}
	
	public String toString(){
		return super.toString().toLowerCase();
	}

	/**
	 * Returns the default value of this property that should be applied if it is not defined.
	 */
	public TilePropertyValue getUndefinedValue() {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
