package map;

import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;


import org.newdawn.slick.SlickException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utils.XMLDocumentLoader;
import utils.triggers.TriggerEvent;

import entities.players.Player;

public final class MapLoader {
	private MapLoader(){} //MapLoader should not be instantiated.
	
	private static final Map<Cell,Point> cellPos = new HashMap<Cell,Point>();
	
	private static int startX, startY;
	private static Cell[][] maps;
	private static Cell currentCell;
	private static int currentX;
	private static int currentY;
	
	/**
	 * Loads all maps based on a specific map layout file specified.<br />
	 * Note that this file must be located in the same directory as the map files.
	 */
	public static void loadAllMaps(String layoutPath) {
		try {
			Document d = XMLDocumentLoader.getXMLDocument(layoutPath);
			NamedNodeMap dattrs = d.getDocumentElement().getAttributes();
			setDimensions(
					Integer.parseInt(dattrs.getNamedItem("width").getNodeValue()),
					Integer.parseInt(dattrs.getNamedItem("height").getNodeValue())
				);
			startX = Integer.parseInt(dattrs.getNamedItem("defx").getNodeValue());
			startY = Integer.parseInt(dattrs.getNamedItem("defy").getNodeValue());
			NodeList nl = d.getElementsByTagName("map");
			int pos = layoutPath.lastIndexOf('/');
			String basePath;
			if(pos >= 0){
				basePath = layoutPath.substring(0, pos+1);
			}else{
				basePath = "";
			}
			for (int i = nl.getLength()-1; i >= 0; --i) {
				NamedNodeMap attrs = nl.item(i).getAttributes();
				try {
					int x = Integer.parseInt(attrs.getNamedItem("x").getNodeValue());
					int y = Integer.parseInt(attrs.getNamedItem("y").getNodeValue());
					Node name = attrs.getNamedItem("name");
					loadMap(name == null ? null : name.getTextContent(), basePath + attrs.getNamedItem("src").getNodeValue(), x, y);
				} catch (DOMException e) {
					e.printStackTrace();
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		CellObjectParser.getInst().parse();
	}
	
	/**
	 * Loads a new map from an xml file into an element of the internal
	 * array.
	 * PRE: 0 <= x < width / 0 <= y < height
	 * @param location 
	 * @param location: a string representing the address of the file
	 * @param x: The x part of the cell's location in the array.
	 * @param y: The corresponding y part.
	 * @return 
	 * @throws SlickException
	 */
	private static void loadMap(String name, String location, int x, int y) throws SlickException {
		maps[y][x] = new Cell(name, location);
		cellPos.put(maps[y][x], new Point(x,y));
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
	 * Setter for the current cell to be rendered and used by the player. Overrides the current player if any exists.
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
		currentCell.initLoad();
		currentCell.addMovingEntity(player);
		currentCell.setPlayer(player);
		currentCell.setVisited();
		TriggerEvent.CELL_TRANSITION.triggered(currentCell);
		return currentCell;
	}
	
	/**
	 * Setter for the current cell to be rendered and used by the player.
	 * @param x
	 * @param y
	 * @return The current cell that was just set.
	 */
	public static Cell setCurrentCell(int x, int y) {
		return setCurrentCell(getCurrentCell().getPlayer(), x, y);
	}
	
	/**
	 * Setter for the current cell based on a known cell reference.
	 * @param c The cell to be set.
	 * @return The position of the cell just set.
	 */
	public static Point setCurrentCell(Cell c){
		Point pos = cellPos.get(c);
		setCurrentCell(pos.x, pos.y);
		return pos;
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
	    
	    for (int y = 0; y < 3; y++) {
	        for (int x = 0; x < 3; x++) {
	            try {
	                res[y][x] = maps[currentY + y - 1][currentX + x - 1];
	            } 
	            catch (IndexOutOfBoundsException e) {
	                res[y][x] = null;
	            }
	        }
	    }
	    
	    return res;
	}

	public static Cell setInitialCell(Player player) {
		return setCurrentCell(player, startX, startY);
	}
}
