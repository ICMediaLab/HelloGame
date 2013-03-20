package map.tileproperties;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class BooleanTilePropertyValue extends TilePropertyValue {
	private boolean value = false;
	
	public BooleanTilePropertyValue(boolean value) { 
		this.value = value; 
	}
	
	public BooleanTilePropertyValue() { }
	
	@Override
	public void setBoolean(boolean newValue) {
		this.value = newValue;
	}

	@Override
	public boolean getBoolean() {
		return value;
	}

	@Override
	public void parse(String str) throws ParseException {
		if(str.equalsIgnoreCase("true")){
			setBoolean(true);
		}else if(str.equalsIgnoreCase("false")){
			setBoolean(false);
		}else{
			throw new ParseException("Not a boolean.");
		}
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}
}