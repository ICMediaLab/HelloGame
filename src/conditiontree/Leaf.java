package conditiontree;


public abstract class Leaf<T> extends TreeNode<T> {
	
	private static final long serialVersionUID = 7894184335897253089L;
	
	protected abstract void setState(String stateStr);
	
}
