package utils;

import java.util.HashMap;

import org.newdawn.slick.tiled.TiledMap;

public class Tile {

	/**
	 * Holds a map of all properties held by this tile.
	 */
	private HashMap<TileProperty,String> properties = new HashMap<TileProperty, String>();
	
	/**
	 * The tile ID of this tile relative to the current cell.
	 */
	private final int tileID;
	
	private final int tileSize;
	
	/**
	 * Class to hold a HashMap of properties about the tile.
	 * Initialises the HashMap.
	 * @param i The tileID relative to the current cell, x and y coordinates.
	 */
	public Tile(int tileID, int tileSize) {
		this.tileID = tileID;
		this.tileSize = tileSize;
	}
	
	/**
	 * Gets the tilesize of this cell.
	 * @return the tilesize as an int.
	 */
	public int getTileSize() {
		return tileSize;
	}
	
	/**
	 * Parses the tile properties from the current cell using the tileID provided at initialisation.
	 * @param cell The current cell.
	 */
	public void parseTileProperties(TiledMap cell){
		for(TileProperty prop : TileProperty.values()){
			addProperty(prop,cell.getTileProperty(tileID, prop.toString(), prop.getUndefinedValue()));
		}
	}
	
	/**
	 * Adds a property to the map for this tile.
	 * @param k Key.
	 * @param v Value.
	 */
	public void addProperty(TileProperty k, String v) {
		properties.put(k, v);
	}
	
	/**
	 * Looks up a key in the map.
	 * @param k Key.
	 * @return The value.
	 */
	public String lookupProperty(TileProperty k) {
		return properties.get(k);
	}
}
