package utils.tracing;

public interface TraceFunctor {
	
	/**
	 * An abstract function to be applied to pairs of x,y coordinates.
	 * @return True if and only if the trace should terminate.
	 */
	boolean function(int x, int y);
}
