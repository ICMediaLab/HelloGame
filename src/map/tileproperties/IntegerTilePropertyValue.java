package map.tileproperties;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class IntegerTilePropertyValue extends TilePropertyValue {
	
	private int value = 0;
	
	public IntegerTilePropertyValue(int value) {
		this.value = value;
	}
	
	public IntegerTilePropertyValue() { }
	
	@Override
	public void setInteger(int newValue) {
		this.value = newValue;
	}

	@Override
	public int getInteger() {
		return value;
	}

	@Override
	public void parse(String str) throws ParseException {
		try{
			setInteger(Integer.parseInt(str));
		}catch(NumberFormatException e){
			throw new ParseException("Not an integer.");
		}
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}