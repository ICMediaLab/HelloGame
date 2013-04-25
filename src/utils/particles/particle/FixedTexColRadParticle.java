package utils.particles.particle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import utils.Position;

public abstract class FixedTexColRadParticle extends AbstractParticle {
	
	private final Image texture;
	
	private final Position inertia;
	private final Color colour;
	
	private float radius;
	
	public FixedTexColRadParticle(Image texture, Position position, Position velocity,
			Color color, float radius, Position inertia) {
		super(position,velocity);
		this.texture = texture;
		this.inertia = inertia;
		this.colour = color;
		this.radius = radius;
	}
	
	protected float getRadius(){
		return radius;
	}
	
	protected void setRadius(float rad){
		radius = rad > 0f ? rad : 0f;
	}
	
	@Override
	public Image getTexture() {
		return texture;
	}
	
	@Override
	protected Color getColour() {
		return colour;
	}
	
	@Override
	protected Position getInertia() {
		return inertia;
	}
}
