package utils;

import java.awt.Point;
import java.io.Serializable;

public class Position implements Cloneable, Serializable {
	
	private static final long serialVersionUID = -9129036695134400854L;
	
	private float x,y;

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Position(){
		this(0f,0f);
	}

	public Position(Point p) {
		this(p.x, p.y);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public void set(float x2, float y2) {
		x = x2;
		y = y2;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void translate(Position p) {
		x += p.x;
		y += p.y;
	}
	
	public void translate(float dx, float dy) {
		x += dx;
		y += dy;
	}
	
	public void scale(Position p){
		x *= p.x;
		y *= p.y;
	}
	
	public void scale(float sx, float sy){
		x *= sx;
		y *= sy;
	}

	public void scale(float s) {
		x *= s;
		y *= s;
	}
	
	public float getMagnitudeSquared(){
		return x*x + y*y;
	}
	
	public float getMagnitude(){
		return (float) Math.sqrt(getMagnitudeSquared());
	}
	
	@Override
	public Position clone() {
		return new Position(x,y);
	}
	
	public void normalise(){
		scale(1f/getMagnitude());
	}

	public double getAngle() {
		return Math.atan2(y, x);
	}
	
	@Override
	public String toString() {
		return x + ", " + y;
	}

	public Position scaledCopy(float s) {
		return new Position(x*s,y*s);
	}
}
