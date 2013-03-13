package map;

import game.config.Config;
import items.projectiles.Projectile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.ObjectGroup;
import org.newdawn.slick.tiled.TiledMap;

import entities.Entity;
import entities.enemies.Enemy;
import entities.objects.Door;
import entities.players.Player;


public class Cell extends TiledMap{

	private final Tile[][] properties = new Tile[getHeight()][getWidth()];
	private final Set<Entity> defaultEntities = new HashSet<Entity>();
	private final Set<Entity> entities = new HashSet<Entity>();
	private final Set<Entity> entitiesToRemove = new HashSet<Entity>(); 
	private final Set<Projectile> projectiles = new HashSet<Projectile>();
    private Set<Projectile> projectilesToRemove = new HashSet<Projectile>();
	private static final long DELTA = 1000/60;
    private Player player;
			
	public Cell(String location) throws SlickException {
		super(location);
		loadDefaultEntities();
		loadProperties();
	}
	
	public void loadDefaultEntities(){
		entities.clear();
		if(defaultEntities.isEmpty()){
			for(ObjectGroup og : super.objectGroups){
				for(GroupObject go : og.objects){
					int x = go.x / Config.getTileSize();
					int y = go.y / Config.getTileSize();
					if(go.type.equalsIgnoreCase("enemy")){
						defaultEntities.add(Enemy.getNewEnemy(this, go.name, x,y));
					}else if(go.type.equalsIgnoreCase("door")){
						defaultEntities.add(new Door(x,y));
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
		entities.add(newEntity);
	}
	
	public void addProjectile(Projectile projectile){
		projectiles.add(projectile);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		super.render(-Config.getTileSize(),-Config.getTileSize());
		for(Entity e : entities){
			e.render(gc, sbg, g);
		}
		for(Projectile p : projectiles){
			p.render(gc, sbg, g);
		}
	}


	public void clearEntities() {
		entitiesToRemove.addAll(entities);
	}
	
	public Set<Entity> getEntities() {
	    return entities;
	}
	
	public void updateEntities(GameContainer gc, StateBasedGame sbg, int delta){
		entitiesToRemove.clear();
		for(Entity e : entities){
			e.update(gc, sbg, delta);
		}
		entities.removeAll(entitiesToRemove);
        projectilesToRemove.clear();
		for(Projectile p : projectiles){
			p.update(DELTA);
		}
        projectiles.removeAll(projectilesToRemove);
	}
    
    public void removeEntity(Entity e) {
        entitiesToRemove.add(e);
    }
    
    public void removeProjectile(Projectile p) {
        projectilesToRemove.add(p);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }

}
