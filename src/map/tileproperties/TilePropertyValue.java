package map.tileproperties;

import java.text.ParseException;

public abstract class TilePropertyValue<T> implements Cloneable {
	
	private T value;
	
	public TilePropertyValue(T value) {
		set(value);
	}
	
	public T get(){
		return value;
	}
	
	public void set(T value){
		if(value != null){
			this.value = value;
		}
	}
	
	public abstract void parse(String str) throws ParseException;
}
