package map.tileproperties;

import java.text.ParseException;

public abstract class TilePropertyValue<T> implements Cloneable {
	
	private final T value;
	
	public TilePropertyValue(T value) {
		this.value = value;
	}
	
	public T get(){
		return value;
	}
	
	public abstract TilePropertyValue<T> parse(String str) throws ParseException;
}
