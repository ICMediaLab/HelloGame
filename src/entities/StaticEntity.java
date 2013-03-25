package entities;


public abstract class StaticEntity extends AbstractEntity {

	public StaticEntity(float x, float y, float width, float height) {
		super(x,y,width,height);
	}
	
	@Override
	public StaticEntity clone() {
		return this;
	}
	
}
