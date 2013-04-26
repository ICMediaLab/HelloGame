package map.tileproperties;

import java.text.ParseException;

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
			throw new ParseException("Not a boolean.", 0);
		}
	}
	
	@Override
	public String toString() {
		return Boolean.toString(get());
	}
}
