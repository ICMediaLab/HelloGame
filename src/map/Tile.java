package map;

import java.util.HashMap;

import javax.naming.OperationNotSupportedException;


import map.tileproperties.TileProperty;
import map.tileproperties.TilePropertyValue;

import org.newdawn.slick.tiled.TiledMap;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class Tile {

	/**
	 * Holds a map of all properties held by this tile.
	 */
	private final HashMap<TileProperty,TilePropertyValue> properties = new HashMap<TileProperty, TilePropertyValue>();
	
	/**
	 * The tile ID of this tile relative to the current cell.
	 */
	private final int tileID;
	
	/**
	 * Class to hold a HashMap of properties about the tile.
	 * Initialises the HashMap.
	 * @param i The tileID relative to the current cell, x and y coordinates.
	 */
	public Tile(int tileID) {
		this.tileID = tileID;
	}
	
	/**
	 * Class to hold a HashMap of properties about the tile.
	 * Initialises the HashMap and parses the tile properties.
	 * @param i The tileID relative to the current cell, x and y coordinates.
	 */
	public Tile(Cell cell, int tileID) {
		this.tileID = tileID;
		parseTileProperties(cell);
	}
	
	/**
	 * Parses the tile properties from the current cell using the tileID provided at initialisation.
	 * @param cell The current cell.
	 */
	public void parseTileProperties(TiledMap cell){
		for(TileProperty prop : TileProperty.values()){
			TilePropertyValue value = prop.getUndefinedValue();
			String newValue = cell.getTileProperty(tileID, prop.toString(), value.toString());
			try{
				value.parse(newValue);
			}catch(OperationNotSupportedException e){
				e.printStackTrace();
			}catch(ParseException e){
				System.out.println("Recieved parse exception while parsing: " + newValue + " in cell " + cell);
				e.printStackTrace();
			}
			addProperty(prop,value);
		}
	}
	
	/**
	 * Adds a property to the map for this tile.
	 * @param k Key.
	 * @param v Value.
	 */
	public void addProperty(TileProperty k, TilePropertyValue v) {
		properties.put(k, v);
	}
	
	/**
	 * Looks up a key in the map.
	 * @param k Key.
	 * @return The value.
	 */
	public TilePropertyValue lookupProperty(TileProperty k) {
		return properties.get(k);
	}
	
	@Override
	public String toString() {
		return properties.toString();
		
	}
}
