package map;

import java.util.Comparator;

import utils.LayerRenderable;

public class LayerComparator implements Comparator<LayerRenderable> {

	@Override
	public int compare(LayerRenderable arg0, LayerRenderable arg1) {
		return arg0.getLayer() - arg1.getLayer();
	}

}
