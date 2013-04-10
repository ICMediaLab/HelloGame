package entities.objects;

import items.projectiles.Projectile;
import entities.MovingEntity;

public class DoorProjectileTrigger extends DoorTrigger {

    public DoorProjectileTrigger(Door trigger, int x, int y) {
        super(trigger, x, y);
    }
    
    @Override
    public void collide(MovingEntity e) {
        if(e instanceof Projectile){
            trigger.setTriggered();
        }
    }

}
