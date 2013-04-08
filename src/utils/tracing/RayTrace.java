package utils.tracing;

import map.Cell;
import map.tileproperties.TileProperty;
import utils.Position;

public class RayTrace {

	/**
	 * A method to map a function f over all integer pairs of x,y values overlapping the straight path from src to dst.<br />
	 * The method will be apply the function to each pair of values exactly once, including the src and dst truncated values. 
	 * @param src The start position for the trace.
	 * @param dst The end position for the trace.
	 * @param f The function to be applied over the trace.
	 * @return The result of the final application of the function f, i.e. f(dst.x, dst.y).
	 */
	public static boolean mapTrace(Position src, Position dst, TraceFunctor f) {
		float x0 = src.getX(), y0 = src.getY();
		float x1 = dst.getX(), y1 = dst.getY();
		int x1i = (int) x1, y1i = (int) y1;
		
		float dx = Math.abs(x1 - x0);
		float dy = Math.abs(y1 - y0);
		
		int x = (int) x0;
		int y = (int) y0;
		
		int n = 1;
		int x_inc, y_inc;
		float error;
		
		if (dx == 0) {
			x_inc = 0;
			error = Float.MAX_VALUE;
		}else if (x1 > x0) {
			x_inc = 1;
			n += x1i - x;
			error = ((int) x0 + 1 - x0) * dy;
		}else {
			x_inc = -1;
			n += x - x1i;
			error = (x0 - (int) x0) * dy;
		}
		
		if (dy == 0) {
			y_inc = 0;
			error -= Float.MAX_VALUE;
		}else if (y1 > y0) {
			y_inc = 1;
			n += y1i - y;
			error -= ((int) y0 + 1 - y0) * dx;
		}else {
			y_inc = -1;
			n += y - y1i;
			error -= (y0 - (int) y0) * dx;
		}
		
		for(;n>0;--n){
			if(f.function(x, y)){
				return true;
			}
			int yDiff = y - y1i;
			if (error > 0 || error == 0 && x == x1i && (yDiff == 1 || yDiff == -1)) {
				y += y_inc;
				error -= dx;
			}else {
				x += x_inc;
				error += dy;
			}
		}
		return f.function(x, y);
	}
	
	/**
	 * Returns true if and only if a straight path exists from src to dst intersecting no blocked tiles.
	 * @param cell The cell context.
	 * @param src The start position for the trace.
	 * @param dst The end position for the trace.
	 */
	public static boolean blockedTrace(Cell cell, Position src, Position dst){
		return !mapTrace(src, dst, new BlockedFunctor(cell));
	}
	
	private static class BlockedFunctor implements TraceFunctor {

		private final Cell cell;

		public BlockedFunctor(Cell cell){
			this.cell = cell;
		}
		
		@Override
		public boolean function(int x, int y) {
			return cell.getTile(x, y).lookup(TileProperty.BLOCKED).getBoolean();
		}
		
	}
}
