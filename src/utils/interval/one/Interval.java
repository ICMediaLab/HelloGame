package utils.interval.one;

public class Interval extends Range<Float> {

	private final float low, width;
	
	public Interval(float low, float high) {
		this.low = Math.min(low,high);
		this.width = Math.max(low, high) - this.low;
	}
	
	@Override
	public Float random() {
		return rand.nextFloat()*width + low;
	}

	@Override
	public Float length() {
		return width;
	}

	@Override
	public Float getMax() {
		return low + width;
	}

	@Override
	public Float getMin() {
		return low;
	}

}
