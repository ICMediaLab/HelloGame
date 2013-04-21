package utils;

import java.io.Serializable;


public class Dimension implements Serializable {
	
	private static final long serialVersionUID = 2660123525298222050L;
	
	private float width,height;
	
	public Dimension(float width, float height){
		this.width = width;
		this.height = height;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	public void set(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void scale(float sx, float sy){
		width *= sx;
		height *= sy;
	}
	
	public void scale(float s){
		scale(s,s);
	}

}
