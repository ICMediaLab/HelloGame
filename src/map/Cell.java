package map;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import lights.AbstractLight;
import lights.AmbientLight;
import lights.EntityLight;
import lights.Light;
import lights.PointLight;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.ObjectGroup;
import org.newdawn.slick.tiled.TiledMap;

import GUI.TextField;

import utils.LayerRenderable;
import utils.MapLoader;
import utils.Renderable;
import utils.Updatable;
import entities.Entity;
import entities.MovingEntity;
import entities.StaticEntity;
import entities.enemies.Enemy;
import entities.npcs.NPC;
import entities.objects.Door;
import entities.objects.DoorTrigger;
import entities.objects.JumpPlatform;
import entities.objects.LeafTest;
import entities.players.Player;
import game.config.Config;


public class Cell extends TiledMap implements Updatable, Renderable {

	private static final Comparator<? super LayerRenderable> LAYER_COMPARATOR = new LayerComparator();
	
	private final Tile[][] properties = new Tile[getHeight()][getWidth()];
	
	private final Set<MovingEntity> defaultEntities = new HashSet<MovingEntity>();
	private final Set<StaticEntity<?>> staticEntities = new HashSet<StaticEntity<?>>();
	
	private final Set<MovingEntity> entities = new HashSet<MovingEntity>();
	private final Set<MovingEntity> entitiesToRemove = new HashSet<MovingEntity>();
	private final Set<MovingEntity> entitiesToAdd = new HashSet<MovingEntity>();
	
	private final Set<Light> lights = new LinkedHashSet<Light>();
	private final Map<Entity,Light> entityLights = new HashMap<Entity,Light>();
	
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
		lights.clear();
		entityLights.clear();
		if(defaultEntities.isEmpty()){
			staticEntities.add(new TextField<Circle>("'Tis a silly place", new Circle(21, 15, 3), 0, -50, Color.transparent, Color.white, 50, 50));
			
			Map<String,Door> doors = new HashMap<String,Door>();
			Map<String,DoorTrigger> triggers = new HashMap<String,DoorTrigger>();
			
			for(ObjectGroup og : super.objectGroups){
				for(GroupObject go : og.objects){
					int x = go.x / Config.getTileSize();
					int y = go.y / Config.getTileSize();
					if(go.type.equalsIgnoreCase("enemy")){
						defaultEntities.add(Enemy.getNew(this, go.name, x,y));
					}else if(go.type.equalsIgnoreCase("npc")){
						defaultEntities.add(NPC.getNew(this, go.name, x,y));
					}else if(go.type.equalsIgnoreCase("door")){
						if(triggers.containsKey(go.name)){
							staticEntities.add(new Door(this,triggers.remove(go.name),x,y));
						}else{
							Door d = new Door(this,null,x,y);
							doors.put(go.name,d);
							staticEntities.add(d);
						}
					}else if(go.type.equalsIgnoreCase("doorTrigger")){
						if(doors.containsKey(go.name)){
							staticEntities.add(new DoorTrigger(doors.remove(go.name),x,y));
						}else{
							DoorTrigger dt = new DoorTrigger(null, x, y);
							triggers.put(go.name, dt);
							staticEntities.add(dt);
						}
					}else if(go.type.equalsIgnoreCase("leafTest")){
						staticEntities.add(new LeafTest(x,y));
					} else if(go.type.equalsIgnoreCase("jumpPlatform")){
						staticEntities.add(new JumpPlatform(x,y));
					}
				}
			}
		}
		for(MovingEntity e : defaultEntities){
			MovingEntity clo = e.clone();
			addMovingEntity(clo);
			addLight(new EntityLight(clo, 3f, new Color(1f,1f,1f,0.6f)));
		}
		
		addLight(new AmbientLight(new Color(0.5f, 0.5f, 1f, 0.4f)));
		addLight(new PointLight(1020, 0, 5));
		addLight(new PointLight(0, 0, 5));
	}
	
	public Tile getTile(int x, int y) {
		return properties[y][x];
	}
	
	private void addLight(Light l){
		if(l instanceof EntityLight){
			entityLights.put(((EntityLight) l).getEntity(), l);
		}
		lights.add(l);
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
	
	
	public void addMovingEntity(MovingEntity newEntity) {
		entitiesToAdd.add(newEntity);
	}
	
	public void render(GameContainer gc, Graphics g) {
		
		System.out.println(entityLights.keySet().removeAll(defaultEntities));
		
		//super.render(-Config.getTileSize(),-Config.getTileSize());
		PriorityQueue<LayerRenderable> orderedLayers = new PriorityQueue<LayerRenderable>(11,LAYER_COMPARATOR);
		orderedLayers.addAll(entities);
		orderedLayers.addAll(staticEntities);
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
		
		renderLightmap(gc,g);
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
	
	private void renderLightmap(GameContainer gc, Graphics g){
		
		//clear alpha map in preparation
		g.clearAlphaMap();
		
		AbstractLight.renderPre(g);
		
		//render each light
		for(Light l : lights){
			l.render(gc,g);
		}
		
		//fill remaining area with darkness... i think... :/
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_DST_ALPHA);
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		
		AbstractLight.renderPost(g);
	}

	public void clearEntities() {
		entitiesToRemove.addAll(entities);
	}
	
	public Set<MovingEntity> getMovingEntities() {
	    return entities;
	}
	
	public Set<StaticEntity<?>> getStaticEntities() {
	    return staticEntities;
	}
	
	public void update(GameContainer gc){
		updateEntities(gc);
		updateLightmap(gc);
	}
	
	private void updateLightmap(GameContainer gc) {
		for(Light l : lights){
			l.update(gc);
		}
	}

	public void updateEntities(GameContainer gc){
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();
		for(MovingEntity e : entities){
			e.update(gc);
			for (MovingEntity e2 : entities){
				if (e != e2 && e.intersects(e2)){
					e.collide(e2);
				}
			}
			for(StaticEntity<?> s : staticEntities){
				if(e.intersects(s)){
					e.collide(s);
					s.collide(e);
				}
			}
		}
		for(StaticEntity<?> s : staticEntities){
			s.update(gc);
		}
		entities.removeAll(entitiesToRemove);
		for(Entity e : entitiesToRemove){
			lights.remove(entityLights.remove(e));
		}
		entitiesToRemove.clear();
	}
    
    public void removeMovingEntity(MovingEntity e) {
        entitiesToRemove.add(e);
    }
    
    public void setPlayer(Player player) {
        this.player = player;
        addLight(new EntityLight(player, 7f));
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
