package utils.particles.rain;

import map.Cell;
import map.MapLoader;
import map.tileproperties.TileProperty;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import utils.Position;
import utils.particles.BlockedCollidingParticle;
import entities.objects.watereffects.WaterEffectParticle;
import game.config.Config;

public class RainParticle extends BlockedCollidingParticle {
	
	private boolean inwater = false;
	private int ty;
	private final int fcs;
	
	public RainParticle(Cell c, Image texture, Position position, Position velocity, Color color, float size, Position attractor, Position inertia) {
		this(c,texture,position,velocity,color,size,attractor,inertia,1);
	}
	
	public RainParticle(Cell c, Image texture, Position position, Position velocity, Color color, float size, Position attractor, Position inertia, int fcs) {
		super(c,texture,position,velocity,color,size,attractor,inertia);
		this.fcs = fcs;
	}

	@Override
	public boolean isAlive() {
		return getCenterY() < fcs || getCenterY() < MapLoader.getCurrentCell().getHeight() && super.isAlive() && getRadius() > 0.001f;
	}
	
	public boolean isInWater(){
		return inwater;
	}
	
	@Override
	public void update(GameContainer gc) {
		xy.translate(dxdy);
		if(getCenterY() >= fcs && isAlive()){
			Cell c = getCell();
			if(!inwater){
				try{
				if(c.getTile((int) getCenterX(), (int) getCenterY()).lookup(TileProperty.WATER)){
					inwater = true;
					ty = (int) getCenterY();
				}
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println(getCenterX() + "\t" + getCenterY());
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
