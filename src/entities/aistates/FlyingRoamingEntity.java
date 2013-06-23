package entities.aistates;

import java.util.Random;

import utils.ani.AnimationState;

import map.Cell;
import map.MapLoader;
import map.tileproperties.TileProperty;
import entities.NonPlayableEntity;

public class FlyingRoamingEntity implements AINextMove {
	
	private static final Random r = new Random();
	
	@Override
	public void updateEntity(NonPlayableEntity e) {
		if(e.getCurrentAnimationState() == AnimationState.SLEEP){
			if(r.nextBoolean()){
				e.setCurrentAnimationState(AnimationState.ROAM_LEFT);
			}else{
				e.setCurrentAnimationState(AnimationState.ROAM_RIGHT);
			}
		}
		//set up dx based on current entity direction.
		float dx = r.nextFloat()*0.6f;
		if(e.getdX() < 0f){
			dx = -dx;
		}
		
		//set up direction-specific testing criteria.
		int curX  = dx > 0 ? (int) (e.getX() + e.getWidth()) : (int) e.getX();
		//int nextX = dx > 0 ? curX + 1 : curX - 1;
		
		Cell c = MapLoader.getCurrentCell();
		
		float ddy = c.getTile(curX, (int) e.getY()-1).lookup(TileProperty.BLOCKED) ?
				(r.nextFloat()*0.1f) : (r.nextFloat()*(-0.1f));
		
		if(c.getTile(curX, (int) e.getY()).lookup(TileProperty.BLOCKED)){
			dx = -dx;
		}
		e.accelerate(dx,ddy);
		if(e.getdX() > 0.1f){
			e.setCurrentAnimationState(AnimationState.ROAM_RIGHT);
		}else if(e.getdX() < -0.1f){
			e.setCurrentAnimationState(AnimationState.ROAM_LEFT);
		}
	}

}
