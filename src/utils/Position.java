package utils;

public class Position implements Cloneable {
	
	private float x,y;

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Position(){
		x=0f; y=0f;
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
		return x + "\t" + y;
	}
}
