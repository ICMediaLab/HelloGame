package entities;

import org.newdawn.slick.geom.Rectangle;

public interface FixedRotationEntity extends Entity {
	
	float getX();
	float getY();
	
	@Override
	Rectangle getHitbox();
}
