package entities.aistates;

import map.Cell;
import map.tileproperties.TileProperty;
import utils.MapLoader;
import entities.Entity;

public class RetreatingEntity implements AINextMove {
	
	private static final float XMARGIN = 0.1f, MARGIN = 0.25f;

	@Override
	public void updateEntity(Entity e) {
		Cell cell = MapLoader.getCurrentCell();
		Entity player = cell.getPlayer();
		int curX, nextX, nextNextX;
		if(e.getX() < player.getX()){
			e.accelerate(-0.1f, 0f);
			curX = (int) (e.getX() + XMARGIN);
			nextX = (int) (e.getX() - 3*XMARGIN);
			nextNextX = nextX - 1;
		}else{
			e.accelerate(0.1f, 0f);
			curX = (int) (e.getX() + e.getWidth() - XMARGIN);
			nextX = (int) (e.getX() + e.getWidth() + 3*XMARGIN);
			nextNextX = nextX + 1;
		}
		if(e.isOnGround()){
			boolean jump = false;
			if(!cell.getTile(curX, (int) (e.getY() + e.getHeight() + MARGIN)).lookupProperty(TileProperty.BLOCKED).getBoolean() &&
					cell.getTile(nextNextX, (int) (e.getY() + e.getHeight() + MARGIN)).lookupProperty(TileProperty.BLOCKED).getBoolean()){
				jump = true;
			}else{
				for(float y=e.getY() + MARGIN;y<e.getY() + e.getHeight();y+=0.5f){
					if(cell.getTile(nextX, (int) y).lookupProperty(TileProperty.BLOCKED).getBoolean()){
						jump = true;
						break;
					}
				}
				if(jump == true){
					for(int count=0, y=(int) (e.getY() - MARGIN);count <2;y--,count++){
						if(cell.getTile(nextX, y).lookupProperty(TileProperty.BLOCKED).getBoolean()){
							jump = false;
							break;
						}
					}
				}
			}
			if(jump){
				e.jump();
			}
		}
	}

}
