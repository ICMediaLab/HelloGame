package utils.interval.one;

public class AngleRange extends Range<Double> {

	private final double min, width;
	
	public AngleRange(double min, double max) {
		this.min = min;
		this.width = max - min;
		System.out.println("width: " + width);
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
		System.out.println("Returning " + res);
		return res;
	}

}
