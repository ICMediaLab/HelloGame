package utils.interval.one;

public class AngleRange extends Range<Double> {

	private final double min, width;
	
	public AngleRange(double min, double max) {
		this.min = min;
		this.width = max - min;
	}
	
	@Override
	public Double random() {
		double res = rand.nextDouble()*width + min;
		while(res > Math.PI){
			res -= Math.PI + Math.PI;
		}
		while(res <= -Math.PI){
			res += Math.PI + Math.PI;
		}
		return res;
	}

	@Override
	public Double length() {
		return Math.abs(width);
	}

	@Override
	public Double getMax() {
		return width < 0 ? min : min + width;
	}

	@Override
	public Double getMin() {
		return width > 0 ? min : min + width;
	}

}
