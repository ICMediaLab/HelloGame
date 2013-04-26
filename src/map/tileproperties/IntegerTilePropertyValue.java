package map.tileproperties;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class IntegerTilePropertyValue extends TilePropertyValue<Integer> {
	
	public IntegerTilePropertyValue(int value) {
		super(value);
	}
	
	@Override
	public void parse(String str) throws ParseException {
		try{
			set(Integer.parseInt(str));
		}catch(NumberFormatException e){
			throw new ParseException("Not an integer.");
		}
	}
	
	@Override
	public String toString() {
		return Integer.toString(get());
	}
}