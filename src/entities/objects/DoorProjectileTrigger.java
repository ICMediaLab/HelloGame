package entities.objects;

import items.projectiles.Projectile;
import entities.MovingEntity;

public class DoorProjectileTrigger extends DoorTrigger {
	
	private static final long serialVersionUID = -3139641687725076650L;
	
	public DoorProjectileTrigger(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void collide(MovingEntity e) {
		if(e instanceof Projectile){
			triggered();
		}
	}
}
