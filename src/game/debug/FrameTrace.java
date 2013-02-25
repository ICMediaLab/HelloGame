package game.debug;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;

public class FrameTrace {
	
	private static final int TRACE_SIZE = 20;

	private final List<FrameStep> steps = new LinkedList<FrameStep>();
	private int size = 0;
	
	public void add(float x, float y, float dx, float dy){
		steps.add(new FrameStep(x,y,dx,dy));
		if(size < TRACE_SIZE){
			size++;
		}else{
			steps.remove(0);
		}
		
	}

	public void add(Rectangle hitbox, float dx, float dy) {
		add(hitbox.getX(),hitbox.getY(),dx,dy);
	}
	
	public void printTrace(){
		for(FrameStep step : steps){
			System.out.println(step);
		}
		System.out.println("^ Most recent.");
	}
	
}
