package utils;


public interface LayerRenderable extends Renderable {
	/**
	 * Returns the layer of this item. This method should be used when implementing Comparable.
	 */
	int getLayer();
}
