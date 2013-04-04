package utils.interval.one;

public class Interval extends Range<Float> {

	private final float low, width;
	
	public Interval(float low, float high) {
		this.low = low;
		this.width = high - low;
	}
	
	@Override
	public Float random() {
		return rand.nextFloat()*width + low;
	}

}
