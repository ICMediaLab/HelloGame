package map.tileproperties;

import java.text.ParseException;

public class FloatTilePropertyValue extends TilePropertyValue<Float> {
	
	public FloatTilePropertyValue(float value) {
		super(value);
	}
	
	@Override
	public void parse(String str) throws ParseException {
		try{
			set(Float.parseFloat(str));
		}catch(NumberFormatException e){
			throw new ParseException("Not a float.",0);
		}
	}
	
	@Override
	public String toString() {
		return Float.toString(get());
	}
}
