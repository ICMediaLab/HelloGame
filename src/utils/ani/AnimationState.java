package utils.ani;

public enum AnimationState {
	PAUSE_LEFT(null),
	PAUSE_RIGHT(null),
	ROAM_LEFT(PAUSE_LEFT),
	ROAM_RIGHT(PAUSE_RIGHT),
	SLEEP(null);
	
	private final AnimationState parent;
	
	private AnimationState(AnimationState parent){
		this.parent = parent;
	}
	
	public static AnimationState parseState(String lookup){
		return lookup == null ? null : valueOf(lookup.toUpperCase());
	}
	
	public boolean hasParent(){
		return getParent() != null;
	}
	
	public AnimationState getParent(){
		return parent;
	}
}
