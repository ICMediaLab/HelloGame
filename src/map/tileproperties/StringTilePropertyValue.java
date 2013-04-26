package map.tileproperties;

public class StringTilePropertyValue extends TilePropertyValue<String> {
	
	public StringTilePropertyValue(String value) {
		super(value);
	}
	
	@Override
	public void parse(String str) {
		set(str);
	}

	@Override
	public String toString() {
		return get();
	}
}
