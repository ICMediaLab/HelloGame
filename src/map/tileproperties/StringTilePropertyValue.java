package map.tileproperties;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class StringTilePropertyValue extends TilePropertyValue {
	
	private String value = "";

	public StringTilePropertyValue(String value) {
		this.value = value;
	}
	
	public StringTilePropertyValue() { }
	
	@Override
	public void setString(String newValue) {
		this.value = newValue;
	}

	@Override
	public String getString() {
		return value;
	}

	@Override
	public void parse(String str) throws ParseException {
		this.value = str;
	}

	@Override
	public String toString() {
		return value;
	}
}