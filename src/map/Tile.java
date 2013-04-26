package map;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import map.tileproperties.TileProperty;
import map.tileproperties.TilePropertyValue;

import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TileSet;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class Tile {

	/**
	 * Holds a map of all properties held by this tile.
	 */
	private final HashMap<TileProperty<?>,TilePropertyValue<?>> properties = new HashMap<TileProperty<?>, TilePropertyValue<?>>();

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
						parse2(TileProperty.parseTileProperty(key),(String) entry.getValue());
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
		for(TileProperty<?> prop : TileProperty.values()){
			parse1(prop, ts, l, x, y);
		}
	}
	
	private <T> void parse1(TileProperty<T> prop, TileSet ts, Layer l, int x, int y){
		TilePropertyValue<T> value = prop.getUndefinedValueInstance();
		if(ts != null){
			Properties props = ts.getProperties(l.getTileID(x, y));
			if (props != null) {
				String str = props.getProperty(prop.toString(), null);
				if(str != null){
					try{
						value.parse(str);
						put(prop,value);
					}catch(ParseException e){
						System.out.println("Recieved parse exception while parsing: " + str + " in layer " + l + " at " + x +"," + y);
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private <T> void parse2(TileProperty<T> prop, String str){
		TilePropertyValue<T> value = prop.getUndefinedValueInstance();
		try{
			value.parse(str);
		}catch(ParseException e){
			System.out.println("Recieved parse exception while parsing: " + str);
			e.printStackTrace();
		}
		put(prop, value);
	}
	
	/**
	 * Adds a property to the map for this tile.
	 * @param prop Key.
	 * @param value Value.
	 */
	public <K> void put(TileProperty<K> prop, TilePropertyValue<K> value) {
		properties.put(prop, value);
	}
	
	/**
	 * Looks up a key in the map.
	 * @param k Key.
	 * @return The value.
	 */
	public <K> TilePropertyValue<K> getTilePropertyValue(TileProperty<K> k) {
		@SuppressWarnings("unchecked")
		TilePropertyValue<K> res = (TilePropertyValue<K>) properties.get(k);
		return res == null ? k.getUndefinedValueInstance() : res;
	}
	
	/**
	 * Looks up a key in the map.
	 */
	public <K> K lookup(TileProperty<K> k) {
		@SuppressWarnings("unchecked")
		TilePropertyValue<K> res = (TilePropertyValue<K>) properties.get(k);
		return res == null ? k.getUndefinedValueInstance().get() : res.get();
	}
	
	@Override
	public String toString() {
		return properties.toString();
		
	}
}
