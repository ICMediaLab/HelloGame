package map.tileproperties;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public abstract class TilePropertyValue<T> {
	
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
