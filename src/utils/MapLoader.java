package utils;

import map.Cell;

import org.newdawn.slick.SlickException;

import entities.players.Player;

public final class MapLoader {
	private MapLoader(){} //MapLoader should not be instantiated.
	
	private static Cell[][] maps;
	private static Cell currentCell;
	private static int currentX;
	private static int currentY;
	
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
	public static void loadMap(String location, int x, int y) throws SlickException {
		maps[y][x] = new Cell(location);
	}
	/**
	 * Returns any map by it's x and y position. Should ONLY be necessary to warp to 
	 * a random cell that isn't adjacent to the player.
	 * @param x
	 * @param y
	 * @return the cell.
	 */
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
	
	/**
	 * Setter for the current cell to be rendered and used by the player
	 * @param player 
	 * @param x
	 * @param y
	 * @return The current cell that was just set.
	 */
	public static Cell setCurrentCell(Player player, int x, int y) {
		if(getCurrentCell() != null){
			getCurrentCell().clearMovingEntities();
		}
		currentX = x;
		currentY = y;
		currentCell = maps[y][x];
		currentCell.loadDefaultEntities();
		currentCell.addMovingEntity(player);
		currentCell.setPlayer(player);
		currentCell.setVisited();
		return currentCell;
	}
	
	/**
	 * Getter to return the current cell to the player and render methods.
	 * @return The current cell.
	 */
	public static Cell getCurrentCell() {
		return currentCell;
	}
	
	
	public static int getCurrentX() {
		return currentX;
	}
	
	public static int getCurrentY() {
		return currentY;
	}
	
	/**
	 * Method to generate the cells needed for the minimap.
	 * @return 3x3 array of cells
	 */
	public static Cell[][] getSurroundingCells() {
	    Cell[][] res = new Cell[3][3];
	    
	    for (int j = 0; j < 3; j++) {
	        for (int i = 0; i < 3; i++) {
	            try {
	                res[i][j] = maps[currentY + j - 1][currentX + i - 1];
	            } 
	            catch (IndexOutOfBoundsException e) {
	                res[i][j] = null;
	            }
	        }
	    }
	    
	    return res;
	}
	
}
