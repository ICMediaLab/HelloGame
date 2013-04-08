package map;

import entities.DestructibleEntity;
import entities.Entity;
import entities.MovingEntity;
import entities.StaticEntity;
import entities.enemies.Enemy;
import entities.npcs.NPC;
import entities.objects.Cage;
import entities.objects.Door;
import entities.objects.DoorTrigger;
import entities.objects.JumpPlatform;
import entities.objects.LeafTest;
import entities.players.Player;
import game.config.Config;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.ObjectGroup;
import org.newdawn.slick.tiled.TiledMap;

import utils.LayerRenderable;
import utils.MapLoader;
import utils.Renderable;
import utils.Updatable;
import utils.particles.ParticleEmitter;
import GUI.TextField;


public class Cell extends TiledMap implements Updatable, Renderable {
	
	private static final Comparator<? super LayerRenderable> LAYER_COMPARATOR = new LayerComparator();
	
	/**
	 * A transformation that can be applied when drawing slick shapes to the window in order to ensure they are drawn correctly.
	 */
	public static final Transform SHAPE_DRAW_TRANSFORM = Transform.createTranslateTransform(-1, -1).concatenate(Transform.createScaleTransform(Config.getTileSize(), Config.getTileSize()));
	
	private final Tile[][] properties = new Tile[getHeight()][getWidth()];
	
	private final Set<LayerRenderable> renderables = new TreeSet<LayerRenderable>(LAYER_COMPARATOR);
	
	private final Set<MovingEntity> defaultEntities = new HashSet<MovingEntity>();
	private final Set<DestructibleEntity> defaultDestructibleEntities = new HashSet<DestructibleEntity>();
	private final Set<StaticEntity<?>> staticEntities = new HashSet<StaticEntity<?>>();
	
	private final Set<MovingEntity> entities = new HashSet<MovingEntity>();
	private final Set<MovingEntity> entitiesToRemove = new HashSet<MovingEntity>();
	private final Set<MovingEntity> entitiesToAdd = new HashSet<MovingEntity>();
	
	private final Set<DestructibleEntity> destructibleEntities = new HashSet<DestructibleEntity>();
	private final Set<DestructibleEntity> destructibleEntitiesToRemove = new HashSet<DestructibleEntity>();
	
	private final Set<ParticleEmitter> particleEmitters = new HashSet<ParticleEmitter>();
	
	private final Set<Light> lights = new LinkedHashSet<Light>();
	private final Map<Entity,Light> entityLights = new HashMap<Entity,Light>();
	
	private Player player;
	private boolean visited = false;
	private int counter = 150;
	private float fadeOut = 1;
	
	public Cell(String location) throws SlickException {
		super(location);
		loadProperties();
		initLoad();
	}
	
	public void initLoad(){
		resetRenderables();
		resetEntities();
		if(defaultEntities.isEmpty()){
			setDefaultEntities();
		}
		initNewEntities();
	}
	
	private void resetEntities() {
		entities.clear();
		entitiesToAdd.clear();
		entitiesToRemove.clear();
		destructibleEntities.clear();
		destructibleEntitiesToRemove.clear();
		lights.removeAll(entityLights.values());
		entityLights.clear();
	}

	private void resetRenderables() {
		renderables.clear();
		for(Layer l : layers){
			try {
				renderables.add(new LayeredLayer(l));
			} catch (NoSuchFieldException e) {
				System.out.println(e.getMessage());
			}
		}
		renderables.addAll(staticEntities);
	}
	
