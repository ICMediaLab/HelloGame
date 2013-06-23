package utils.ani;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class AnimationManager implements Cloneable {
	
	private final Map<AnimationState,AnimationContainer> animap = new HashMap<AnimationState,AnimationContainer>();
	private AnimationState currentState = null;
	
	public AnimationManager() {
		
	}
	
	public AnimationManager(AnimationManager base) {
		this.currentState = base.currentState;
		this.animap.putAll(base.animap);
	}

	public AnimationContainer getAnimation(AnimationState state){
		if(state == null){
			return null;
		}
		AnimationContainer res = animap.get(state);
		if(res == null && state.hasParent()){
			return getAnimation(state.getParent());
		}
		return res;
	}
	
	public void addAnimation(AnimationState state, AnimationContainer ani){
		if(state == null || ani == null || animap.containsKey(state)){
			return;
		}
		animap.put(state, ani);
		System.out.println("Added mapping from " + state + " to " + ani);
		addAnimation(state.getParent(),ani);
		if(currentState == null){
			currentState = state;
		}
	}
	
	private void addNode(Node n) throws SlickException {
		AnimationContainer aniCont = new AnimationContainer(n);
		NamedNodeMap attrs = n.getAttributes();
		try{
			if(Boolean.parseBoolean(attrs.getNamedItem("flipped").getTextContent())){
				aniCont = aniCont.flippedCopy(true, false);
			}
		}catch(NullPointerException e){ }
		try{
			AnimationState state = AnimationState.parseState(attrs.getNamedItem("state").getTextContent());
			if(state == null){
				System.out.println("Warning: No such state: '" + attrs.getNamedItem("state").getTextContent() + "'.");
			}else{
				addAnimation(state, aniCont);
			}
		}catch(NullPointerException e){
			System.out.println("Warning: All Animations must have a state defined.");
		}
	}
	
	public static AnimationManager parseElement(Element node) throws SlickException {
		AnimationManager ani = new AnimationManager();
		NodeList ns = node.getElementsByTagName("animation");
		for(int i=ns.getLength()-1;i>=0;i--){
			Node n = ns.item(i);
			ani.addNode(n);
		}
		return ani;
	}

	public AnimationState getCurrentState() {
		return currentState;
	}
	
	public AnimationContainer getCurrentAnimationContainer(){
		return animap.get(currentState);
	}
	
	public void setCurrentState(AnimationState state) {
		this.currentState = state;
	}

	public void updateCurrentAnimation(int delta) {
		getCurrentAnimationContainer().update(delta);
	}
	
	public AnimationManager clone(){
		return new AnimationManager(this);
	}
}
