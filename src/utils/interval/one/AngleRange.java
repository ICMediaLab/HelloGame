package utils.interval.one;

public class AngleRange extends Range {

	private final float min, width;
	
	public AngleRange(float min, float max) {
		this.min = min;
		this.width = max - min;
	}
	
	@Override
	public float random() {
		float res = rand.nextFloat()*width + min;
		while(res > Math.PI){
			res -= Math.PI + Math.PI;
		}
		while(res <= -Math.PI){
			res += Math.PI + Math.PI;
		}
		return res;
	}

	@Override
	public float length() {
		return Math.abs(width);
	}

	@Override
	public float getMax() {
		return width < 0 ? min : min + width;
	}

	@Override
	public float getMin() {
		return width > 0 ? min : min + width;
	}

}
