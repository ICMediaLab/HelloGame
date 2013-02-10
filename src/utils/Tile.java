package utils;

import java.util.HashMap;

public class Tile {

	private HashMap<String,String> properties;
	
	/**
	 * Class to hold a HashMap of properties about the tile.
	 * Initialises the HashMap.
	 */
	public Tile() {
		properties = new HashMap<String, String>();
	}
	
	/**
	 * Adds a property to the map for this tile.
	 * @param k: Key.
	 * @param v: Value.
	 */
	public void addProperty(String k, String v) {
		properties.put(k, v);
	}
	
	/**
	 * Looks up a key in the map.
	 * @param k: Key.
	 * @return The value.
	 */
	public String lookupProperty(String k) {
		return properties.get(k);
	}
}
