package utils.interval;

import org.newdawn.slick.Color;

import utils.interval.one.FixedValue;
import utils.interval.one.Interval;

/**
 * A class for holding a range of colours based on rgba components. Each component is held as a separate interval.
 * The alpha channel will have a fixed value of 1f (opaque) if not specified in the constructor.
 */
public class ColourRange {
	
	private final Interval r,g,b,a;
	
	/**
	 * Creates a new ColourRange object with specified intervals for the red, green, blue and alpha channels respectfully.
	 */
	public ColourRange(Interval r, Interval g, Interval b, Interval a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/**
	 * Creates a new ColourRange object with specified intervals for the red, green and blue channels respectfully.<br />
	 * The alpha channel will be set to a fixed value of 1f (opaque).
	 */
	public ColourRange(Interval r, Interval g, Interval b) {
		this(r,g,b,new FixedValue(1f));
	}
	
	/**
	 * Intermediate constructor for handling rgb components :)
	 */
	private ColourRange(float minr, float maxr, float ming, float maxg, float minb, float maxb, Interval a){
		this(
				minr == maxr ? new FixedValue(minr) : new Interval(minr,maxr),
				ming == maxg ? new FixedValue(ming) : new Interval(ming,maxg),
				minb == maxb ? new FixedValue(minb) : new Interval(minb,maxb),
				a
			);
	}
	
	/**
	 * Creates a new ColourRange object of specified ranges for red, green, blue and alpha components respectfully in the form of minimum, then maximum parameters.
	 */
	public ColourRange(float minr, float maxr, float ming, float maxg, float minb, float maxb, float mina, float maxa) {
		this(minr, maxr, ming, maxg, minb, maxb, mina == maxa ? new FixedValue(mina) : new Interval(mina,maxa));
	}
	
	/**
	 * Creates a new ColourRange object of specified ranges for red, green, blue and components respectfully in the form of minimum, then maximum parameters.<br />
	 * The alpha channel will be set to a default fixed value of 1f (opaque).
	 */
	public ColourRange(float minr, float maxr, float ming, float maxg, float minb, float maxb) {
		this(minr,maxr,ming,maxg,minb,maxb,new FixedValue(1f));
	}

	public Color random() {
		return new Color(r.random(),g.random(),b.random(),a.random());
	}

	public Color length() {
		return null;
	}
}
