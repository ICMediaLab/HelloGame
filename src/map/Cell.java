package map;

import entities.DestructibleEntity;
import entities.Entity;
import entities.MovingEntity;
import entities.StaticEntity;
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
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.ObjectGroup;
import org.newdawn.slick.tiled.TiledMap;

import utils.LayerRenderable;
import utils.Renderable;
import utils.Updatable;
import utils.particles.ParticleEmitter;
import utils.particles.RainTest;


public class Cell extends TiledMap implements Updatable, Renderable {
	
	private static final Comparator<? super LayerRenderable> LAYER_COMPARATOR = new LayerComparator();
	
	/**
	 * A transformation that can be applied when drawing slick shapes to the window in order to ensure they are drawn correctly.
	 */
	public static final Transform SHAPE_DRAW_TRANSFORM = Transform.createScaleTransform(Config.getTileSize(), Config.getTileSize()).concatenate(Transform.createTranslateTransform(-1, -1));
	
	private static final int MINIMAP_OFFSET = 10;
	private static final int MINIMAP_CELL_WIDTH = 22, MINIMAP_CELL_HEIGHT = 22;
	private static final int MINIMAP_CELL_SPACING = 7;
	private static final int MINIMAP_CELL_CORNER_RADIUS = 5;
	private static final int MINIMAP_BORDER_RADIUS = 2;
	
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
	
	private final Set<ParticleEmitter<?>> particleEmitters = new HashSet<ParticleEmitter<?>>();
	
	private final Set<Light> lights = new LinkedHashSet<Light>();
	private final Map<Entity,Light> entityLights = new HashMap<Entity,Light>();
	
	private Player player;
	private boolean visited = false;
	
	private static float minimapOpacity = 1f;
	private static float minimapOpacityDelta = 0.05f;
	
	public Cell(String location) throws SlickException {
		super(location);
		loadProperties();
		setDefaultEntities();
	}
	
	public void initLoad(){
		resetEntities();
		resetRenderables();
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
		particleEmitters.clear();
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
		addParticleEmmiter(RainTest.getRain(this,100000));
		renderables.addAll(staticEntities);
	}
	
	private void setDefaultEntities() {
		addLight(new AmbientLight(new Color(0.5f, 0.5f, 1f, 0.4f)));
		addLight(new PointLight(1020, 0, 5));
		addLight(new PointLight(0, 0, 5));
		
		for(ObjectGroup og : objectGroups){
			for(GroupObject go : og.objects){
				CellObjectParser.getInst().addParseObject(this, go);
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
				properties[yAxis][xAxis] = new Tile(this,xAxis, yAxis);
			}
		}
	}
	
	public Layer getLayer(int index){
		return layers.get(index);
	}
	
	void addDefaultMovingEntity(MovingEntity newEntity){
		defaultEntities.add(newEntity);
	}
	
	void addDefaultDestructibleEntity(DestructibleEntity newEntity){
		defaultDestructibleEntities.add(newEntity);
	}
	
	public void addMovingEntity(MovingEntity newEntity) {
		entitiesToAdd.add(newEntity);
		renderables.add(newEntity);
	}
	
	void addDestructibleEntity(DestructibleEntity newEntity) {
		destructibleEntities.add(newEntity);
		renderables.add(newEntity);
	}
	
	void addStaticEntity(StaticEntity<?> newEntity) {
		staticEntities.add(newEntity);
		renderables.add(newEntity);
	}
	
	public void render(GameContainer gc, Graphics g) {
		for(LayerRenderable lr : renderables){
			lr.render(gc, g);
		}
		
		renderLightmap(gc,g);
		renderMinimap(gc,g);
	}
	
	private static void renderMinimap(GameContainer gc, Graphics g){
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_K)) {
			minimapOpacityDelta = -minimapOpacityDelta;
		}
		
		// render minimap
		minimapOpacity = Math.min(1f, Math.max(0f, minimapOpacity + minimapOpacityDelta));
		Cell[][] mini = MapLoader.getSurroundingCells();
		
		int y = MINIMAP_OFFSET;
		for (int ty = 0; ty < 3; ty++) {
			int x = gc.getWidth() - MINIMAP_OFFSET;
			for (int tx = 2; tx >= 0; tx--) {
				x -= MINIMAP_CELL_WIDTH;
				
				if (mini[ty][tx] != null) {
					if (tx != 1 || ty != 1) {
						if (mini[ty][tx].visited) {
							g.setColor(Color.green.scaleCopy(minimapOpacity));
						} else {
							g.setColor(Color.white.scaleCopy(minimapOpacity));
						}
					}else{
						g.setColor(Color.orange.scaleCopy(minimapOpacity));
					}
					g.fillRoundRect(x - MINIMAP_BORDER_RADIUS, y - MINIMAP_BORDER_RADIUS, 
							MINIMAP_CELL_WIDTH + 2*MINIMAP_BORDER_RADIUS, MINIMAP_CELL_HEIGHT + 2*MINIMAP_BORDER_RADIUS, MINIMAP_CELL_CORNER_RADIUS);
					g.setColor(Color.darkGray.scaleCopy(minimapOpacity));
					g.fillRoundRect(x, y, MINIMAP_CELL_WIDTH, MINIMAP_CELL_HEIGHT, MINIMAP_CELL_CORNER_RADIUS);
				}
				x -= MINIMAP_CELL_SPACING;
			}
			y += MINIMAP_CELL_SPACING + MINIMAP_CELL_HEIGHT;
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
		updateEmmiters(gc);
		updateEntities(gc);
		updateLightmap(gc);
	}
	
	private void updateEmmiters(GameContainer gc) {
		Set<ParticleEmitter<?>> toRemove = new HashSet<ParticleEmitter<?>>();
		for(ParticleEmitter<?> pe : particleEmitters){
			pe.update(gc);
			if(!pe.isAlive()){
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

	public void addParticleEmmiter(ParticleEmitter<?> particleEngine) {
		particleEmitters.add(particleEngine);
		renderables.add(particleEngine);
	}

}
