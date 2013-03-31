package map;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import lights.EntityLight;
import lights.PointLight;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.ObjectGroup;
import org.newdawn.slick.tiled.TiledMap;

import utils.LayerRenderable;
import utils.MapLoader;
import utils.Renderable;
import utils.Updatable;
import entities.Entity;
import entities.enemies.Enemy;
import entities.objects.Door;
import entities.objects.DoorTrigger;
import entities.objects.JumpPlatform;
import entities.objects.LeafTest;
import entities.players.Player;
import game.config.Config;


public class Cell extends TiledMap implements Updatable, Renderable {

	private static final Comparator<? super LayerRenderable> LAYER_COMPARATOR = new LayerComparator();
	private final Tile[][] properties = new Tile[getHeight()][getWidth()];
	private final Set<Entity> defaultEntities = new HashSet<Entity>();
	private final Set<Entity> entities = new HashSet<Entity>();
	private final Set<Entity> entitiesToRemove = new HashSet<Entity>();
	private final Set<Entity> entitiesToAdd = new HashSet<Entity>();
	private final LightMap lightmap = new LightMap();
    private Player player;
    private boolean visited = false;
    private int counter = 150;
    private float fadeOut = 1;
			
	public Cell(String location) throws SlickException {
		super(location);
		loadProperties();
		loadDefaultEntities();
	}
	
	public void loadDefaultEntities(){
		entities.clear();
		entitiesToAdd.clear();
		entitiesToRemove.clear();
		if(defaultEntities.isEmpty()){
			Map<String,Door> doors = new HashMap<String,Door>();
			Map<String,DoorTrigger> triggers = new HashMap<String,DoorTrigger>();
			
			for(ObjectGroup og : super.objectGroups){
				for(GroupObject go : og.objects){
					int x = go.x / Config.getTileSize();
					int y = go.y / Config.getTileSize();
					if(go.type.equalsIgnoreCase("enemy")){
						defaultEntities.add(Enemy.getNew(this, go.name, x,y));
					}else if(go.type.equalsIgnoreCase("door")){
						if(triggers.containsKey(go.name)){
							defaultEntities.add(new Door(this,triggers.remove(go.name),x,y));
						}else{
							Door d = new Door(this,null,x,y);
							doors.put(go.name,d);
							defaultEntities.add(d);
						}
					}else if(go.type.equalsIgnoreCase("doorTrigger")){
						if(doors.containsKey(go.name)){
							defaultEntities.add(new DoorTrigger(doors.remove(go.name),x,y));
						}else{
							DoorTrigger dt = new DoorTrigger(null, x, y);
							triggers.put(go.name, dt);
							defaultEntities.add(dt);
						}
					}else if(go.type.equalsIgnoreCase("leafTest")){
						defaultEntities.add(new LeafTest(x,y));
					} else if(go.type.equalsIgnoreCase("jumpPlatform")){
						defaultEntities.add(new JumpPlatform(x,y));
					}
				}
			}
		}
		for(Entity e : defaultEntities){
			addEntity(e.clone());
		}
		
		lightmap.addLight(new PointLight(800, 0, 5));
		lightmap.addLight(new PointLight(0, 0, 5));
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
		entitiesToAdd.add(newEntity);
	}
	
	public void render(GameContainer gc, Graphics g) {
		//super.render(-Config.getTileSize(),-Config.getTileSize());
		PriorityQueue<LayerRenderable> orderedLayers = new PriorityQueue<LayerRenderable>(11,LAYER_COMPARATOR);
		orderedLayers.addAll(entities);
		for(Layer l : layers){
			try{
				orderedLayers.add(new LayeredLayer(l));
			}catch(NoSuchFieldException e){
				System.out.println(e.getMessage());
				for(int i=0;i<l.height-2;i++){
					l.render(0, 0, 1, 1, l.width-2, i, false, Config.getTileSize(), Config.getTileSize());
				}
			}
		}
		while(!orderedLayers.isEmpty()){
			orderedLayers.poll().render(gc, g);
		}
		
		lightmap.render(gc,g);
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_K)) {
			counter = 0;
			fadeOut = 1;
		}
		
		if (counter < 150) {
			if (counter > 100) fadeOut -= 0.02;
		// render minimap
		Cell[][] mini = MapLoader.getSurroundingCells();
		g.setColor(Color.orange.scaleCopy(fadeOut));
        g.fillRoundRect(width * Config.getTileSize() - 128, 48, 24, 24, 5);
        g.setColor(Color.white);
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (mini[i][j] != null) {
                    if (i != 1 || j != 1) {
                        if (mini[i][j].visited) {
                            g.setColor(Color.green.scaleCopy(fadeOut));
                        } else {
                            g.setColor(Color.white.scaleCopy(fadeOut));
                        }
                        g.fillRoundRect(width * Config.getTileSize() - (154 - (i * 26)), 22 + (j * 26), 24, 24, 5);
                    }
                    g.setColor(Color.darkGray.scaleCopy(fadeOut));
                    g.fillRoundRect(width * Config.getTileSize() - (154 - (i * 26)) + 2, 22 + (j * 26) +  2, 20, 20, 5);
                }
             }
        }
        
        g.setColor(Color.darkGray.scaleCopy(fadeOut));
        g.fillRoundRect(width * Config.getTileSize() - 128 + 2, 48 + 2, 20, 20, 5);
        
        counter++;
		}
	}
	
	public void clearEntities() {
		entitiesToRemove.addAll(entities);
	}
	
	public Set<Entity> getEntities() {
	    return entities;
	}
	
	public void update(GameContainer gc){
		updateEntities(gc);
		lightmap.update(gc);
	}
	
	public void updateEntities(GameContainer gc){
		entitiesToRemove.clear();
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();
		for(Entity e : entities){
			e.update(gc);
			for (Entity e2 : entities){
				if (e != e2 && e.intersects(e2)){
					e.collide(e2);
				}
			}
		}
		entities.removeAll(entitiesToRemove);
	}
    
    public void removeEntity(Entity e) {
        entitiesToRemove.add(e);
    }
    
    public void setPlayer(Player player) {
        this.player = player;
        lightmap.addLight(new EntityLight(player, 7f));
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setVisited() {
        visited = true;
    }
    
    public boolean isVisited() {
        return visited;
    }

}
