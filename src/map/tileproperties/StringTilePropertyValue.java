package map.tileproperties;

public class StringTilePropertyValue extends TilePropertyValue<String> {
	
	public StringTilePropertyValue(String value) {
		super(value);
	}
	
	@Override
	public TilePropertyValue<String> parse(String str) {
		return new StringTilePropertyValue(str);
	}

	@Override
	public String toString() {
		return get();
	}
}
