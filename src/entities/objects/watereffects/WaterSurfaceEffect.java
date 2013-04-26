package entities.objects.watereffects;

import java.io.Serializable;
import java.util.Random;

import map.Cell;
import map.MapLoader;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import utils.interval.two.EntityBasedRange;
import utils.interval.two.Interval2D;
import utils.particles.ParticleEmitter;
import utils.particles.ParticleGenerator;
import entities.MovingEntity;
import entities.StaticRectEntity;
import game.config.Config;

public class WaterSurfaceEffect extends StaticRectEntity {
	
	private static final long serialVersionUID = 4624455664318242407L;
	
	private static final float NODE_SPACING = 0.25f;
	private static final Random r = new Random();
	
	private static final int WATER_SURFACE_EFFECT_DEFAULT_LAYER = 100;
	
	private final ParticleGenerator<WaterEffectParticle> pGen;
	
	private final SurfaceNode[] nodes;
	
	public WaterSurfaceEffect(float x, float y, float width) {
		super(x, y, width, 0.9f);
		nodes = new SurfaceNode[(int) (width/NODE_SPACING) + 1];
		for(int i=0;i<nodes.length;i++){
			nodes[i] = new SurfaceNode();
		}
		pGen = new WaterEffectParticleGenerator(getY() + 0.1f);
	}

	@Override
	public void collide(MovingEntity e) {
		float hw = e.getWidth()/2;
		float x = e.getCentreX();
		if(x-hw >= getX() && x+hw <= getX() + getWidth()){
			nodes[getIndex(e.getCentreX())].p += e.getdY();
			Cell c = MapLoader.getCurrentCell();
			c.addParticleEmmiter(new WaterSurfaceEffectParticleEmitter(pGen, getLayer()+1, e));
		}
	}
	
	private int getIndex(float x){
		return Math.round((x - getX())/NODE_SPACING);
	}
	
	@Override
	public void update(GameContainer gc) {
		nodes[0].update(nodes[0], nodes[1]);
		for(int i=1;i<nodes.length-1;i++){
			nodes[i].update(nodes[i-1], nodes[i+1]);
		}
		nodes[nodes.length-1].update(nodes[nodes.length-2], nodes[nodes.length-1]);
		for(SurfaceNode n : nodes){
			n.set();
		}
	}

	@Override
	public int getLayer() {
		return WATER_SURFACE_EFFECT_DEFAULT_LAYER;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		g.setColor(Color.blue);
		float x = (getX()-1)*Config.getTileSize();
		float dx = NODE_SPACING*Config.getTileSize();
		for(int i=0;i<nodes.length-1;){
			g.drawLine(x, (nodes[i].getP() + getY() - 1)*Config.getTileSize(), x+=dx, (nodes[++i].getP() + getY() - 1)*Config.getTileSize());
		}
	}

	@Override
	public boolean isSolid() {
		return false;
	}
	
	private static class SurfaceNode implements Serializable {
		
		private static final long serialVersionUID = -4814847695358868793L;

		private float p = 0f;
		private transient float np = 0f;
		
		void set(){
			p = np;
		}
		
		void update(SurfaceNode left, SurfaceNode right){
			np += np + (left.p - p) + (right.p - p);
			np /= 4;
		}
		
		float getP(){
			return p+0.1f;
		}
		
	}
	
	private class WaterSurfaceEffectParticleEmitter extends ParticleEmitter<WaterEffectParticle> {
		
		private int lifespan = 120;
		private final int init = lifespan*2;
		
		public WaterSurfaceEffectParticleEmitter(ParticleGenerator<? extends WaterEffectParticle> pGen, int layer, MovingEntity e) {
			super(pGen, new EntityBasedRange(e, new Interval2D(-0.5f, 0.5f, -0.5f, 0.5f)), new Interval2D(-0.1f,0.1f,0f,0f), layer);
		}

		@Override
		public boolean isEmitting() {
			return lifespan-- > 0;
		}
		
		@Override
		protected void generateParticles() {
			if(r.nextInt(init) < lifespan){
				addParticle(getNewParticle());
			}
		}
		
	}

}
