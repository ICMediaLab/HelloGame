package utils.particles;

import map.Cell;
import map.MapLoader;
import map.tileproperties.TileProperty;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import entities.objects.watereffects.WaterEffectParticle;

import utils.Position;

public class RainParticle extends BlockedCollidingParticle {
	
	private boolean inwater = false;
	private int ty;
	
	public RainParticle(Cell c, Image texture, Position position,
			Position velocity, Color color, float size, Position attractor,
			Position inertia) {
		super(c,texture,position,velocity,color,size,attractor,inertia);
	}
	
	@Override
	public boolean isAlive() {
		return getCenterY() < MapLoader.getCurrentCell().getHeight() && super.isAlive() && getRadius() > 0.001f;
	}
	
	public boolean isInWater(){
		return inwater;
	}
	
	@Override
	public void update(GameContainer gc) {
		xy.translate(dxdy);
		if(isAlive()){
			Cell c = getCell();
			if(!inwater){
				if(c.getTile((int) getCenterX(), (int) getCenterY()).lookup(TileProperty.TYPESTR).equalsIgnoreCase("water")){
					inwater = true;
					ty = (int) getCenterY();
				}
			}
			if(inwater){
				setRadius(getRadius()*0.7f);
			}
		}  
	}

	public WaterEffectParticle getBubbleParticle() {
		return new WaterEffectParticle(xy.clone(), dxdy.clone(), ty);
	}
	
}
