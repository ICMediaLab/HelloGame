package map;

import game.config.Config;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.Layer;

public class LayeredLayer extends AbstractLayerRenderable {
	
	private int depth;
	private Layer layer;

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
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		for(int i=0;i<layer.height-2;i++){
			layer.render(0, 0, 1, 1, layer.width-2, i, false, Config.getTileSize(), Config.getTileSize());
		}
	}

	@Override
	public int getLayer() {
		return depth;
	}

}
