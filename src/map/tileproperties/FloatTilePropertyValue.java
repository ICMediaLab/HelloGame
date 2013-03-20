package map.tileproperties;

import javax.naming.OperationNotSupportedException;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class FloatTilePropertyValue extends TilePropertyValue {
	
	private float value = 0f;

	public FloatTilePropertyValue(float value) {
		this.value = value;
	}
	
	public FloatTilePropertyValue() { }
	
	@Override
	public void setFloat(float newValue) {
		this.value = newValue;
	}

	@Override
	public float getFloat() {
		return value;
	}

	@Override
	public void parse(String str) throws ParseException, OperationNotSupportedException {
		try{
			setFloat(Float.parseFloat(str));
		}catch(NumberFormatException e){
			throw new ParseException("Not a float.");
		}
	}

	@Override
	public String toString() {
		return Float.toString(value);
	}
}