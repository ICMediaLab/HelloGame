package utils.particles;

import map.Cell;
import map.tileproperties.TileProperty;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import utils.Position;

public class BlockedCollidingParticle extends Particle {
	
	private final Cell c;
	
	public BlockedCollidingParticle(Cell cell, Image texture, Position position,
			Position velocity, Color color, float size) {
		super(texture, position, velocity, color, size);
		c = cell;
	}
	@Override
	public boolean isAlive() {
		float cY = getCenterY();
		return cY < 1 || !c.getTile((int) getCenterX(), (int) cY).lookup(TileProperty.BLOCKED).getBoolean(); 
	}
}
