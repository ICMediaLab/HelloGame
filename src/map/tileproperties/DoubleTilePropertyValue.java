package map.tileproperties;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class DoubleTilePropertyValue extends TilePropertyValue {
	
	private double value = 0.0;

	public DoubleTilePropertyValue(double value) {
		this.value = value;
	}
	
	public DoubleTilePropertyValue() { 	}
	
	@Override
	public void setDouble(double newValue) {
		this.value = newValue;
	}

	@Override
	public double getDouble() {
		return value;
	}

	@Override
	public void parse(String str) throws ParseException {
		try{
			setDouble(Double.parseDouble(str));
		}catch(NumberFormatException e){
			throw new ParseException("Not a double.");
		}
	}

	@Override
	public String toString() {
		return Double.toString(value);
	}
}