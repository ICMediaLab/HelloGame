package map;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import entities.Entity;
import game.config.Config;


public class Cell extends TiledMap{

	private final Tile[][] properties = new Tile[getHeight()][getWidth()];
	private final Set<Entity> entities = new HashSet<Entity>(); 
			
	public Cell(String location) throws SlickException {
		super(location);
		loadProperties();
	}
	
	
	public Tile getTile(int x, int y) {
		return properties[y][x];
	}
	
	
	/**
	 * Extracts the properties of each cell in the map to a
	 * Tile.
	 * (This method only pulls out "blocked" and "enemy" properties
	 *  for the moment. Add more as needed.)
	 * PRE: 0 <= x < width / 0 <= y < height
	 * @return 
	 */
	private void loadProperties(){
		
		//go through all tiles in map
		for (int xAxis = 0; xAxis < width; xAxis++) { 
			for (int yAxis = 0; yAxis < height; yAxis++) {
				properties[yAxis][xAxis] = new Tile(getTileId(xAxis, yAxis, 0));
				properties[yAxis][xAxis].parseTileProperties(this);
			}
		}
	}


	public void addEntity(Entity newEntity) {
		entities.add(newEntity);
	}


	public void render() {
		super.render(-Config.getTileSize(),-Config.getTileSize());
		for(Entity e : entities){
			e.render();
		}
	}


	public void clearEntities() {
		entities.clear();
	}

}
