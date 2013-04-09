package utils.interval.one;

import org.newdawn.slick.Color;

public class ColourRange extends Range<Color>{
	
	private final Interval r,g,b,a;
	
	public ColourRange(Interval r, Interval g, Interval b, Interval a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public ColourRange(Interval r, Interval g, Interval b) {
		this(r,g,b,new FixedValue(1f));
	}
	
	public ColourRange(float minr, float maxr, float ming, float maxg, float minb, float maxb, float mina, float maxa) {
		this(new Interval(minr,maxr),new Interval(ming,maxg),new Interval(minb,maxb),new Interval(mina,maxa));
	}
	
	public ColourRange(float minr, float maxr, float ming, float maxg, float minb, float maxb) {
		this(new Interval(minr,maxr),new Interval(ming,maxg),new Interval(minb,maxb),new FixedValue(1f));
	}

	@Override
	public Color random() {
		return new Color(r.random(),g.random(),b.random(),a.random());
	}

	@Override
	public Color length() {
		return null;
	}

	@Override
	public Color getMin() {
		return null;
	}

	@Override
	public Color getMax() {
		return null;
	}

}
