package utils.interval.two;

import java.util.Random;

import utils.Position;

public class CompoundRange2D extends Range2D {
	
	private static final Random rand = new Random();
	
	private final float[] areas;
	private final Range2D[] ranges;
	private final float totalArea;
	
	public CompoundRange2D(Range2D... ranges) {
		float totalArea = 0f;
		this.ranges = ranges;
		areas = new float[ranges.length];
		for(int i=0;i<ranges.length;i++){
			totalArea += (areas[i] = ranges[i].area());
		}
		this.totalArea = totalArea;
	}

	@Override
	public Position random() {
		float r = rand.nextFloat()*totalArea;
		for(int i=0;i<ranges.length-1;i++){
			if(r < areas[i]){
				return ranges[i].random();
			}
			r -= areas[i];
		}
		return ranges[ranges.length-1].random();
	}

	@Override
	public float area() {
		return totalArea;
	}

}
