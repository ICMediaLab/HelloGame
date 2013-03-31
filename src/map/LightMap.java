package map;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lights.Light;
import lights.AbstractLight;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import utils.Renderable;
import utils.Updatable;

public class LightMap implements Updatable, Renderable {

	private final Set<Light> lights = new HashSet<Light>();
	
	public LightMap() { 
	}
	
	public LightMap(Collection<? extends Light> lights) {
		this.lights.addAll(lights);
	}
	
	public void addLight(Light entityLight){
		lights.add(entityLight);
	}
	
	public boolean removeLight(Object o){
		return lights.remove(o);
	}
	
	public Set<Light> getLights() {
		return lights;
	}
	
	public void render(GameContainer gc, Graphics g){
		
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

	public void update(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}
	
	
}
