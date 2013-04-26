package map.tileproperties;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class FloatTilePropertyValue extends TilePropertyValue<Float> {
	
	public FloatTilePropertyValue(float value) {
		super(value);
	}
	
	@Override
	public void parse(String str) throws ParseException {
		try{
			set(Float.parseFloat(str));
		}catch(NumberFormatException e){
			throw new ParseException("Not a float.");
		}
	}
	
	@Override
	public String toString() {
		return Float.toString(get());
	}
}
