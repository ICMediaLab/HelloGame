package entities.objects;

import items.projectiles.Projectile;
import entities.MovingEntity;

public class DoorProjectileTrigger extends DoorTrigger {
	
	public DoorProjectileTrigger(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void collide(MovingEntity e) {
		if(e instanceof Projectile){
			trigger();
		}
	}
}
