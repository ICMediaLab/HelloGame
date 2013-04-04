package utils.interval;

import utils.Position;

public class Sector2D extends Range2D {
	
	private final Position src;
	private final float minM, maxM;
	private final double minA, maxA;
	
	public Sector2D(Position src, float minMagnitude, float maxMagnitude, double minAngle, double maxAngle) {
		this.src = src;
		this.minM = minMagnitude;
		this.maxM = maxMagnitude;
		while(minAngle < -Math.PI){
			minAngle += Math.PI + Math.PI;
		}
		while(maxAngle < -Math.PI){
			maxAngle += Math.PI + Math.PI;
		}
		while(minAngle >= -Math.PI){
			minAngle -= Math.PI + Math.PI;
		}
		while(maxAngle >= -Math.PI){
			maxAngle -= Math.PI + Math.PI;
		}
		this.maxA = maxAngle;
		this.minA = minAngle < maxAngle ? minAngle : minAngle - (Math.PI + Math.PI);
	}

	@Override
	public Position random() {
		float m = rand.nextFloat()*(maxM - minM) + minM;
		double a = rand.nextDouble()*(maxA - minA) + minA;
		return new Position(m*(float) Math.cos(a) + src.getX(), m*(float) Math.sin(a) + src.getY());
	}

}
