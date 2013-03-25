package entities.aistates;

import map.tileproperties.TileProperty;
import utils.MapLoader;
import entities.Entity;

public class RoamingEntity implements AINextMove {
	
	private static final float MARGIN = 0.25f;
	
	@Override
	public void updateEntity(Entity e) {
		//set up dx based on current entity direction.
		float dx = 0.2f * ((int) (Math.random()*2) - 0.5f);
		if(e.getdX() > 0f){
			dx = Math.abs(dx);
		}else if(e.getdX() < 0f){
			dx = -Math.abs(dx);
		}
		
		//set up direction-specific testing criteria.
		int curX = dx > 0 ? (int) (e.getX() + e.getWidth()) : (int) e.getX();
		
		//checks for
		//     e->
		//  ####
		// #######
		if(!MapLoader.getCurrentCell().getTile(curX, (int) (e.getY() + e.getHeight())).lookupProperty(TileProperty.BLOCKED).getBoolean()){
			dx *= -1;
		}else{
			//checks for
			//      #
			//   e->#
			// ######
			for(float y=e.getY() + MARGIN;y<e.getY() + e.getHeight();y+=0.5f){
				if(MapLoader.getCurrentCell().getTile(curX, (int) y).lookupProperty(TileProperty.BLOCKED).getBoolean()){
					dx *= -1;
					break;
				}
			}
		}
		//accelerate to resolve any issues and maintain speed.
		e.accelerate(dx,0f);
	}

}
