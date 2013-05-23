package map.tileproperties;

import java.text.ParseException;

public class BooleanTilePropertyValue extends TilePropertyValue<Boolean> {
	
	public static final BooleanTilePropertyValue TRUE = new BooleanTilePropertyValue(true), FALSE = new BooleanTilePropertyValue(false);
	
	private BooleanTilePropertyValue(boolean value) {
		super(value);
	}
	
	@Override
	public TilePropertyValue<Boolean> parse(String str) throws ParseException {
		if(str.equalsIgnoreCase("true")){
			return TRUE; 
		}else if(str.equalsIgnoreCase("false")){
			return FALSE;
		}else{
			throw new ParseException("Not a boolean.", 0);
		}
	}
	
	@Override
	public String toString() {
		return Boolean.toString(get());
	}
}
