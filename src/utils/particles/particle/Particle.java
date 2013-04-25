package utils.particles.particle;

import utils.PositionReturn;
import utils.Renderable;
import utils.Updatable;

public interface Particle extends Updatable, Renderable, PositionReturn {
	
	boolean isAlive();
	float getCenterY();
	float getCenterX();
	float getdX();
	float getdY();
	
}
