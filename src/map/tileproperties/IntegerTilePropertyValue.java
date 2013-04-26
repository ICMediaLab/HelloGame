package map.tileproperties;

import java.text.ParseException;

public class IntegerTilePropertyValue extends TilePropertyValue<Integer> {
	
	public IntegerTilePropertyValue(int value) {
		super(value);
	}
	
	@Override
	public void parse(String str) throws ParseException {
		try{
			set(Integer.parseInt(str));
		}catch(NumberFormatException e){
			throw new ParseException("Not an integer.",0);
		}
	}
	
	@Override
	public String toString() {
		return Integer.toString(get());
	}
}