package utils.interval.colour;

import java.util.Random;

import org.newdawn.slick.Color;

import utils.interval.one.FixedValue;
import utils.interval.one.Interval;

/**
 * A class for holding a range of colours based on rgba components. Each component is held as a separate interval.
 * The alpha channel will have a fixed value of 1f (opaque) if not specified in the constructor.
 */
public class DiscreteColourRange implements ColourRange {
	
	private static final Random r = new Random();
	
	private final Color[] insts;
	
	/**
	 * Creates a new ColourRange object with specified intervals for the red, green, blue and alpha channels respectfully.
	 */
	public DiscreteColourRange(Interval r, Interval g, Interval b, Interval a, int instCount) {
		ContinuousColourRange cr = new ContinuousColourRange(r, g, b);
		insts = new Color[instCount];
		for(int i=0;i<instCount;i++){
			insts[i] = cr.random();
		}
	}
	
	/**
	 * Creates a new ColourRange object with specified intervals for the red, green and blue channels respectfully.<br />
	 * The alpha channel will be set to a fixed value of 1f (opaque).
	 */
	public DiscreteColourRange(Interval r, Interval g, Interval b, int instCount) {
		this(r,g,b,new FixedValue(1f), instCount);
	}
	
	/**
	 * Intermediate constructor for handling rgb components :)
	 */
	private DiscreteColourRange(float minr, float maxr, float ming, float maxg, float minb, float maxb, Interval a, int instCount){
		this(
				minr == maxr ? new FixedValue(minr) : new Interval(minr,maxr),
				ming == maxg ? new FixedValue(ming) : new Interval(ming,maxg),
				minb == maxb ? new FixedValue(minb) : new Interval(minb,maxb),
				a,instCount
			);
	}
	
	/**
	 * Creates a new ColourRange object of specified ranges for red, green, blue and alpha components respectfully in the form of minimum, then maximum parameters.
	 */
	public DiscreteColourRange(float minr, float maxr, float ming, float maxg, float minb, float maxb, float mina, float maxa, int instCount) {
		this(minr, maxr, ming, maxg, minb, maxb, mina == maxa ? new FixedValue(mina) : new Interval(mina,maxa),instCount);
	}
	
	/**
	 * Creates a new ColourRange object of specified ranges for red, green, blue and components respectfully in the form of minimum, then maximum parameters.<br />
	 * The alpha channel will be set to a default fixed value of 1f (opaque).
	 */
	public DiscreteColourRange(float minr, float maxr, float ming, float maxg, float minb, float maxb, int instCount) {
		this(minr,maxr,ming,maxg,minb,maxb,new FixedValue(1f),instCount);
	}

	public Color random() {
		return insts[r.nextInt(insts.length)];
	}
}
