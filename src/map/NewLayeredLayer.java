package map;

import game.config.Config;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.Element;

import utils.LayerRenderable;

/**
 * Eventually planned to replace slick's default layer usage
 * @author george
 *
 */
public class NewLayeredLayer extends Layer implements LayerRenderable {

	private final int depth;

	public NewLayeredLayer(TiledMap map, Element element) throws SlickException, NoSuchFieldException {
		super(map, element);
		try{
			depth = Integer.parseInt(props.getProperty("render-index"));
		}catch(NumberFormatException e){
			throw new NoSuchFieldException("Could not find property 'render-index' in layer: " + name);
		}catch(NullPointerException e){
			throw new NoSuchFieldException("Could not find property 'render-index' in layer: " + name);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		for(int i=0;i<height-2;i++){
			render(0, 0, 1, 1, width-2, i, false, Config.getTileSize(), Config.getTileSize());
		}
	}

	@Override
	public int getLayer() {
		return depth;
	}

}
