package utils.mouse;

public enum MouseButton {
	LEFT,MIDDLE,RIGHT;
	
	public static MouseButton getButton(int representation) throws IllegalArgumentException {
		MouseButton[] vals = values();
		if(representation < 0 || representation >= vals.length){
			throw new IllegalArgumentException("Mouse button specified does not exist.");
		}
		return vals[representation];
	}
	
	public int getValue(){
		return ordinal();
	}

}
