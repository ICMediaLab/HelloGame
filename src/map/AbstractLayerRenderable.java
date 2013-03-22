package map;

import utils.LayerRenderable;

public abstract class AbstractLayerRenderable implements LayerRenderable {

	@Override
	public int compareTo(LayerRenderable o) {
		return getLayer() - o.getLayer();
	}
}
