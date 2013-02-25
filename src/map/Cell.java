package map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


public class Cell extends TiledMap{

	private final Tile[][] properties = new Tile[getHeight()][getWidth()];
	
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

}
