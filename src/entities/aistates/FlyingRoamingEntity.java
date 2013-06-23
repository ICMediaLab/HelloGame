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
		//set up dx based on current entity direction.
		float dx = 0.2f * ((int) (Math.random()*2) - 0.5f);
		if(e.getdX() > 0f){
			dx = Math.abs(dx);
		}else if(e.getdX() < 0f){
			dx = -Math.abs(dx);
		}
		
		//set up direction-specific testing criteria.
		int curX = dx > 0 ? (int) (e.getX() + e.getWidth()) : (int) e.getX();
		
		Cell c = MapLoader.getCurrentCell();
		
		float ddy = c.getTile(curX, (int) e.getY()-1).lookup(TileProperty.BLOCKED) ?
				r.nextFloat() : r.nextFloat()*(-0.1f);
		
		if(c.getTile(curX, (int) e.getdY()).lookup(TileProperty.BLOCKED)){
			dx = -dx;
		}
		
		e.accelerate(dx,ddy);
		if(e.getdX() > 0){
			e.setCurrentAnimationState(AnimationState.ROAM_RIGHT);
		}else if(e.getdX() < 0){
			e.setCurrentAnimationState(AnimationState.ROAM_LEFT);
		}
	}

}
