package map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import map.tileproperties.TileProperty;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.ObjectGroup;
import org.newdawn.slick.tiled.TiledMap;

import utils.LayerRenderable;
import entities.Entity;
import entities.enemies.Enemy;
import entities.objects.Bricks;
import entities.objects.Door;
import entities.objects.DoorTrigger;
import entities.objects.FloorPhysics;
import entities.objects.JumpPlatform;
import entities.objects.LeafTest;
import entities.players.Player;
import game.GameplayState;
import game.config.Config;


public class Cell extends TiledMap{

	private final Tile[][] properties = new Tile[getHeight()][getWidth()];
	private final Set<Entity> defaultEntities = new HashSet<Entity>();
	private final Set<Entity> entities = new HashSet<Entity>();
	private final Set<FloorPhysics> floor = new HashSet<FloorPhysics>();
	private final Set<Entity> entitiesToRemove = new HashSet<Entity>();
	private final Set<Entity> entitiesToAdd = new HashSet<Entity>();
    private Player player;
			
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
						defaultEntities.add(Enemy.getNewEnemy(this, go.name, x,y));
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
					} else if(go.type.equalsIgnoreCase("bricks")){
						defaultEntities.add(new Bricks(x, y));
					}
				}
			}
		}
		for(Entity e : defaultEntities){
			addEntity(e.clone());
		}
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
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		//super.render(-Config.getTileSize(),-Config.getTileSize());
		PriorityQueue<LayerRenderable> orderedLayers = new PriorityQueue<LayerRenderable>();
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
			orderedLayers.poll().render(gc, sbg, g);
		}
		
	}
	
	public void clearEntities() {
		entitiesToRemove.addAll(entities);
		for (Entity e : entities) {
			if (!e.equals(player)) e.destroy();
		}
	}
	
	public Set<Entity> getEntities() {
	    return entities;
	}
	
	public void updateEntities(GameContainer gc, StateBasedGame sbg, int delta){
		entitiesToRemove.clear();
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();
		System.out.println(entities.size() + " entities to test");
		Map<Class<? extends Entity>,Set<Entity>> freq = new HashMap<Class<? extends Entity>, Set<Entity>>();
		for(Entity e : entities){
			{
				Set<Entity> clazz = freq.get(e.getClass());
				if(clazz == null){
					clazz = new HashSet<Entity>();
					freq.put(e.getClass(), clazz);
				}
				clazz.add(e);
			}
			e.update(gc, sbg, delta);
			for (Entity e2 : entities){
				if (e != e2 && e.intersects(e2)){
					e.collide(e2);
				}
			}
		}
		for(Class<?> c : freq.keySet()){
			System.out.println("\t" + c.getSimpleName() + ":\t" + freq.get(c).size());
		}
		entities.removeAll(entitiesToRemove);
	}
    
    public void removeEntity(Entity e) {
        entitiesToRemove.add(e);
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void addPhysicsEntities() {
    	boolean[][] blocked = new boolean[height][width];
    	boolean[][] edgeblocked = new boolean[height][width];
    	for (int xAxis = 0; xAxis < width; xAxis++) { 
			for (int yAxis = 0; yAxis < height; yAxis++) {
				blocked[yAxis][xAxis] = getTile(xAxis, yAxis).lookupProperty(TileProperty.BLOCKED).getBoolean();
			}
		}
    	for(int y=0;y<blocked.length;y++){
    		for(int x=0;x<blocked[y].length;x++){
    			edgeblocked[y][x] = blocked[y][x] && ( 
    					y == 0 ? true : !blocked[y-1][x] ||
    					x == 0 ? true : !blocked[y][x-1] ||
    					y == blocked.length-1 ? true : !blocked[y+1][x] ||
    					x == blocked[y].length-1 ? true : !blocked[y][x+1]);
    		}
    	}
	    for(int y=0;y<blocked.length;y++){
			for(int x=0;x<blocked[y].length;x++){
				if(edgeblocked[y][x]){
					int width = 0;
					for(;;){
						width++;
						if(x+width>=blocked[y].length){
							width--;
							break;
						}
						if(!blocked[y][x+width]){
							break;
						}
					}
					for(int w=0;w<width;w++){
						edgeblocked[y][x+w] = false;
					}
					FloorPhysics f = new FloorPhysics(x, y, width, 1, GameplayState.getWorld());
					floor.add(f);
					addEntity(f);
				}
			}
	    }
    }
    
    public void removePhysicsEntities() {
    	for(FloorPhysics obj : floor){
    		GameplayState.getWorld().destroyBody(obj.getBody());
    	}
    }

}
