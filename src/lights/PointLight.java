package lights;

import org.newdawn.slick.Color;

import utils.Position;

public class PointLight extends AbstractLight {
    private final Position p;
    
    public PointLight(float x, float y, float scale, Color tint) {
    	super(scale,tint);
        p = new Position(x,y);
    }

    public PointLight(float x, float y, float scale) {
    	super(scale);
    	p = new Position(x,y);
    }

	@Override
	public float getX() {
		return p.getX();
	}

	@Override
	public float getY() {
		return p.getY();
	}
    
}
