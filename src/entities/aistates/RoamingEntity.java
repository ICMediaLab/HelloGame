package entities.aistates;

import map.tileproperties.TileProperty;
import utils.MapLoader;
import entities.AbstractEntity;

public class RoamingEntity implements AINextMove {
	
	private static final float MARGIN = 0.25f;
	
	@Override
	public void updateEntity(AbstractEntity e) {
		float dx = 0.2f * ((int) (Math.random()*2) - 0.5f);
		if(e.getdX() > 0f){
			dx = Math.abs(dx);
		}else if(e.getdX() < 0f){
			dx = -Math.abs(dx);
		}
		if(dx > 0){
			if(!MapLoader.getCurrentCell().getTile((int) (e.getX() + e.getWidth()), (int) (e.getY() + e.getHeight())).lookupProperty(TileProperty.BLOCKED).getBoolean()){
				dx *= -1;
			}else{
				for(float y=e.getY() + MARGIN;y<e.getY() + e.getHeight();y+=0.5f){
					if(MapLoader.getCurrentCell().getTile((int) (e.getX() + 0.01f + e.getWidth()), (int) y).lookupProperty(TileProperty.BLOCKED).getBoolean()){
						dx *= -1;
						break;
					}
				}
			}
		}else if(dx < 0){
			if(!MapLoader.getCurrentCell().getTile((int) e.getX(), (int) (e.getY() + e.getHeight())).lookupProperty(TileProperty.BLOCKED).getBoolean()){
				dx *= -1;
			}else{
				for(float y=e.getY() + MARGIN;y<e.getY() + e.getHeight();y+=0.5f){
					if(MapLoader.getCurrentCell().getTile((int) (e.getX() - 0.01f), (int) y).lookupProperty(TileProperty.BLOCKED).getBoolean()){
						dx *= -1;
						break;
					}
				}
			}
		}
		e.accelerate(dx,0f);
	}

}
