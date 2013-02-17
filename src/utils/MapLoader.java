package utils;

import map.Cell;

import org.newdawn.slick.SlickException;

public final class MapLoader {
	private MapLoader(){} //MapLoader should not be instantiated.
	
	private static Cell[][] maps;
	
	/**
	 * Loads a new map from an xml file into an element of the internal
	 * array.
	 * PRE: 0 <= x < width / 0 <= y < height
	 * @param location: a string representing the address of the file
	 * @param x: The x part of the cell's location in the array.
	 * @param y: The corresponding y part.
	 * @return 
	 * @throws SlickException
	 */
	public static Cell loadMap(String location, int x, int y) throws SlickException {
		maps[y][x] = new Cell(location);
		return maps[y][x];
	}
	
	public static Cell getMap(int x, int y) {
		return maps[y][x];
	}

	/**
	 * Initialises the size of the internal 2D array to hold the loaded maps.<br />
	 * Will clear any data previously stored on the MapLoader.
	 * @param cellsWidth The width of the global map.
	 * @param cellsHeight The height of the global map.
	 */
	public static void setDimensions(int cellsWidth, int cellsHeight) {
		maps = new Cell[cellsHeight][cellsWidth];
	}
}
