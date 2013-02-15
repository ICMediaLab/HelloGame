package utils;


/**
 * An enumeration of all Tile Properties with default values.
 */
public enum TileProperty {
	BLOCKED("false"),ENEMY("null");
	
	/**
	 * The default value if the property is not present in the XML file.
	 */
	private final String undef;
	
	private TileProperty(String undef){
		this.undef = undef;
	}
	
	public String toString(){
		return super.toString().toLowerCase();
	}

	/**
	 * Returns the default value of this property that should be applied if it is not defined.
	 */
	public String getUndefinedValue() {
		return undef;
	}
	
	
}
