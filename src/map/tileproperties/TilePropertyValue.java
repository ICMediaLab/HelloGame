package map.tileproperties;

import javax.naming.OperationNotSupportedException;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public abstract class TilePropertyValue {
	public boolean getBoolean() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	public int getInteger() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	public double getDouble() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	public float getFloat() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	public String getString() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	public void setBoolean(boolean newValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	public void setInteger(int newValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	public void setDouble(double newValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	public void setFloat(float newValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	public void setString(String newValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	public abstract void parse(String str) throws ParseException, OperationNotSupportedException;
	
	@Override
	public abstract String toString();
}