package map.tileproperties;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class BooleanTilePropertyValue extends TilePropertyValue<Boolean> {
	
	public BooleanTilePropertyValue(boolean value) { 
		super(value);
	}
	
	@Override
	public void parse(String str) throws ParseException {
		if(str.equalsIgnoreCase("true")){
			set(true);
		}else if(str.equalsIgnoreCase("false")){
			set(false);
		}else{
			throw new ParseException("Not a boolean.");
		}
	}
	
	@Override
	public String toString() {
		return Boolean.toString(get());
	}
}
