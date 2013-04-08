package map;

import game.config.Config;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.Layer;

import utils.LayerRenderable;

public class LayeredLayer implements LayerRenderable {
	
	private final int depth;
	private final Layer layer;

	/**
	 * Attempts to create a new layered-layer object using the 'render-layer' property of this layer.<br />
	 * If no such property exists, a {@link NoSuchFieldException} will be thrown.
	 * @throws NoSuchFieldException 
	 */
	public LayeredLayer(Layer l) throws NoSuchFieldException {
		layer = l;
		try{
			depth = Integer.parseInt(l.props.getProperty("render-index"));
		}catch(NumberFormatException e){
			throw new NoSuchFieldException("Could not find property 'render-index' in layer: " + l.name);
		}catch(NullPointerException e){
			throw new NoSuchFieldException("Could not find property 'render-index' in layer: " + l.name);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		for(int ty=0;ty<layer.height-2;ty++){
			layer.render(0, 0, 1, 1, layer.width-2, ty, false, Config.getTileSize(), Config.getTileSize());
		}
	}

	@Override
	public int getLayer() {
		return depth;
	}

}
