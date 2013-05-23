package utils.interval.one;

public class Interval extends Range {

	private final float low, width;
	
	public Interval(float low, float high) {
		this.low = Math.min(low,high);
		this.width = Math.max(low, high) - this.low;
	}
	
	@Override
	public float random() {
		return rand.nextFloat()*width + low;
	}

	@Override
	public float length() {
		return width;
	}

	@Override
	public float getMax() {
		return low + width;
	}

	@Override
	public float getMin() {
		return low;
	}

}
