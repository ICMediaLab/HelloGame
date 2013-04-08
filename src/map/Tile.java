package map;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import javax.naming.OperationNotSupportedException;

import map.tileproperties.TileProperty;
import map.tileproperties.TilePropertyValue;

import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TileSet;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class Tile {

	/**
	 * Holds a map of all properties held by this tile.
	 */
	private final HashMap<TileProperty,TilePropertyValue> properties = new HashMap<TileProperty, TilePropertyValue>();

	/**
	 * Class to hold a HashMap of properties about the tile.<br />
	 * Automatically initialises the property map with the data provided.
	 * @param cell The cell to which this tile belongs
	 * @param x The x position of this tile
	 * @param y The y position of this tile
	 */
	public Tile(Cell cell, int x, int y) {
		this(cell,0,x,y);
	}
	
	/**
	 * Class to hold a HashMap of properties about the tile.<br />
	 * Automatically initialises the property map with the data provided.
	 * @param cell The cell to which this tile belongs
	 * @param layer The layer of this tile
	 * @param x The x position of this tile
	 * @param y The y position of this tile
	 */
	public Tile(Cell cell, int layer, int x, int y){
		Layer l = cell.getLayer(layer);
		System.out.println("Loading " + x + ", "+ y);
		TileSet ts = l.data[x][y][0] < 0 ? null : cell.getTileSet(l.data[x][y][0]);
		
		//parse tileset properties
		if(ts != null){
			try {
				Field f = ts.getClass().getDeclaredField("tilesetProperties");
				f.setAccessible(true);
				Object res = f.get(ts);
				if(res != null && res instanceof Properties){
					Properties p = (Properties) res;
					for(Entry<Object, Object> entry : p.entrySet()){
						String key = (String) entry.getKey();
						TileProperty prop = TileProperty.parseTileProperty(key);
						String str = (String) entry.getValue();
						TilePropertyValue value = prop.getUndefinedValue();
						try{
							value.parse(str);
						}catch(OperationNotSupportedException e){
							e.printStackTrace();
						}catch(ParseException e){
							System.out.println("Recieved parse exception while parsing: " + str + " in cell " + cell);
							e.printStackTrace();
						}
						put(prop, value);
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		//parse individual tile properties
		for(TileProperty prop : TileProperty.values()){
			TilePropertyValue value = prop.getUndefinedValue();
			if(ts != null){
				Properties props = ts.getProperties(l.getTileID(x, y));
				if (props != null) {
					String str = props.getProperty(prop.toString(), null);
					if(str != null){
						try{
							value.parse(str);
							put(prop,value);
						}catch(OperationNotSupportedException e){
							e.printStackTrace();
						}catch(ParseException e){
							System.out.println("Recieved parse exception while parsing: " + str + " in cell " + cell);
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Adds a property to the map for this tile.
	 * @param k Key.
	 * @param v Value.
	 */
	public void put(TileProperty k, TilePropertyValue v) {
		properties.put(k, v);
	}
	
	/**
	 * Looks up a key in the map.
	 * @param k Key.
	 * @return The value.
	 */
	public TilePropertyValue lookup(TileProperty k) {
		TilePropertyValue res = properties.get(k);
		return res == null ? k.getUndefinedValue() : res;
	}
	
	@Override
	public String toString() {
		return properties.toString();
		
	}
}
