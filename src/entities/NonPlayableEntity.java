package entities;


public abstract class NonPlayableEntity extends Entity implements INonPlayableEntity {
	
	public NonPlayableEntity(float x, float y, int width, int height, int maxhealth) {
		super(x,y,width,height,maxhealth);
	}
	
	public NonPlayableEntity(float x, float y, int maxhealth){
		super(x,y,maxhealth);
	}
	
	public NonPlayableEntity(int width, int height, int maxhealth){
		super(width,height,maxhealth);
	}
	
	public NonPlayableEntity(float x, float y, int width, int height){
		super(x,y,width,height);
	}
	
	public NonPlayableEntity(float x, float y){
		super(x,y);
	}
	
	public NonPlayableEntity(int width, int height){
		super(width,height);
	}
	
	public NonPlayableEntity(){
		super();
	}
}