	private void setDefaultEntities() {
		addStaticEntity(new TextField<Rectangle>("'Tis a silly place", new Rectangle(19, 14, 5,3), 0, -50, Color.transparent, Color.white, 50, 50));
		addStaticEntity(new TextField<Circle>("Help, help, I'm being repressed!", new Circle(25, 16, 10), 0, -50, Color.transparent, Color.white, 50, 50));
		
		addLight(new AmbientLight(new Color(0.5f, 0.5f, 1f, 0.4f)));
		addLight(new PointLight(1020, 0, 5));
		addLight(new PointLight(0, 0, 5));
		
		Map<String,Door> doors = new HashMap<String,Door>();
		Map<String,DoorTrigger> triggers = new HashMap<String,DoorTrigger>();
		
		for(ObjectGroup og : objectGroups){
			for(GroupObject go : og.objects){
				int x = go.x / Config.getTileSize();
				int y = go.y / Config.getTileSize();
				int width  = go.width  / Config.getTileSize();
				int height = go.height / Config.getTileSize();
				
				if(go.type.equalsIgnoreCase("enemy")){
					defaultEntities.add(Enemy.getNew(this, go.name, x,y));
				}else if(go.type.equalsIgnoreCase("npc")){
					defaultEntities.add(NPC.getNew(this, go.name, x,y));
				}else if(go.type.equalsIgnoreCase("door")){
					if(triggers.containsKey(go.name)){
						addStaticEntity(new Door(this,triggers.remove(go.name),x,y));
					}else{
						Door d = new Door(this,null,x,y);
						doors.put(go.name,d);
						addStaticEntity(d);
					}
				}else if(go.type.equalsIgnoreCase("doorTrigger")){
					if(doors.containsKey(go.name)){
						addStaticEntity(new DoorTrigger(doors.remove(go.name),x,y));
					}else{
						DoorTrigger dt = new DoorTrigger(null, x, y);
						triggers.put(go.name, dt);
						addStaticEntity(dt);
					}
				}else if(go.type.equalsIgnoreCase("leafTest")){
					addStaticEntity(new LeafTest(x,y));
				} else if(go.type.equalsIgnoreCase("jumpPlatform")){
					addStaticEntity(new JumpPlatform(x,y,width));
				} else if(go.type.equalsIgnoreCase("cage")){
					defaultDestructibleEntities.add(new Cage(x,y,width,height));
				}
			}
		}
	}
	
	private void initNewEntities() {
		for(MovingEntity e : defaultEntities){
			MovingEntity clo = e.clone();
			addMovingEntity(clo);
			addLight(new EntityLight(clo, 3f, new Color(1f,1f,1f,0.6f)));
		}
		for(DestructibleEntity e : defaultDestructibleEntities){
			DestructibleEntity clo = e.clone();
			addDestructibleEntity(clo);
			addLight(new EntityLight(clo, 3f, new Color(1f,1f,1f,0.6f)));
		}
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
		renderables.add(newEntity);
	}
	
	private void addDestructibleEntity(DestructibleEntity newEntity) {
		destructibleEntities.add(newEntity);
		renderables.add(newEntity);
	}
	
	private void addStaticEntity(StaticEntity<?> newEntity) {
		staticEntities.add(newEntity);
		renderables.add(newEntity);
	}
	
	public void render(GameContainer gc, Graphics g) {
		for(LayerRenderable lr : renderables){
			lr.render(gc, g);
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

	public void clearMovingEntities() {
		entitiesToRemove.addAll(entities);
	}
	
	public Set<MovingEntity> getMovingEntities() {
		return entities;
	}
	
	public Set<StaticEntity<?>> getStaticEntities() {
		return staticEntities;
	}
	
	public Set<DestructibleEntity> getDestructibleEntities() {
		return destructibleEntities;
	}
	
	public void update(GameContainer gc){
		updateEntities(gc);
		updateEmmiters(gc);
		updateLightmap(gc);
	}
	
	private void updateEmmiters(GameContainer gc) {
		Set<ParticleEmitter> toRemove = new HashSet<ParticleEmitter>();
		for(ParticleEmitter pe : particleEmitters){
			pe.update(gc);
			if(!pe.isEmitting()){
				toRemove.add(pe);
				renderables.remove(pe);
			}
		}
		particleEmitters.removeAll(toRemove);
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
			for(DestructibleEntity d : destructibleEntities){
				if(e.intersects(d)){
					e.collide(d);
					d.collide(e);
				}
			}
			for(StaticEntity<?> s : staticEntities){
				if(e.intersects(s)){
					e.collide(s);
					s.collide(e);
				}
			}
		}
		for(DestructibleEntity d : destructibleEntities){
			d.update(gc);
		}
		for(StaticEntity<?> s : staticEntities){
			s.update(gc);
		}
		entities.removeAll(entitiesToRemove);
		for(Entity e : entitiesToRemove){
			lights.remove(entityLights.remove(e));
		}
		destructibleEntities.removeAll(destructibleEntitiesToRemove);
		for(Entity e : destructibleEntitiesToRemove){
			lights.remove(entityLights.remove(e));
		}
		entitiesToRemove.clear();
	}
	
	public void removeMovingEntity(MovingEntity e) {
		entitiesToRemove.add(e);
		renderables.remove(e);
	}
	
	public void removeDestructibleEntity(DestructibleEntity e) {
		destructibleEntitiesToRemove.add(e);
		renderables.remove(e);
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

	public void addParticleEmmiter(ParticleEmitter particleEngine) {
		particleEmitters.add(particleEngine);
		renderables.add(particleEngine);
	}

}
