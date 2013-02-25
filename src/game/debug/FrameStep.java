package game.debug;

public class FrameStep {
	
	private final float x,y,dx,dy;

	FrameStep(float x, float y, float dx, float dy) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	
	public String toString(){
		return "(x: " + x + ",\ty: " + y + ",\tdx: " + dx + ",\tdy: " + dy + ")";
	}
}
