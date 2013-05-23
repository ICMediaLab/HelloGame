package map.tileproperties;

import java.text.ParseException;

public class FloatTilePropertyValue extends TilePropertyValue<Float> {
	
	public FloatTilePropertyValue(float value) {
		super(value);
	}
	
	@Override
	public TilePropertyValue<Float> parse(String str) throws ParseException {
		try{
			return new FloatTilePropertyValue(Float.parseFloat(str));
		}catch(NumberFormatException e){
			throw new ParseException("Not a float.",0);
		}
	}
	
	@Override
	public String toString() {
		return Float.toString(get());
	}
}
