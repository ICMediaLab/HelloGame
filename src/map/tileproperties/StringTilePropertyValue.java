package map.tileproperties;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class StringTilePropertyValue extends TilePropertyValue<String> {
	
	public StringTilePropertyValue(String value) {
		super(value);
	}
	
	@Override
	public void parse(String str) throws ParseException {
		set(str);
	}

	@Override
	public String toString() {
		return get();
	}
}
