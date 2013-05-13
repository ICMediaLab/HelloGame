package entities.objects;

import map.Cell;
import map.tileproperties.BooleanTilePropertyValue;
import map.tileproperties.TileProperty;
import entities.StaticRectEntity;

public abstract class StaticBlockingEntity extends StaticRectEntity {
	
	protected final Cell parent;
	
	public StaticBlockingEntity(Cell parent, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.parent = parent;
		setBlocked(true);
	}
	
	public void setBlocked(boolean blocked){
		for(int y=(int) getY();y<(int) (getY()+getHeight());y++){
			for(int x=(int) getX();x<(int) (getX()+getWidth());x++){
				parent.getTile(x, y).put(TileProperty.BLOCKED, new BooleanTilePropertyValue(blocked));
			}
		}
	}
}
