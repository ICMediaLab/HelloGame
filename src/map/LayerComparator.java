package map;

import java.util.Comparator;

import utils.LayerRenderable;

public class LayerComparator implements Comparator<LayerRenderable> {

	@Override
	public int compare(LayerRenderable arg0, LayerRenderable arg1) {
		int diff = arg0.getLayer() - arg1.getLayer();
		return diff == 0 ? arg0.hashCode() - arg1.hashCode() : diff;
	}
}
