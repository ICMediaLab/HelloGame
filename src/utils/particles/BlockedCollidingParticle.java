package utils.particles;

import map.Cell;
import map.tileproperties.TileProperty;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import utils.Position;
import utils.particles.particle.InfiniteAttractorParticle;

public class BlockedCollidingParticle extends InfiniteAttractorParticle {
	
	private final Cell c;
	
	public BlockedCollidingParticle(Cell cell, Image texture, Position position, Position velocity, Color color, float size) {
		super(texture, position, velocity, color, size);
		c = cell;
	}
	
	public BlockedCollidingParticle(Cell cell, Image texture, Position position, Position velocity, Color color, float size, float drag) {
		super(texture, position, velocity, color, size, drag);
		c = cell;
	}
	
	public BlockedCollidingParticle(Cell cell, Image texture, Position position, Position velocity, Color color, float size, Position attractor, Position inertia) {
		super(texture, position, velocity, color, size, attractor, inertia);
		c = cell;
	}
	
	public Cell getCell(){
		return c;
	}
	
	@Override
	public boolean isAlive() {
		float cY = getCenterY();
		return cY < 1 || !c.getTile((int) getCenterX(), (int) cY).lookup(TileProperty.BLOCKED).getBoolean(); 
	}
}
